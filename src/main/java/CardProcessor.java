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
                    reqFigure(m, rect, cntscount + 1);
                    Imgproc.rectangle(m, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 1);
                    cntscount++;
                }
            }
            GUI.getInstance().showResult(m, "");
        }
        System.out.println(cntscount);
    }

    public void reqFigure(Mat frame, Rect figure, int count) {


        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);

        int fjolleH = (int) (figureCropped.height() * 0.11);
        int fjolleW = (int) (figureCropped.width() * 0.2);

        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, THRESHOLD, 255, Imgproc.THRESH_BINARY);

        double[] centerPixel = figureCropped.get(figureCropped.height() / 2, figureCropped.width() / 2);
        double[] clubsPixel = grayCropped.get((int) (figureCropped.height() * 0.33), (int) (figureCropped.width() * 0.27));

        double[] diamondsPixel = grayCropped.get(fjolleH, fjolleW);

        figureCropped.put(fjolleH, fjolleW, new double[]{129.0, 215.0, 66.0});


        String output = "";

        //If center pixel is black
        if (centerPixel[2] < 100) {
            if (clubsPixel[0] < 100) output = "Spades ";
            if (clubsPixel[0] > 100) output = "Clubs ";
        }

        //If center pixel is red
        if (centerPixel[2] > 100) {
            if (diamondsPixel[0] < 100) output = "Hearts ";
            if (diamondsPixel[0] > 100) output = "Diamonds ";
        }


        GUI.getInstance().showResult(figureCropped, output + count);

    }

}
