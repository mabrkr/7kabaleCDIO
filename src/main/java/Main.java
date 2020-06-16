import view.GUI;
import nu.pattern.OpenCV;

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
//        gamelogic.MoveCalculator moveCalculator = new gamelogic.MoveCalculator();
//        System.out.println(moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());

    }


}
