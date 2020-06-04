import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class CardProcessor {

    private final int THRESHOLD = 165;

    public List<Rect> detectCards(Mat frame) {
        Mat orgFrame = frame.clone();
        Mat frameGray = new Mat();
        Mat frameBlurred = new Mat();
        Mat frameThresh = new Mat();

        //Image processing
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(frameGray, frameBlurred, new Size(5, 5), 0);
        Imgproc.threshold(frameBlurred, frameThresh, THRESHOLD, 255, Imgproc.THRESH_BINARY);

        List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
        List<Rect> cards = new ArrayList<Rect>();

        //Use Imgproc.RETR_EXTERNAL for cards and Imgproc.RETR_TREE for all contours.
        Imgproc.findContours(frameThresh, cnts, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint p : cnts) {
            Rect rect = Imgproc.boundingRect(p);

            //Filter out small and large contours by size
            if ((rect.width >= 100 && rect.height >= 100) && (rect.width <= 350 && rect.height <= 500)) {
                cards.add(rect);
                Imgproc.rectangle(orgFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            }
        }

        GUI.getInstance().showResult(orgFrame, "");
        System.out.println(cards.size() + " cards found!");

        return cards;
    }

    public void findCornerContours(Mat frame, List<Rect> listOfCards) {
        List<Mat> listOfCorners = new ArrayList<Mat>();
        List<Rect> listOfFigures = new ArrayList<Rect>();

        //Crops the corners of each of the cards
        for (int i = 0; i < listOfCards.size(); i++) {
            Mat cardsCropped = new Mat(frame, listOfCards.get(i));
            Mat cornersCropped = new Mat(cardsCropped, new Rect(0, 0, cardsCropped.width() / 4, cardsCropped.height() / 3));
            listOfCorners.add(cornersCropped);
        }

        //Find the contours (figures and numbers) on each corner
        int cntscount = 0;
        for (Mat m : listOfCorners) {
            List<MatOfPoint> croppedCnts = new ArrayList<MatOfPoint>();
            Mat mGray = new Mat();

            //Image processing
            Imgproc.cvtColor(m, mGray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(mGray, mGray, THRESHOLD, 255, Imgproc.THRESH_BINARY);
            Imgproc.findContours(mGray, croppedCnts, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint point : croppedCnts) {
                Rect rect = Imgproc.boundingRect(point);

                //Filter out small and large contours by size
                if ((rect.width >= 8 && rect.height >= 31) && (rect.width <= 100 && rect.height <= 100)) {
                    listOfFigures.add(rect);


//                     reqFigure(m, rect, cntscount + 1);

//                    recNumbers(m, rect, cntscount + 1);

                    NumberDetector numberDetector = new NumberDetector();
                    numberDetector.recNumber(m, rect, THRESHOLD);

                    Imgproc.rectangle(m, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 1);
                    cntscount++;
                }
            }
            //GUI.getInstance().showResult(m, "");
        }
        System.out.println(cntscount);
    }

    public void recFigure(Mat frame, Rect figure, int count) {


        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);


        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, THRESHOLD, 255, Imgproc.THRESH_BINARY);

        double[] centerPixel = figureCropped.get(figureCropped.height() / 2, figureCropped.width() / 2);

        double[] clubsPixel = grayCropped.get((int) (figureCropped.height() * 0.33), (int) (figureCropped.width() * 0.27));
        double[] clubsPixel2 = grayCropped.get((int) (figureCropped.height() * 0.33), (int) (figureCropped.width() * 0.73));
        double[] clubsPixel3 = grayCropped.get((int) (figureCropped.height() * 0.15), (int) (figureCropped.width() * 0.25));

        double[] diamondsPixel = grayCropped.get((int) (figureCropped.height() * 0.11), (int) (figureCropped.width() * 0.2));
        double[] diamondsPixel2 = grayCropped.get((int) (figureCropped.height() * 0.11), (int) (figureCropped.width() * 0.8));
        double[] diamondsPixel3 = grayCropped.get((int) (figureCropped.height() * 0.8), (int) (figureCropped.width() * 0.5));
        double[] diamondsPixel4 = grayCropped.get((int) (figureCropped.height() * 0.2), (int) (figureCropped.width() * 0.5));

        String output = "";

        //If center pixel is black
        if (centerPixel[2] < 100 && clubsPixel3[0] > 100) {
            if (clubsPixel[0] < 100 && clubsPixel2[0] < 100) {
                output = "Spades ";
                GUI.getInstance().showResult(figureCropped, output + count);
            }
            if (clubsPixel[0] > 100 && clubsPixel2[0] > 100) {
                output = "Clubs ";
                GUI.getInstance().showResult(figureCropped, output + count);
            }

        }

        //If center pixel is red
        else if (centerPixel[2] > 100 && diamondsPixel3[0] < 100 && diamondsPixel4[0] < 100) {
            if (diamondsPixel[0] < 100 && diamondsPixel2[0] < 100) {
                output = "Hearts ";
                GUI.getInstance().showResult(figureCropped, output + count);
            }

            if (diamondsPixel[0] > 100 && diamondsPixel2[0] > 100) {
                output = "Diamonds ";
                GUI.getInstance().showResult(figureCropped, output + count);
            }

        }


    }

    public void recNumbers(Mat frame, Rect figure, int count) {
        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);

        int bottom = (int) (figureCropped.height() * 0.9);
        int left = (int) (figureCropped.width() * 0.2);
        int right = (int) (figureCropped.width() * 0.8);
        int top = (int) (figureCropped.height() * 0.1);
        int center = (int) (figureCropped.height() * 0.5);

        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, THRESHOLD, 255, Imgproc.THRESH_BINARY);

        boolean centerPixel = isPixelAreaColorBlack(grayCropped, figureCropped.height() / 2, figureCropped.width() / 2, 2);
        boolean tlPixel = isPixelAreaColorBlack(grayCropped, top, left, 2);
        boolean blPixel = isPixelAreaColorBlack(grayCropped, bottom, left, 2);
        boolean trPixel = isPixelAreaColorBlack(grayCropped, top, right, 2);
        boolean brPixel = isPixelAreaColorBlack(grayCropped, bottom, right, 2);
        boolean crPixel = isPixelAreaColorBlack(grayCropped, center, right, 2);
        boolean clPixel = isPixelAreaColorBlack(grayCropped, center, left, 2);

        figureCropped.put(figureCropped.height() / 2, figureCropped.width() / 2, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(top, left, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(bottom, left, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(top, right, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(bottom, right, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(center, right, new double[]{0.0, 255.0, 0.0});
        figureCropped.put(center, left, new double[]{0.0, 255.0, 0.0});

        String output = "tal";

        if (centerPixel && tlPixel && blPixel && trPixel && brPixel && crPixel && clPixel) {
            output = "OTTE ";
        }
        if (!centerPixel && !tlPixel && !blPixel && trPixel && brPixel && crPixel && clPixel) {
            output = "FIRE ";
        }
//        if (centerPixel[0] > 100 && numberPixel1[0] < 100 && numberPixel2[0] < 100 && numberPixel3[0] < 100 && numberPixel4[0] < 100 && numberPixel5[0] < 100 && numberPixel6[0] < 100) {
//            output = "TI ";
//        }
//        if (centerPixel[0] < 100 && numberPixel1[0] < 100 && numberPixel2[0] < 100 && numberPixel3[0] < 100 && numberPixel4[0] < 100 && numberPixel5[0] > 100 && numberPixel6[0] > 100) {
//            output = "TO ";
//        }
//        if (centerPixel[0] > 100 && numberPixel1[0] > 100 && numberPixel2[0] > 100 && numberPixel3[0] < 100 && numberPixel4[0] < 100 && numberPixel5[0] < 100 && numberPixel6[0] < 100) {
//            output = "FIRE ";
//        }

        GUI.getInstance().showResult(grayCropped, output + count);

    }


    public boolean isPixelAreaColorBlack(Mat image, int row, int col, int area) {
        if (area == 1) {
            image.put(row, col, new double[]{255.0, 0.0, 0.0});
            if (image.get(row, col)[0] > 100) {
                return false;
            } else {
                return true;
            }


        }

        int whitecounter = 0;
        int blackcounter = 0;
        for (int i = -(area / 2); i < area / 2; i++) {
            for (int j = -(area / 2); j < area / 2; j++) {
                if (image.get(row + j, col + i)[0] > 100) {
                    whitecounter++;
                } else {
                    blackcounter++;
                }
                image.put(row + j, col + i, new double[]{0.0, 255.0, 0.0});
            }
        }
        return whitecounter > blackcounter ? false : true;

    }

}
