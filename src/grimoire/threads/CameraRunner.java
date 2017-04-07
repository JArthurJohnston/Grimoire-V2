package grimoire.threads;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import java.util.concurrent.BlockingQueue;

public class CameraRunner implements Runnable {

    private VideoCapture capture;
    private final int cameraIndex;
    private BlockingQueue<Mat> coms;
    private boolean isRunning;

    public CameraRunner(int cameraIndex, BlockingQueue<Mat> coms){
        this.cameraIndex = cameraIndex;
        this.coms = coms;
//        this.detector = detector;
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        capture = new VideoCapture();
        capture.open(this.cameraIndex);
//        detector.init();
        while (isRunning){
            Mat cameraFrame = new Mat();
            if(capture.read(cameraFrame)){
//                detector.detect(cameraFrame);
                try {
                    coms.put(cameraFrame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        capture.release();
    }

    public void stop(){
        isRunning = false;
    }
}
