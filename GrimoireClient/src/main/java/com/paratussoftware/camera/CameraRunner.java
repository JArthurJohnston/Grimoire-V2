package com.paratussoftware.camera;

import com.paratussoftware.data.RingBuffer;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CameraRunner implements Runnable {
    private final int cameraIndex;
    private final RingBuffer<Mat> imageBuffer;
    private final Mat defaultFrame;
    private final VideoCapture capture;
    private boolean running;

    public CameraRunner(final int cameraIndex, final RingBuffer<Mat> imageBuffer) {
        this.cameraIndex = cameraIndex;
        this.imageBuffer = imageBuffer;
        this.defaultFrame = new Mat();
        this.capture = new VideoCapture();
    }

    @Override
    public void run() {
        this.running = true;
        this.capture.open(this.cameraIndex);
        while (this.running) {
            this.capture.read(this.defaultFrame);
            try {
                this.imageBuffer.put(this.defaultFrame);
            } catch (final InterruptedException e) {
            }
        }

    }

    int getCameraIndex() {
        return this.cameraIndex;
    }

    RingBuffer<Mat> getImageBuffer() {
        return this.imageBuffer;
    }

    boolean isRunning() {
        return this.running;
    }
}
