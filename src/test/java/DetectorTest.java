import nu.pattern.OpenCV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import static org.junit.jupiter.api.Assertions.*;

class DetectorTest {

    @BeforeAll
    static void setUp() {
        OpenCV.loadShared();
    }

    @Test
    void getInstance() {
        assertNotNull(Detector.getInstance());
    }

    @Test
    void recNumber() {
        Mat frame;
        frame = Imgcodecs.imread("resources/test_images/1.jpg");

        Rect cardRect = new Rect();
        cardRect.x = 6;
        cardRect.y = 10;
        cardRect.width = 317;
        cardRect.height = 480;

        Mat cardCropped = new Mat(frame, cardRect);
        Mat cornerCropped = new Mat(cardCropped, new Rect(0, 0, cardCropped.width() / 4, cardCropped.height() / 3));

        Rect rect = new Rect();
        rect.x = 12;
        rect.y = 27;
        rect.width = 38;
        rect.height = 58;

        assertEquals('A', Detector.getInstance().recNumber(cornerCropped, rect, 165));

    }

    @Test
    void recFigure() {
        Mat frame;
        frame = Imgcodecs.imread("resources/test_images/1.jpg");

        Rect cardRect = new Rect();
        cardRect.x = 6;
        cardRect.y = 10;
        cardRect.width = 317;
        cardRect.height = 480;

        Mat cardCropped = new Mat(frame, cardRect);
        Mat cornerCropped = new Mat(cardCropped, new Rect(0, 0, cardCropped.width() / 4, cardCropped.height() / 3));

        Rect rect = new Rect();
        rect.x = 16;
        rect.y = 88;
        rect.width = 30;
        rect.height = 40;

        assertEquals('S', Detector.getInstance().recFigure(cornerCropped, rect, 165));
    }
}