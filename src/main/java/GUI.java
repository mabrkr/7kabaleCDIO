import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 * Singleton for showing the pictures to the users
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class GUI {
    private static GUI single_instance = null;

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    private JFrame jframe;

    private GUI() {
    }

    public static GUI getInstance() {
        if (single_instance == null)
            single_instance = new GUI();

        return single_instance;
    }

    public void showResult(Mat img, String title) {
        Imgproc.resize(img, img, new Size(WIDTH, HEIGHT));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);

            jframe = new JFrame();
            if(title != null) jframe.setTitle(title);
            jframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jframe.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            jframe.pack();
            jframe.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
