import nu.pattern.OpenCV;
import org.opencv.core.Mat;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();


        OpenCV.loadShared();
        SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
        Mat snapshot = snapshotCapturer.captureSnapshot();

        CardProcessor cp = new CardProcessor();
        List<Card> listOfCards = cp.detectCards(snapshot);
        for (Card card : listOfCards) {
            cp.findCornerContours(snapshot, card);
        }

//        System.out.println(listOfCards.toString());
        System.out.println("Running time: " + (System.currentTimeMillis() - startTime + "ms"));

    }


}
