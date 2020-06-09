import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

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
        frame = Imgcodecs.imread("resources/test_images/AllCards.jpg");
        return frame;
    }

}
