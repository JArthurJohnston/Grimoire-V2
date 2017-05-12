package grimoire.image_analysis.cameras;

import grimoire.Grimoire;
import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.gestures.GestureDetector;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.image_analysis.processors.MotionProcessor;
import grimoire.image_analysis.processors.WandMotion;
import grimoire.threads.ProcessedFrameData;
import grimoire.threads.ThreadCommunicator;
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

    public MotionCaptureDetector(Spellbook spellbook, ThreadCommunicator communicator){
        this.spellbook = spellbook;
        this.communicator = communicator;
        processor = new MotionProcessor();
        hasBeenInitialized = false;
    }

    public void detect(Mat cameraFrame){
        if(!hasBeenInitialized) {
            hasBeenInitialized = true;
            initFrames(cameraFrame);
        } else {
            Mat motionFrame = applyMotionFilters();

            /*
            At rest, this is a constant 4-6 miliseconds
            when motion is detected it jumps to 10-15 miliseconds
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
    }

    void initFrames(Mat frame){
        previousImage = new Mat();
        currentImage = new Mat();
        nextImage = new Mat();
        motion = new Mat();

        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        frame.copyTo(previousImage);
        frame.copyTo(currentImage);
        frame.copyTo(nextImage);
        frame.copyTo(motion);
    }

    private void updateFrames(Mat cameraFrame){
        currentImage.copyTo(previousImage);
        nextImage.copyTo(currentImage);
        Imgproc.cvtColor(cameraFrame, nextImage, Imgproc.COLOR_BGR2GRAY);
        int kernelSize = Grimoire.UserSettings.GAUSSIAN_KERNEL_SIZE;
        Imgproc.GaussianBlur(nextImage, nextImage, new Size(kernelSize, kernelSize), 1.5);
    }

    private Mat applyMotionFilters() {
        Core.absdiff(nextImage, currentImage, motion);
        Imgproc.threshold(motion, motion, Grimoire.UserSettings.MOTION_THRESHOLD, 255, Imgproc.THRESH_TOZERO);
        return motion;
    }
}
