package grimoire.threads;

import grimoire.image_analysis.cameras.DetectorInterface;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CameraRunner implements Runnable {

    private VideoCapture capture;
    private final int cameraIndex;
    private DetectorInterface detector;
    private boolean isRunning;

    public CameraRunner(int cameraIndex, DetectorInterface detector){
        this.cameraIndex = cameraIndex;
        this.detector = detector;
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        capture = new VideoCapture();
        capture.open(this.cameraIndex);
        detector.init();
        while (isRunning){
            Mat cameraFrame = new Mat();
            if(capture.read(cameraFrame)){
                detector.detect(cameraFrame);
            }
        }
        capture.release();
    }

    public void stop(){
        isRunning = false;
    }
}
