package com.paratussoftware.image_analysis.cameras;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CameraWrapper implements CameraInterface{

    private final VideoCapture capture;
    private int cameraIndex;

    public CameraWrapper(int cameraIndex){
        this.cameraIndex = cameraIndex;
        capture = new VideoCapture();
    }

    public boolean open(){
        return capture.open(this.cameraIndex);
    }

    public boolean read(Mat frame){
        return capture.read(frame);
    }

    public void release(){
        capture.release();
    }

}
