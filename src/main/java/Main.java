import Billedgenkendelse.CardProcessor;
import Billedgenkendelse.GUI;
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
        GUI.getInstance().startGUI();

        OpenCV.loadShared();
//        SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
//        Mat snapshot = snapshotCapturer.readFromFile("resources/test_images/Test2 (1).jpg");
//
//        CardProcessor cp = new CardProcessor();
//        List<Card> listOfCards = cp.detectCards(snapshot, 180);
//
//        System.out.println(listOfCards.size() + " cards found!");
//
//        GameSnapshot gameSnapshot = GameSnapshotFactory.fromPositionCards(listOfCards);
//
//        model.MoveCalculator moveCalculator = new model.MoveCalculator();
//        System.out.println(moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());

    }


}
