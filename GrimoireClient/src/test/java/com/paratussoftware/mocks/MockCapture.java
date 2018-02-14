package com.paratussoftware.mocks;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class MockCapture extends VideoCapture {
    public Mat framePassedToRead;
    public int deviceIndexPassedIn;

    @Override
    public boolean read(final Mat frame) {
        this.framePassedToRead = frame;
        return true;
    }

    @Override
    public boolean open(final int deviceIndex) {
        this.deviceIndexPassedIn = deviceIndex;
        return true;
    }
}
