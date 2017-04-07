package grimoire.threads;

import grimoire.image_analysis.cameras.DetectorInterface;
import org.opencv.core.Mat;

import java.util.concurrent.BlockingQueue;

public class DetectionRunner implements Runnable {

    private DetectorInterface detector;
    private BlockingQueue<Mat> coms;
    private boolean isRunning;
    private Mat lastFrame;

    public DetectionRunner(DetectorInterface detector, BlockingQueue<Mat> coms){
        this.detector = detector;
        this.coms = coms;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            try {
                detector.detect(coms.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        isRunning = false;
    }
}
