import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Singleton for showing the pictures to the users
 *
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class GUI {
    private static GUI single_instance = null;

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    private JFrame jframe;
//    private JButton = new Button();

    private GUI() {
    }

    public static GUI getInstance() {
        if (single_instance == null)
            single_instance = new GUI();

        return single_instance;
    }

    public void showResult(Mat img, String title) {

        JLabel label = new JLabel("suggestion");
        label.setMinimumSize(new Dimension(1000, 100));

        JButton btn = new JButton("pres");
        btn.setMinimumSize(new Dimension(1000, 100));

        Imgproc.resize(img, img, new Size(WIDTH, HEIGHT));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);

            jframe = new JFrame();
            BoxLayout boxLayout = new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS);
            if (title != null) jframe.setTitle(title);
            jframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            Box.createVerticalGlue();

            jframe.getContentPane().add(new JLabel(new ImageIcon(bufImage)));

            jframe.setLayout(boxLayout);
            jframe.getContentPane().add(btn);
            Box.createVerticalGlue();
            jframe.getContentPane().add(label);
            jframe.pack();
            jframe.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
