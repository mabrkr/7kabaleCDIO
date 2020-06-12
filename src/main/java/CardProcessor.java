import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Method for detecting and identifying cards
 *
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class CardProcessor {

    private int threshold;

    /**
     * Method for detecting and returning cards in an image
     *
     * @param frame openCV representation of an image
     * @param threshold an integer representing the threshold for making the image black and white.
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
            if ((rect.width >= 100 && rect.height >= 10) && (rect.width <= 375 && rect.height <= 575)) {
                cards.add(new Card(rect.x, rect.y, ' ', ' ', rect));
                Imgproc.rectangle(orgFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            }
        }

        //TODO: Slet dette når alle test er færdige
        GUI.getInstance().showResult(orgFrame, "");
        GUI.getInstance().showResult(frameThresh, "Threshold");

        //Clear out 'false' cards by adding them to a new array.
        for (Card card : cards) {
            identifyCard(frame, card);
            if (card.suit != ' ' && card.number != ' ') {
                finalListOfCards.add(card);
            }
        }
//        return cards;
        return finalListOfCards;
    }

    /**
     * Adds number/value and suit to a card
     *
     * @param frame openCV representation of an image
     * @param card a card object. Often from the 'detectCards' method.
     */
    public void identifyCard(Mat frame, Card card) {
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
        List<Rect> cntsRects = new ArrayList<>();
        Mat mGray = new Mat();

        //Image processing
        Imgproc.cvtColor(cornerCropped, mGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(mGray, mGray, threshold, 255, Imgproc.THRESH_BINARY);
        Imgproc.findContours(mGray, croppedCnts, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint point : croppedCnts) {
            Rect rect = Imgproc.boundingRect(point);

            //Filter out small and large contours by size
            if ((rect.width >= 8 && rect.height >= 31) && (rect.width <= 115 && rect.height <= 115)) {
                cntsRects.add(rect);
            }

        }

        Comparator<Rect> compareByXCord = new Comparator<Rect>() {
            @Override
            public int compare(Rect o1, Rect o2) {
                Integer x1 = o1.x;
                Integer x2 = o2.x;

                return x1.compareTo(x2);
            }
        };

        cntsRects.sort(compareByXCord);

        if (cntsRects.size() >= 2) {
            for (int i = 0; i < 2; i++) {
                Rect rect = cntsRects.get(i);

                if (rect.y < cornerCropped.height() * 0.3) {

                    card.number = Detector.getInstance().recNumber(cornerCropped, rect, threshold);
                }
                if (rect.y > cornerCropped.height() * 0.3) {
                    card.suit = Detector.getInstance().recFigure(cornerCropped, rect, threshold);
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
