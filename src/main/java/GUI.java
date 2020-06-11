import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Singleton for showing the pictures to the users and GUI for choosing suggestion
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

        Imgproc.resize(img, img, new Size(WIDTH, HEIGHT));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);


        } catch (Exception e) {
            e.printStackTrace();
        }


        jframe = new JFrame();

        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JPanel bottom = new JPanel();


        JLabel image = new JLabel(new ImageIcon(bufImage));
        JButton btn = new JButton("Press to get suggestion");
        JLabel text = new JLabel("suggestion here");


        top.add(image);
        center.add(btn);
        bottom.add(text);

        jframe.add(top);
        jframe.add(center);
        jframe.add(bottom);


        BoxLayout boxLayout = new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS);
        if (title != null) jframe.setTitle(title);
        jframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jframe.setLayout(boxLayout);
        jframe.pack();
        jframe.setVisible(true);

        btn.addActionListener(new CustomActionListener());


    }

    class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("button clicked");

            //TODO: Take photo with webcam on click
        }
    }
}
