package grimoire.threads;

import grimoire.image_analysis.cameras.DetectorInterface;
import org.opencv.core.Mat;

import java.util.concurrent.BlockingQueue;

public class DetectionRunner implements Runnable {

    private DetectorInterface detector;
    private BlockingQueue<Mat> imageQueue;
    private boolean isRunning;

    public DetectionRunner(DetectorInterface detector, BlockingQueue<Mat> imageQueue){
        this.detector = detector;
        this.imageQueue = imageQueue;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            try {
                detector.detect(imageQueue.take());
            } catch (InterruptedException e) {

            }
        }
    }

    public void stop(){
        isRunning = false;
    }
}
