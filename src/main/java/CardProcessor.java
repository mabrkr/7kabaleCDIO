import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Method for detectin cards and adding contours
 *
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class CardProcessor {

    private int threshold;

    /**
     * Joe exotic
     *
     * @param frame openCV representation of a .jpg
     * @return A list of card objects
     */
    public List<Card> detectCards(Mat frame, int threshold) {
        this.threshold = threshold;

        Mat orgFrame = frame.clone();
        Mat frameGray = new Mat();
        Mat frameBlurred = new Mat();
        Mat frameThresh = new Mat();

        //Image processing
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(frameGray, frameBlurred, new Size(5, 5), 0);
        Imgproc.threshold(frameBlurred, frameThresh, threshold, 255, Imgproc.THRESH_BINARY);


        List<MatOfPoint> cnts = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        List<Card> finalListOfCards = new ArrayList<>();

        //Use Imgproc.RETR_EXTERNAL for cards and Imgproc.RETR_TREE for all contours.
        Imgproc.findContours(frameThresh, cnts, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint p : cnts) {
            Rect rect = Imgproc.boundingRect(p);

            //Filter out small and large contours by size
            if ((rect.width >= 100 && rect.height >= 10) && (rect.width <= 350 && rect.height <= 600)) {
                cards.add(new Card(rect.x, rect.y, ' ', ' ', rect));
                Imgproc.rectangle(orgFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            }
        }

        //TODO: Slet dette når alle test er færdige
        GUI.getInstance().showResult(orgFrame, "");
        GUI.getInstance().showResult(frameThresh, "Threshold");

        //Clear out 'false' cards by adding them to a new array.
        for (Card card : cards) {
            identifyCards(frame, card);
            if (card.suit != ' ' && card.number != ' ') {
                finalListOfCards.add(card);
            }
        }

        return finalListOfCards;
    }

    /**
     * Adds value and suit to a card, this method also checks if the card is a 10
     *
     * @param frame
     * @param card
     */
    public void identifyCards(Mat frame, Card card) {
        //Crops the corner of the card
        Mat cardCropped = new Mat(frame, card.rectangle);
        Mat cornerCropped;

        if (card.rectangle.height > 400) {
            cornerCropped = new Mat(cardCropped, new Rect(0, 0, cardCropped.width() / 4, cardCropped.height() / 3));

        } else {
            cornerCropped = new Mat(cardCropped, new Rect(0, 0, cardCropped.width() / 4, cardCropped.height()));
        }

        //Find the contours (figures and numbers) on each corner
        int cntscount = 0;

        List<MatOfPoint> croppedCnts = new ArrayList<>();
        Mat mGray = new Mat();

        //Image processing
        Imgproc.cvtColor(cornerCropped, mGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(mGray, mGray, threshold, 255, Imgproc.THRESH_BINARY);
        Imgproc.findContours(mGray, croppedCnts, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint point : croppedCnts) {
            Rect rect = Imgproc.boundingRect(point);

            //Filter out small and large contours by size
            if ((rect.width >= 8 && rect.height >= 31) && (rect.width <= 100 && rect.height <= 100)) {

                if (cntscount > 2) {
                    card.number = 'T';
                } else {
                    if (rect.y < cornerCropped.height() * 0.3) {

                        card.number = Detector.getInstance().recNumber(cornerCropped, rect, threshold);
                    }
                    if (rect.y > cornerCropped.height() * 0.3) {
                        card.suit = Detector.getInstance().recFigure(cornerCropped, rect, threshold);
                    }
                }

                Imgproc.rectangle(cornerCropped, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 1);
                cntscount++;
            }
        }

        //TODO: Slet dette når alle test er færdige
        if (card.suit != ' ' && card.number != ' ') {
            GUI.getInstance().showResult(cornerCropped, "" + card.number + card.suit);
        }
    }


}
