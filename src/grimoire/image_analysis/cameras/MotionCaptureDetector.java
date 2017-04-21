package grimoire.image_analysis.cameras;

import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.gestures.GestureDetector;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.image_analysis.processors.MotionProcessor;
import grimoire.image_analysis.processors.WandMotion;
import grimoire.threads.ProcessedFrameData;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static grimoire.image_analysis.ImageFilterer.applyThreshold;

public class MotionCaptureDetector implements DetectorInterface{

    private final Spellbook spellbook;
    private BlockingQueue<ProcessedFrameData> processedFrameData;
    private Mat previousImage, currentImage, nextImage, temp1, temp2, motion;
    private final MotionProcessor processor;
    private boolean hasBeenInitialized;

    public MotionCaptureDetector(Spellbook spellbook, BlockingQueue<ProcessedFrameData> processedFrameData){
        this.spellbook = spellbook;
        this.processedFrameData = processedFrameData;
        processor = new MotionProcessor();
        hasBeenInitialized = false;
    }

    public void detect(Mat cameraFrame){
        if(!hasBeenInitialized) {
            hasBeenInitialized = true;
            initFrames(cameraFrame);
        } else {
            Mat motionFrame = applyMotionFilters(cameraFrame);

//            List<WandMotion> wandMotions = new LinkedList<>();
            List<WandMotion> wandMotions = processor.scanFrame(motionFrame, cameraFrame);
            wandMotions.sort(Comparator.naturalOrder());
            if (!wandMotions.isEmpty()) {
                WandMotion largestWandMotion = wandMotions.get(0);
                Gesture gesture = GestureDetector.getMostRecentGesture(largestWandMotion);
                spellbook.handle(gesture);
            }
            try {
                this.processedFrameData.put(new ProcessedFrameData(cameraFrame, wandMotions));
            } catch (InterruptedException e) {}
            updateFrames(cameraFrame);
        }
    }

    void initFrames(Mat frame){
        //all of these need to be greyscaled if i want to use the grayscale feature, or even the blur feature

        previousImage = new Mat();
        currentImage = new Mat();
        nextImage = new Mat();
        temp2 = new Mat();
        temp1 = new Mat();
        motion = new Mat();

        frame.copyTo(previousImage);
        frame.copyTo(currentImage);
        frame.copyTo(nextImage);
        frame.copyTo(temp1);
        frame.copyTo(temp2);
        frame.copyTo(motion);
    }


    private void updateFrames(Mat cameraFrame){
        currentImage.copyTo(previousImage);
        nextImage.copyTo(currentImage);
        cameraFrame.copyTo(nextImage);
//        Imgproc.cvtColor(cameraFrame, nextImage, Imgproc.COLOR_BGR2GRAY);

//        int kernelSize = Grimoire.UserSettings.GAUSSIAN_KERNEL_SIZE;
//        Imgproc.GaussianBlur(nextImage, nextImage, new Size(kernelSize, kernelSize), 1.5);
//        blurredAndGrayscaleFrame(cameraFrame, nextImage);
    }

    private Mat applyMotionFilters(Mat frame) {
            Core.absdiff(nextImage, currentImage, temp1);
            Core.absdiff(currentImage, previousImage, temp2);
            Core.bitwise_and(temp1, temp2, motion);
            applyThreshold(motion, motion);
        return motion;
    }
}
