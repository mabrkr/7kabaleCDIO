import nu.pattern.OpenCV;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();


        OpenCV.loadShared();
        SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
        Mat snapshot = snapshotCapturer.captureSnapshot();

        CardProcessor cp = new CardProcessor();
        List<Card> listOfCards = cp.detectCards(snapshot);
        List<Card> finalListOfCards = new ArrayList<>();

        for (Card card : listOfCards) {
            cp.findCornerContours(snapshot, card);
            if (card.suit != ' ' && card.number != ' ') {
                finalListOfCards.add(card);
            }
        }

        for (Card card : finalListOfCards) {
            GUI.getInstance().showResult(snapshot, "" + card.number + card.suit);
        }


        System.out.println(finalListOfCards.size() + " cards found! (rigtigt)");
        System.out.println(finalListOfCards.toString());
        System.out.println("Running time: " + (System.currentTimeMillis() - startTime + "ms"));

    }


}
