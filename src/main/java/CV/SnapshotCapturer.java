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
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SnapshotCapturer {
    private JFrame jframe;

    // Ref: https://www.tutorialspoint.com/opencv/opencv_using_camera.htm
    public void captureSnapshot() throws IOException {
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

        Mat frame = new Mat();
        frame = Imgcodecs.imread("resources/snapshots/Joe2.jpg");
        detectAndDisplayDigit(frame);
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

    public void detectAndDisplayDigit(Mat frame) {
        Mat orgFrame = frame.clone();
        Mat frameGray = new Mat();
        Mat frameBlurred = new Mat();
        Mat frameThresh = new Mat();


        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(frameGray, frameBlurred, new Size(5, 5), 0);
        Imgproc.threshold(frameBlurred, frameThresh, 160, 255, Imgproc.THRESH_BINARY);
        //Imgproc.adaptiveThreshold(frameBlurred, frameThresh, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11,2);

        //Imgproc.Canny(frameThresh, frameEdged, 20, 20);

        List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
        List<Rect> cardCnts = new ArrayList<Rect>();

        //Use Imgproc.RETR_EXTERNAL for cards and Imgproc.RETR_TREE for all contours.
        Imgproc.findContours(frameThresh, cnts, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint p : cnts) {
            Rect rect = Imgproc.boundingRect(p);

            if ((rect.width >= 100 && rect.height >= 100) && (rect.width <= 500 && rect.height <= 500)) {
                cardCnts.add(rect);
                Imgproc.rectangle(orgFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            }
        }

        System.out.println("Der er " + cardCnts.size() + " spillekort på bordet!");
        //showResult(frameThresh);
        //showResult(frame);

        for (int i = 0; i < 10; i++)
        {
            Mat cropped = new Mat(frame, cardCnts.get(i));
            Mat ultraCropped = new Mat(cropped, new Rect(0,0,50,150));
            showResult(ultraCropped);
        }
    }


    public void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(1000, 800));
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
