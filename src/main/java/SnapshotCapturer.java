import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

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
//
//        String filenameFaceCascade = "C:\\Users\\hemer\\IdeaProjects\\7kabaleCDIO\\src\\main\\java\\haarcascade_frontalface_alt.xml";
//
//        CascadeClassifier faceCascade = new CascadeClassifier();
//        faceCascade.load(filenameFaceCascade);

        // Argumentet angiver hvilket kamera (0 = standardkameraet)
//        VideoCapture capture = new VideoCapture(1);
//
//        Mat frame = new Mat();
//
//        if (!capture.isOpened()) {
//            throw new IOException("Kamerafejl. Tjek evt. om det rigtige kamera anvendes.");
//        } else {
//            capture.read(frame);
//            //detectAndDisplay(frame, faceCascade);
//            detectAndDisplayDigit(frame);
//
//
//        }

        Mat frame;
        frame = Imgcodecs.imread("resources/snapshots/Joe2.jpg");
        return frame;
    }

}
