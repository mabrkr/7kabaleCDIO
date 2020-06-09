import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class CardProcessor {

    private final int THRESHOLD = 165;

    public List<Card> detectCards(Mat frame) {
        Mat orgFrame = frame.clone();
        Mat frameGray = new Mat();
        Mat frameBlurred = new Mat();
        Mat frameThresh = new Mat();

        //Image processing
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(frameGray, frameBlurred, new Size(5, 5), 0);
        Imgproc.threshold(frameBlurred, frameThresh, THRESHOLD, 255, Imgproc.THRESH_BINARY);


        List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
        List<Card> cards = new ArrayList<Card>();

        //Use Imgproc.RETR_EXTERNAL for cards and Imgproc.RETR_TREE for all contours.
        Imgproc.findContours(frameThresh, cnts, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint p : cnts) {
            Rect rect = Imgproc.boundingRect(p);

            //Filter out small and large contours by size
            if ((rect.width >= 100 && rect.height >= 100) && (rect.width <= 350 && rect.height <= 500)) {
                cards.add(new Card(rect.x, rect.y, ' ', ' ', rect));
                Imgproc.rectangle(orgFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            }
        }

        GUI.getInstance().showResult(orgFrame, "");
        GUI.getInstance().showResult(frameThresh, "Threshold");
        System.out.println(cards.size() + " cards found!");

        return cards;
    }

    public void findCornerContours(Mat frame, Card card) {
        List<Rect> listOfFigures = new ArrayList<Rect>();

        //Crops the corner of the card
        Mat cardCropped = new Mat(frame, card.rectangle);
        Mat cornerCropped = new Mat(cardCropped, new Rect(0, 0, cardCropped.width() / 4, cardCropped.height() / 3));

        //Find the contours (figures and numbers) on each corner
        int cntscount = 0;

        List<MatOfPoint> croppedCnts = new ArrayList<MatOfPoint>();
        Mat mGray = new Mat();

        //Image processing
        Imgproc.cvtColor(cornerCropped, mGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(mGray, mGray, THRESHOLD, 255, Imgproc.THRESH_BINARY);
        Imgproc.findContours(mGray, croppedCnts, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint point : croppedCnts) {
            Rect rect = Imgproc.boundingRect(point);

            //Filter out small and large contours by size
            if ((rect.width >= 8 && rect.height >= 31) && (rect.width <= 100 && rect.height <= 100)) {
                listOfFigures.add(rect);

                if (cntscount > 2) {
                    card.number = 'T';
                } else {
                    if (rect.y < cornerCropped.height() * 0.5) {
                        card.number = Detector.getInstance().recNumber(cornerCropped, rect, THRESHOLD);
                    }
                    if (rect.y > cornerCropped.height() * 0.5) {
                        card.suit = Detector.getInstance().recFigure(cornerCropped, rect, THRESHOLD);
                    }
                }

                Imgproc.rectangle(cornerCropped, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 1);
                cntscount++;
            }
        }
//        GUI.getInstance().showResult(cornerCropped, "" + card.number + card.suit);
    }


}
