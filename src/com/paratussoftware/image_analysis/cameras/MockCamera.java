package com.paratussoftware.image_analysis.cameras;

import org.opencv.core.Mat;
import java.io.File;
import java.util.Arrays;
import static org.opencv.highgui.Highgui.imread;

public class MockCamera implements CameraInterface{

    private final String frameDirectory;
    private Mat[] frames;
    private int imageIndex;
    private long lastTimeCameraRead = 0;
    private static final int CAMERA_SPEED = 33;
    private boolean shouldDelayCamera;

    public MockCamera(String frameDirectory, boolean shouldDelayCamera){
        this.shouldDelayCamera = shouldDelayCamera;
        imageIndex = 0;
        this.frameDirectory = frameDirectory;
    }

    private Mat[] loadImageFiles() {
        File imageDirectory = new File(this.frameDirectory);
        File[] files = imageDirectory.listFiles();
        Mat[] images = new Mat[files.length];
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++) {
            images[i] = imread(files[i].getAbsolutePath());
        }
        return images;
    }

    @Override
    public boolean open() {
        this.frames = loadImageFiles();
        return true;
    }

    @Override
    public boolean read(Mat frame) {
        long now = System.currentTimeMillis();
        if(shouldDelayCamera && now < lastTimeCameraRead + CAMERA_SPEED){
            return false;
        }
        this.frames[imageIndex++].copyTo(frame);
        if(imageIndex >= this.frames.length)
            imageIndex = 0;
        lastTimeCameraRead = now;
        return true;
    }

    @Override
    public void release() {
        this.frames = null;
    }
}
