package com.paratussoftware.image_analysis.cameras;

import com.paratussoftware.gesture_analysis.spells.Spellbook;
import com.paratussoftware.Grimoire;
import com.paratussoftware.gesture_analysis.gestures.Gesture;
import com.paratussoftware.gesture_analysis.gestures.GestureDetector;
import com.paratussoftware.image_analysis.processors.MotionProcessor;
import com.paratussoftware.image_analysis.processors.WandMotion;
import com.paratussoftware.threads.ProcessedFrameData;
import com.paratussoftware.threads.ThreadCommunicator;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Comparator;
import java.util.List;

public class MotionCaptureDetector {

    private final Spellbook spellbook;
    private ThreadCommunicator communicator;
    private Mat previousImage, currentImage, nextImage, motion;
    private final MotionProcessor processor;
    private boolean hasBeenInitialized;
    private Size kernelSize = new Size(Grimoire.UserSettings.GAUSSIAN_KERNEL_SIZE, Grimoire.UserSettings.GAUSSIAN_KERNEL_SIZE);

    public MotionCaptureDetector(Spellbook spellbook, ThreadCommunicator communicator){
        this.spellbook = spellbook;
        this.communicator = communicator;
        processor = new MotionProcessor();
        hasBeenInitialized = false;
    }

    public void detect(Mat cameraFrame){
        Mat motionFrame = applyMotionFilters();

            /*
            At rest, this is a constant 4-6 miliseconds
            when motion is detected it can jump to 10-15 miliseconds
             */
        ProcessedFrameData frameData = processor.scanFrame(motionFrame);

        //This takes almost no time at all
        List<WandMotion> motions = frameData.getMotions();
        if (!motions.isEmpty()) {
            motions.sort(Comparator.naturalOrder());
            WandMotion largestWandMotion = motions.get(0);
            Gesture gesture = GestureDetector.getMostRecentGesture(largestWandMotion);
            spellbook.handle(gesture);
        }
        try {
            communicator.addData(frameData);
        } catch (InterruptedException e) {}
        updateFrames(cameraFrame); //seems to be a constant 2-4 miliseconds
    }

    public void initialize(Mat frame){
        previousImage = new Mat();
        currentImage = new Mat();
        nextImage = new Mat();
        motion = new Mat();

        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        frame.copyTo(previousImage);
        frame.copyTo(currentImage);
        frame.copyTo(nextImage);
        frame.copyTo(motion);

        processor.initialize(frame);
    }

    private void updateFrames(Mat cameraFrame){
        currentImage.copyTo(previousImage);
        nextImage.copyTo(currentImage);
        Imgproc.cvtColor(cameraFrame, nextImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(nextImage, nextImage, kernelSize, 1.5);
    }

    private Mat applyMotionFilters() {
        Core.absdiff(nextImage, currentImage, motion);
        Imgproc.threshold(motion, motion, Grimoire.UserSettings.MOTION_THRESHOLD, 255, Imgproc.THRESH_TOZERO);
        return motion;
    }
}
