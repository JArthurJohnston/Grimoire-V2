package grimoire.threads;

import grimoire.image_analysis.buffer.RingBuffer;
import grimoire.image_analysis.cameras.MotionCaptureDetector;
import org.opencv.core.Mat;

public class DetectionRunner implements Runnable {

    private MotionCaptureDetector detector;
    private RingBuffer<Mat> imageQueue;
    private boolean isRunning;

    public DetectionRunner(MotionCaptureDetector detector, RingBuffer<Mat> imageQueue){
        this.detector = detector;
        this.imageQueue = imageQueue;
    }

    @Override
    public void run() {
        isRunning = true;
        int numberOfFrames = 0;
        long fpsTimestamp = System.currentTimeMillis();
        while (isRunning){
            try {
                detector.detect(imageQueue.take());
                numberOfFrames++;
                long timestamp = System.currentTimeMillis();
                if(timestamp > fpsTimestamp + 1000){
                    fpsTimestamp = timestamp;
                    System.out.println(numberOfFrames);
                    numberOfFrames = 0;
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void stop(){
        isRunning = false;
    }
}
