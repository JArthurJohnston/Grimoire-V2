package com.paratussoftware.threads;

import com.paratussoftware.image_analysis.buffer.RingBuffer;
import com.paratussoftware.image_analysis.cameras.MotionCaptureDetector;
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
        while (isRunning){
            try {
                detector.detect(imageQueue.take());
            } catch (InterruptedException e) {
            }
        }
    }

    public void initialize(Mat image){
        detector.initialize(image);
    }

    public void stop(){
        isRunning = false;
    }
}
