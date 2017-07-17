package com.paratussoftware.mocks;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class MockCapture extends VideoCapture {
    public boolean readSuccessfulToReturn = true;
    public boolean openSuccessfulToReturn = true;
    public Mat framePassedToRead;
    public int deviceIndexPassedIn;

    @Override
    public boolean read(final Mat frame) {
        this.framePassedToRead = frame;
        return this.readSuccessfulToReturn;
    }

    @Override
    public boolean open(final int deviceIndex) {
        this.deviceIndexPassedIn = deviceIndex;
        return this.openSuccessfulToReturn;
    }
}
