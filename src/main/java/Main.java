import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        OpenCV.loadShared();

        SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
        Mat snapshot = snapshotCapturer.captureSnapshot();

        CardProcessor cp = new CardProcessor();
        List<Rect> listOfCards = cp.detectCards(snapshot);
        cp.findCornerContours(snapshot, listOfCards);

    }


}
