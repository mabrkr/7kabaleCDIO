package CV;

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
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SnapshotCapturer {
    private JFrame jframe;

    // Ref: https://www.tutorialspoint.com/opencv/opencv_using_camera.htm
    public void captureSnapshot() throws IOException {

        String filenameFaceCascade = "/home/Jeppe/IdeaProjects/7kabaleCDIO/src/main/java/haarcascade_frontalface_alt.xml";

        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load(filenameFaceCascade);

        // Argumentet angiver hvilket kamera (0 = standardkameraet)
        VideoCapture capture = new VideoCapture(0);

        Mat frame = new Mat();

        if (!capture.isOpened()) {
            throw new IOException("Kamerafejl. Tjek evt. om det rigtige kamera anvendes.");
        } else {
            capture.read(frame);
            detectAndDisplay(frame, faceCascade);

        }
    }


    public void detectAndDisplay(Mat frame, CascadeClassifier faceCascade) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);
        // -- Detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frameGray, faces);
        List<Rect> listOfFaces = faces.toList();
        for (Rect face : listOfFaces) {
            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.ellipse(frame, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
                    new Scalar(255, 0, 255));
            Mat faceROI = frameGray.submat(face);
        }
        //-- Show what you got
        showResult(frame);
    }


    public void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);

            jframe = new JFrame();

            jframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jframe.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            jframe.pack();
            jframe.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
