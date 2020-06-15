package Billedgenkendelse;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;

/**
 * Class to either insert a picture through a path, or taking a picture with the clients webcam
 *
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
        frame = Imgcodecs.imread("resources/test_images/Test2 (1).jpg");
        return frame;
    }

}