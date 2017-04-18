package grimoire.threads;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import java.util.concurrent.BlockingQueue;

public class CameraRunner implements Runnable {

    private VideoCapture capture;
    private final int cameraIndex;
    private BlockingQueue<Mat> frameQueue;
    private boolean isRunning;

    public CameraRunner(int cameraIndex, BlockingQueue<Mat> frameQueue){
        this.cameraIndex = cameraIndex;
        this.frameQueue = frameQueue;
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        capture = new VideoCapture();
        capture.open(this.cameraIndex);
        while (isRunning){
            Mat cameraFrame = new Mat();
            if(capture.read(cameraFrame)){
                try {
                    frameQueue.put(cameraFrame);
                } catch (InterruptedException e) {
                }
            }
        }
        capture.release();
    }

    public void stop(){
        isRunning = false;
    }
}
