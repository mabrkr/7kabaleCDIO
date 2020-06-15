import model.Card;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.*;

/**
 * A singleton class for classifying numbers/values and figures
 *
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class Detector {

    private static Detector single_instance = null;

    Map<Coordinates[], Integer> numbers = new HashMap<>();

    /**
     * initialising coordinates to the card to make a pattern of pixels for recognising numbers
     */
    private Detector() {
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.1, 0.5),
                        new Coordinates(0.3, 0.4),
                        new Coordinates(0.3, 0.6),
                        new Coordinates(0.5, 0.3),
                        new Coordinates(0.5, 0.7),
                        new Coordinates(0.7, 0.3),
                        new Coordinates(0.7, 0.7),
                        new Coordinates(0.7, 0.5),
                        new Coordinates(0.925, 0.2),
                        new Coordinates(0.925, 0.8),
                        new Coordinates(0.925, 0.1),
                        new Coordinates(0.925, 0.9),
                        new Coordinates(0.925, 0.65)
                }
                , 1);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.1, 0.2),
                        new Coordinates(0.9, 0.2),
                        new Coordinates(0.1, 0.8),
                        new Coordinates(0.9, 0.8),
                        new Coordinates(0.5, 0.5),
                        new Coordinates(0.4, 0.7),
                        new Coordinates(0.6, 0.3),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.95, 0.5),
                        new Coordinates(0.3, 0.15),
                        new Coordinates(0.7, 0.15),
                        new Coordinates(0.3, 0.85),
                        new Coordinates(0.8, 0.8)
                }
                , 2);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.2, 0.2),
                        new Coordinates(0.075, 0.2),
                        new Coordinates(0.075, 0.4),
                        new Coordinates(0.075, 0.6),
                        new Coordinates(0.075, 0.8),
                        new Coordinates(0.175, 0.7),
                        new Coordinates(0.25, 0.6),
                        new Coordinates(0.35, 0.5),
                        new Coordinates(0.925, 0.5),
                        new Coordinates(0.85, 0.15),
                        new Coordinates(0.5, 0.775),
                        new Coordinates(0.825, 0.775),
                        new Coordinates(0.65, 0.875),
                }
                , 3);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.1, 0.75),
                        new Coordinates(0.3, 0.75),
                        new Coordinates(0.9, 0.75),
                        new Coordinates(0.5, 0.75),
                        new Coordinates(0.7, 0.75),
                        new Coordinates(0.7, 0.5),
                        new Coordinates(0.7, 0.3),
                        new Coordinates(0.7, 0.1),
                        new Coordinates(0.6, 0.2),
                        new Coordinates(0.7, 0.9),
                        new Coordinates(0.3, 0.5),
                        new Coordinates(0.4, 0.4),
                        new Coordinates(0.5, 0.3)
                }
                , 4);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.075, 0.5),
                        new Coordinates(0.075, 0.15),
                        new Coordinates(0.075, 0.75),
                        new Coordinates(0.3, 0.2),
                        new Coordinates(0.5, 0.2),
                        new Coordinates(0.375, 0.5),
                        new Coordinates(0.425, 0.7),
                        new Coordinates(0.425, 0.3),
                        new Coordinates(0.925, 0.5),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.7, 0.85),
                        new Coordinates(0.875, 0.25),
                        new Coordinates(0.725, 0.15)
                }
                , 5);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.225, 0.85),
                        new Coordinates(0.85, 0.25),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.4, 0.5),
                        new Coordinates(0.45, 0.7),
                        new Coordinates(0.45, 0.3),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.95, 0.5),
                        new Coordinates(0.3, 0.15),
                        new Coordinates(0.7, 0.15),
                        new Coordinates(0.5, 0.1),
                        new Coordinates(0.7, 0.85)
                }
                , 6);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.075, 0.1),
                        new Coordinates(0.2, 0.1),
                        new Coordinates(0.075, 0.3),
                        new Coordinates(0.075, 0.4),
                        new Coordinates(0.075, 0.5),
                        new Coordinates(0.075, 0.6),
                        new Coordinates(0.075, 0.7),
                        new Coordinates(0.075, 0.8),
                        new Coordinates(0.075, 0.9),
                        new Coordinates(0.9, 0.4),
                        new Coordinates(0.7, 0.5),
                        new Coordinates(0.5, 0.6),
                        new Coordinates(0.3, 0.7),
                        new Coordinates(0.2, 0.8)
                }
                , 7);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.15, 0.75),
                        new Coordinates(0.85, 0.25),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.45, 0.5),
                        new Coordinates(0.45, 0.7),
                        new Coordinates(0.45, 0.3),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.95, 0.5),
                        new Coordinates(0.35, 0.175),
                        new Coordinates(0.7, 0.15),
                        new Coordinates(0.35, 0.825),
                        new Coordinates(0.7, 0.85)
                }
                , 8);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.15, 0.75),
                        new Coordinates(0.85, 0.25),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.6, 0.5),
                        new Coordinates(0.55, 0.7),
                        new Coordinates(0.55, 0.3),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.925, 0.5),
                        new Coordinates(0.35, 0.175),
                        new Coordinates(0.75, 0.1),
                        new Coordinates(0.35, 0.825),
                        new Coordinates(0.7, 0.85)
                }
                , 9);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.075, 0.5),
                        new Coordinates(0.075, 0.6),
                        new Coordinates(0.075, 0.7),
                        new Coordinates(0.075, 0.8),
                        new Coordinates(0.075, 0.9),
                        new Coordinates(0.9, 0.4),
                        new Coordinates(0.7, 0.675),
                        new Coordinates(0.5, 0.675),
                        new Coordinates(0.3, 0.675),
                        new Coordinates(0.2, 0.675),
                        new Coordinates(0.75, 0.1),
                        new Coordinates(0.85, 0.2),
                        new Coordinates(0.825, 0.625)
                }
                , 11);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.15, 0.75),
                        new Coordinates(0.85, 0.3),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.625, 0.5),
                        new Coordinates(0.75, 0.7),
                        new Coordinates(0.55, 0.3),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.925, 0.5),
                        new Coordinates(0.35, 0.25),
                        new Coordinates(0.575, 0.1),
                        new Coordinates(0.35, 0.8),
                        new Coordinates(0.7, 0.8)
                }
                , 12);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.075, 0.15),
                        new Coordinates(0.075, 0.85),
                        new Coordinates(0.9, 0.15),
                        new Coordinates(0.9, 0.85),
                        new Coordinates(0.1, 0.25),
                        new Coordinates(0.3, 0.25),
                        new Coordinates(0.5, 0.25),
                        new Coordinates(0.7, 0.25),
                        new Coordinates(0.9, 0.25),
                        new Coordinates(0.8, 0.725),
                        new Coordinates(0.625, 0.575),
                        new Coordinates(0.3, 0.575),
                        new Coordinates(0.5, 0.5)
                }
                , 13);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.10, 0.5),
                        new Coordinates(0.15, 0.5),
                        new Coordinates(0.20, 0.5),
                        new Coordinates(0.25, 0.5),
                        new Coordinates(0.35, 0.5),
                        new Coordinates(0.40, 0.5),
                        new Coordinates(0.45, 0.5),
                        new Coordinates(0.50, 0.5),
                        new Coordinates(0.55, 0.5),
                        new Coordinates(0.65, 0.5),
                        new Coordinates(0.75, 0.5),
                        new Coordinates(0.85, 0.5),
                        new Coordinates(0.90, 0.5),
                }
                , 10);
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.15, 0.75),
                        new Coordinates(0.85, 0.25),
                        new Coordinates(0.85, 0.75),
                        new Coordinates(0.45, 0.85),
                        new Coordinates(0.45, 0.15),
                        new Coordinates(0.6, 0.15),
                        new Coordinates(0.05, 0.5),
                        new Coordinates(0.95, 0.5),
                        new Coordinates(0.35, 0.175),
                        new Coordinates(0.7, 0.15),
                        new Coordinates(0.35, 0.825),
                        new Coordinates(0.7, 0.85)
                }
                , 10);

    }

    public static Detector getInstance() {
        if (single_instance == null)
            single_instance = new Detector();

        return single_instance;
    }

    /**
     * @param frame     openCV representation of an image
     * @param figure    position of the figure on the frame representated as a rectangle
     * @param threshold an integer representing the threshold for making the image black and white.
     * @return the best matching number/value as a char
     */
    public int recValue(Mat frame, Rect figure, int threshold) {
        Map.Entry<Coordinates[], Integer> closestMatch = null;

        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);

        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, threshold, 255, Imgproc.THRESH_BINARY);

        int matches = 0;

        for (Map.Entry<Coordinates[], Integer> entry : numbers.entrySet()) {
            int currentMatches = 0;
            for (int i = 0; i < entry.getKey().length; i++) {

                if (grayCropped.get((int) (grayCropped.height() * entry.getKey()[i].row), (int) (grayCropped.width() * entry.getKey()[i].col))[0] < 100) {
                    currentMatches++;
                }
            }
            if (currentMatches > matches && currentMatches >= 10) {
                matches = currentMatches;
                closestMatch = entry;
            }
        }

        if (closestMatch != null) {
            for (int i = 0; i < closestMatch.getKey().length; i++) {
                figureCropped.put((int) (grayCropped.height() * closestMatch.getKey()[i].row), (int) (grayCropped.width() * closestMatch.getKey()[i].col), 0.0, 255.0, 0.0);
            }
        }

        if (closestMatch != null) {
            return closestMatch.getValue();
        }
        return -1;


    }

    /**
     * @param frame     openCV representation of an image
     * @param figure    position of the figure on the frame representated as a rectangle
     * @param threshold an integer representing the threshold for making the image black and white.
     * @return the best matching suit as a char
     */
    public Card.Suit recSuit(Mat frame, Rect figure, int threshold) {


        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);


        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, threshold, 255, Imgproc.THRESH_BINARY);

        double[] centerPixel = figureCropped.get(figureCropped.height() / 2, figureCropped.width() / 2);

        double[] clubsPixel = grayCropped.get((int) (figureCropped.height() * 0.33), (int) (figureCropped.width() * 0.27));
        double[] clubsPixel2 = grayCropped.get((int) (figureCropped.height() * 0.33), (int) (figureCropped.width() * 0.73));
        double[] clubsPixel3 = grayCropped.get((int) (figureCropped.height() * 0.15), (int) (figureCropped.width() * 0.25));

        double[] diamondsPixel = grayCropped.get((int) (figureCropped.height() * 0.11), (int) (figureCropped.width() * 0.35));
        double[] diamondsPixel2 = grayCropped.get((int) (figureCropped.height() * 0.11), (int) (figureCropped.width() * 0.65));


        double[] diamondsPixel3 = grayCropped.get((int) (figureCropped.height() * 0.8), (int) (figureCropped.width() * 0.5));

        Card.Suit output = null;

        //If center pixel is black
        if (centerPixel[2] < 100 && clubsPixel3[0] > 100) {
            if (clubsPixel[0] < 100 && clubsPixel2[0] < 100) {
                output = Card.Suit.SPADE;

            } else {
                output = Card.Suit.CLUB;

            }

        }

        //If center pixel is red
        else if (centerPixel[2] > 100 && diamondsPixel3[0] < 100) {
            if (diamondsPixel[0] < 100 && diamondsPixel2[0] < 100) {
                output = Card.Suit.HEART;

            } else {
                output = Card.Suit.DIAMOND;

            }

        }
        return output;

    }

    /**
     * Model for the coordinates
     */
    private class Coordinates {
        public double row;
        public double col;

        public Coordinates(double row, double col) {
            this.row = row;
            this.col = col;
        }
    }

}
