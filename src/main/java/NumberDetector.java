import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.*;

public class NumberDetector {

    Map<Coordinates[], Character> numbers = new HashMap<Coordinates[], Character>();

    public NumberDetector() {
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
                , '8');
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
                , '9');
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
                , 'Q');
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
                , '3');
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.15, 0.25),
                        new Coordinates(0.15, 0.75),
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
                , '6');
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.075, 0.5),
                        new Coordinates(0.075, 0.25),
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
                , '5');

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
                , '2');
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
                , '4');

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
                , 'A');
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
                , '7');
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
                , 'J');
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
                , 'K');
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.2, 0.15),
                        new Coordinates(0.4, 0.15),
                        new Coordinates(0.1, 0.3),
                        new Coordinates(0.175, 0.4),
                        new Coordinates(0.25, 0.5),
                        new Coordinates(0.175, 0.6),
                        new Coordinates(0.1, 0.7),
                        new Coordinates(0.4, 0.85),
                        new Coordinates(0.2, 0.85),
                        new Coordinates(0.7, 0.4),
                        new Coordinates(0.8, 0.5),
                        new Coordinates(0.6, 0.75),
                        new Coordinates(0.6, 0.25),
                        new Coordinates(0.7, 0.6)

                }
                , 'H'); //hearts
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.5, 0.1),
                        new Coordinates(0.5, 0.9),
                        new Coordinates(0.1, 0.5),
                        new Coordinates(0.9, 0.5),
                        new Coordinates(0.4, 0.225),
                        new Coordinates(0.4, 0.775),
                        new Coordinates(0.275, 0.35),
                        new Coordinates(0.275, 0.65),
                        new Coordinates(0.8, 0.35),
                        new Coordinates(0.8, 0.6),
                        new Coordinates(0.65, 0.275),
                        new Coordinates(0.7, 0.7),
                        new Coordinates(0.6, 0.8),
                        new Coordinates(0.0785, 0.5)

                }
                , 'D');//Diamonds

        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.1, 0.4),
                        new Coordinates(0.25, 0.35),
                        new Coordinates(0.85, 0.5),
                        new Coordinates(0.5, 0.1),
                        new Coordinates(0.5, 0.9),
                        new Coordinates(0.1, 0.6),
                        new Coordinates(0.7, 0.9),
                        new Coordinates(0.75, 0.75),
                        new Coordinates(0.45, 0.8),
                        new Coordinates(0.45, 0.2),
                        new Coordinates(0.7, 0.1),
                        new Coordinates(0.25, 0.65),
                        new Coordinates(0.75, 0.25)


                }
                , 'C');//Clubs
        numbers.put(
                new Coordinates[]{
                        new Coordinates(0.1, 0.5),
                        new Coordinates(0.25, 0.35),
                        new Coordinates(0.85, 0.5),
                        new Coordinates(0.5, 0.1),
                        new Coordinates(0.5, 0.9),
                        new Coordinates(0.1, 0.5),
                        new Coordinates(0.7, 0.9),
                        new Coordinates(0.75, 0.75),
                        new Coordinates(0.32, 0.725),
                        new Coordinates(0.32, 0.275),
                        new Coordinates(0.7, 0.1),
                        new Coordinates(0.25, 0.65),
                        new Coordinates(0.75, 0.25)


                }
                , 'S');//SPARE

    }


    public Character recNumber(Mat frame, Rect figure, int threshold) {
        Map.Entry<Coordinates[], Character> closestMatch = null;

        Mat figureCropped = new Mat(frame, figure);
        Mat grayCropped = new Mat(frame, figure);

        Imgproc.cvtColor(figureCropped, grayCropped, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayCropped, grayCropped, threshold, 255, Imgproc.THRESH_BINARY);

        int matches = 0;

        for (Map.Entry<Coordinates[], Character> entry : numbers.entrySet()) {
            int currentMatches = 0;
            for (int i = 0; i < entry.getKey().length; i++) {

                if (grayCropped.get((int) (grayCropped.height() * entry.getKey()[i].row), (int) (grayCropped.width() * entry.getKey()[i].col))[0] < 100) {
                    currentMatches++;
                }
            }
            if (currentMatches > matches && currentMatches >= 12) {
                matches = currentMatches;
                closestMatch = entry;
            }
        }

        if (closestMatch != null) {
            for (int i = 0; i < closestMatch.getKey().length; i++) {
                figureCropped.put((int) (grayCropped.height() * closestMatch.getKey()[i].row), (int) (grayCropped.width() * closestMatch.getKey()[i].col), new double[]{0.0, 255.0, 0.0});
            }
        }

        if (closestMatch != null) {
            GUI.getInstance().showResult(figureCropped, closestMatch.getValue().toString());
            return closestMatch.getValue();
        }
        return ' ';


    }

    private class Coordinates {
        public double row;
        public double col;

        public Coordinates(double row, double col) {
            this.row = row;
            this.col = col;
        }
    }

}
