package image_recognition;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.IOException;

/**
 * Class to either insert a picture through a path or take a picture with the clients webcam
 *
 * @author Jeppe Kaare Larsen, Mads Martin Dickmeiss Hemer & Malte Brink Kristensen
 */

public class SnapshotCapturer {

    // Ref: https://www.tutorialspoint.com/opencv/opencv_using_camera.htm
    public Mat captureSnapshot() throws IOException {


//        The index parameter is used for choosing a connected camera, try -1, 0 or 1.
        VideoCapture capture = new VideoCapture(1);

        Mat frame = new Mat();

        if (!capture.isOpened()) {
            throw new IOException("Kamerafejl. Tjek evt. om det rigtige kamera anvendes.");
        } else {
            capture.read(frame);
        }
        return frame;

    }

    public Mat readFromFile(String path) {
        Mat frame;
        frame = Imgcodecs.imread(path);
        return frame;
    }

}
