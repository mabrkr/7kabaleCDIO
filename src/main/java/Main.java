import Billedgenkendelse.CardProcessor;
import Billedgenkendelse.SnapshotCapturer;
import model.Card;
import model.GameSnapshot;
import model.GameSnapshotFactory;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;

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
        List<Card> listOfCards = cp.detectCards(snapshot, 180);

        System.out.println(listOfCards.size() + " cards found!");
        System.out.println(listOfCards.toString());
        System.out.println("Running time: " + (System.currentTimeMillis() - startTime + "ms"));

       GameSnapshot gameSnapshot = GameSnapshotFactory.fromPositionCards(listOfCards);

        MoveCalculator moveCalculator = new MoveCalculator();
        System.out.println(moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
        MaltesMain.printGameSnapshotToConsole(gameSnapshot);
    }


}
