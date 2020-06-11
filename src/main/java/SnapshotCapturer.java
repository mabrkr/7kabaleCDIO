import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Class to either read a picture through a path, or take a picture with the user's webcam
 * @author Jeppe Kaare Larsen, Mads Martin Dickmeiss Hemer & Malte Brink Kristensen
 */

public class SnapshotCapturer {

    // Ref: https://www.tutorialspoint.com/opencv/opencv_using_camera.htm
    public Mat captureSnapshot() throws IOException {



////        Argumentet angiver hvilket kamera (0 = standardkameraet)
//        VideoCapture capture = new VideoCapture(0);
//
//        Mat frame = new Mat();
//
//        if (!capture.isOpened()) {
//            throw new IOException("Kamerafejl. Tjek evt. om det rigtige kamera anvendes.");
//        } else {
//            capture.read(frame);
//        }

        Mat frame;

//        frame = Imgcodecs.imread("resources/test_images/AllCards.jpg"); //Threshold 163

//        frame = Imgcodecs.imread("resources/test_images/EndGameTest.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Kabale.jpg");
        frame = Imgcodecs.imread("resources/test_images/Kabale2.jpg");
//        frame = Imgcodecs.imread("resources/test_images/FullCardTest.jpg");
//        frame = Imgcodecs.imread("resources/test_images/1.jpg");
        return frame;
    }

}
