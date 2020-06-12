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
        frame = Imgcodecs.imread("resources/test_images/AS.jpg");
        assertEquals(1, cp.detectCards(frame,165).size());

        frame = Imgcodecs.imread("resources/test_images/10C.jpg");
        assertEquals(1, cp.detectCards(frame,165).size());

        frame = Imgcodecs.imread("resources/test_images/AllCards.jpg");
        assertEquals(52, cp.detectCards(frame,163).size());

        frame = Imgcodecs.imread("resources/test_images/TraekPhotoshop.jpg");
        assertEquals(22, cp.detectCards(frame,200).size());

        frame = Imgcodecs.imread("resources/test_images/Test1.jpg");
        assertEquals(18, cp.detectCards(frame,210).size());

    }

    @Test
    void identifyCard() {
        Mat frame;
        frame = Imgcodecs.imread("resources/test_images/AS.jpg");
        List<Card> listOfCards = cp.detectCards(frame,165);

        assertEquals('A', listOfCards.get(0).number);
        assertEquals('S', listOfCards.get(0).suit);
    }


}