import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class SnapshotCapturer {

    // Ref: https://www.tutorialspoint.com/opencv/opencv_using_camera.htm
    public void captureSnapshot() throws IOException {

//        WritableImage writableImage;

        // Argumentet angiver hvilket kamera (0 = standardkameraet)
        VideoCapture capture = new VideoCapture(0);
        Mat frame = new Mat();

        if (!capture.isOpened()) {
            throw new IOException("Kamerafejl. Tjek om evt. om det rigtige kamera anvendes.");
        } else {
            capture.read(frame);

            BufferedImage bufferedImage = new BufferedImage(
                    frame.width(),
                    frame.height(),
                    BufferedImage.TYPE_3BYTE_BGR);

            WritableRaster raster = bufferedImage.getRaster();
            DataBuffer dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = ((DataBufferByte) dataBuffer).getData();
            frame.get(0, 0, data);

//            writableImage = SwingFXUtils.toFXImage(bufferedImage, null);

            try {
                File file = new File(
                        "resources"
                                + File.separator
                                + "snapshots"
                                + File.separator
                                + "snapshot.png");

                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//            File file = new File(
//                    "resources"
//                            + File.separator
//                            + "snapshots"
//                            + File.separator
//                            + "snapshot.png");
//
//            BufferedImage output = SwingFXUtils.fromFXImage(writableImage, null);
//
//            ImageIO.write(output, "png", file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
