import nu.pattern.OpenCV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardProcessorTest {
    private static CardProcessor cp;

    @BeforeAll
    static void setUp() {
        OpenCV.loadShared();
        cp = new CardProcessor();
    }

    @Test
    void detectCards() {
        Mat frame;
        frame = Imgcodecs.imread("resources/test_images/1.jpg");
        assertEquals(1, cp.detectCards(frame).size());

        frame = Imgcodecs.imread("resources/test_images/2.jpg");
        assertEquals(1, cp.detectCards(frame).size());

        frame = Imgcodecs.imread("resources/test_images/AllCards.jpg");
        assertEquals(52, cp.detectCards(frame).size());

        frame = Imgcodecs.imread("resources/test_images/Kabale.jpg");
        assertEquals(7, cp.detectCards(frame).size());
    }

    @Test
    void findCornerContours() {
        Mat frame;
        frame = Imgcodecs.imread("resources/test_images/1.jpg");
        List<Card> listOfCards = cp.detectCards(frame);

        for (Card card : listOfCards) {
            cp.findCornerContours(frame, card);
        }

        assertEquals('A', listOfCards.get(0).number);
        assertEquals('S', listOfCards.get(0).suit);
    }


}