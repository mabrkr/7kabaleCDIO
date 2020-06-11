import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Class to either insert a picture through a path, or taking a picture with the clients webcam
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
//        frame = Imgcodecs.imread("resources/test_images/AllCards.jpg");
//        frame = Imgcodecs.imread("resources/test_images/CAROLBASKIN.jpg");
//        frame = Imgcodecs.imread("resources/test_images/TestSortB.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek1.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek2.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek3.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek4.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek5.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek6.jpg");
//        frame = Imgcodecs.imread("resources/test_images/Traek7.jpg");
        frame = Imgcodecs.imread("resources/test_images/TraekPhotoshop.jpg");
//        frame = Imgcodecs.imread("resources/test_images/TEEEEEST.jpg");

//        frame = Imgcodecs.imread("resources/test_images/1.jpg");
        return frame;
    }

}
