package view;

import gamelogic.MoveCalculator;
import gamelogic.strategies.DefaultStrategy;
import gamelogic.strategies.DoEverythingStrategy;
import image_recognition.CardProcessor;
import image_recognition.SnapshotCapturer;
import model.Card;
import model.GameSnapshot;
import model.GameSnapshotFactory;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Singleton for showing the pictures to the users and GUI for choosing suggestion
 *
 * @author Jeppe Kaare Larsen & Mads Martin Dickmeiss Hemer
 */
public class GUI {
    private static GUI single_instance = null;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static JFrame jframe;
    private static ImageIcon imageIcon;
    private static String suggestionText;
    private static MoveCalculator moveCalculator;
    private static Mat snapshot;

    private GUI() {
    }

    public static GUI getInstance() {
        if (single_instance == null)
            single_instance = new GUI();

        return single_instance;
    }

    public static void startGUI() {
        jframe = new JFrame();

        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JPanel bottom = new JPanel();

        JButton fileChooserButton = new JButton("Choose file to get suggestion");
        JButton strategyChooserButton = new JButton("Switch strategy");
        JLabel suggestionLabel = new JLabel("suggestion output here");

        if (imageIcon != null) {
            JLabel image = new JLabel(imageIcon);
            top.add(image);
        }

        if (suggestionText != null) {
            suggestionLabel.setText(suggestionText);
        }

        center.add(fileChooserButton);
        center.add(strategyChooserButton);
        bottom.add(suggestionLabel);

        jframe.add(top);
        jframe.add(center);
        jframe.add(bottom);


        BoxLayout boxLayout = new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS);
        jframe.setTitle("7kabaleCDIO");
        jframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jframe.setLayout(boxLayout);
        jframe.pack();
        jframe.setVisible(true);

        fileChooserButton.addActionListener(new fileChooserActionListener());
        strategyChooserButton.addActionListener(new strategyChooserActionListener());
    }

    public static void updateGUI() {
        jframe.dispose();
        startGUI();
    }

    public static void updateGUI(String title) {
        jframe.dispose();
        startGUI();
        jframe.setTitle(title);
    }

    public static void setImage(Mat img) {
        Imgproc.resize(img, img, new Size(WIDTH, HEIGHT));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;

        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            imageIcon = new ImageIcon(bufImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Nyt navn!
    public static void spilSpil() {
        CardProcessor cp = new CardProcessor();
        List<Card> listOfCards = cp.detectCards(snapshot.clone(), 190);

        System.out.println(listOfCards.size() + " cards found!");

        GameSnapshot gameSnapshot = GameSnapshotFactory.fromPositionCards(listOfCards);

        if (moveCalculator == null) {
            moveCalculator = new MoveCalculator();
        }

        String suggestion = moveCalculator.calculateBestPossibleMove(gameSnapshot).toString();
        suggestionText = suggestion;
        System.out.println(suggestion);

//                setImage(snapshot);
        updateGUI(moveCalculator.getStrategy().toString());
    }

    //ToDo: Skal hele programmets flow virkeligt køre i det følgende?
    static class fileChooserActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("resources/test_images/1606Test1/"));
            int result = fileChooser.showOpenDialog(jframe);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
                snapshot = snapshotCapturer.readFromFile(selectedFile.getPath());

                spilSpil();

            }
        }
    }

    static class strategyChooserActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (moveCalculator.getStrategy().toString().equals("DefaultStrategy")) {
                moveCalculator.setStrategy(new DoEverythingStrategy());
                updateGUI("DoEveryThingStrategy");
            } else {
                moveCalculator.setStrategy(new DefaultStrategy());
                updateGUI("DefaultStrategy");
            }
            spilSpil();

        }
    }

}
