import nu.pattern.OpenCV;

public class Main {

    public static void main(String[] args) throws Exception {
        OpenCV.loadShared();

        SnapshotCapturer snapshotCapturer = new SnapshotCapturer();
        snapshotCapturer.captureSnapshot();
    }


}
