package grimoire.threads;

import grimoire.image_analysis.buffer.RingBuffer;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CameraRunner implements Runnable {

    private VideoCapture capture;
    private final int cameraIndex;
    private RingBuffer<Mat> frameQueue;
    private boolean isRunning;
    private Mat defaultFrame = new Mat();

    public CameraRunner(int cameraIndex, RingBuffer<Mat> frameQueue){
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
            Mat cameraFrame = defaultFrame;
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
