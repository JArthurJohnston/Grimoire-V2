package grimoire.image_analysis.cameras;

import grimoire.image_analysis.processors.MotionProcessor;
import grimoire.image_analysis.processors.WandMotion;
import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.gestures.GestureDetector;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.ui.views.GrimoireViewInterface;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.*;
import java.util.List;

import static grimoire.image_analysis.ImageFilterer.*;
import static grimoire.image_analysis.cameras.CameraHelper.blurredAndGrayscaleFrame;

public class MotionCaptureDetector implements DetectorInterface{

    private final CameraInterface camera;
    private final Spellbook spellbook;
    private boolean isRunning = false;
    private Mat previousImage, currentImage, nextImage, temp1, temp2, motion;
    private final MotionProcessor processor;
    private GrimoireViewInterface view;

    public MotionCaptureDetector(CameraInterface camera, Spellbook spellbook){
        this.camera = camera;
        this.spellbook = spellbook;
        processor = new MotionProcessor();
        this.view = new NullView();
        initializeTempFrames();
    }

    public void viewOpened(GrimoireViewInterface view){
        this.view = view;
    }

    public void detect(Mat cameraFrame){
        if(readyToProcessImages()){
            scanForMotion();
            List<WandMotion> wandMotions = processor.scanFrame(motion, cameraFrame);
            wandMotions.sort(Comparator.naturalOrder());
            if(!wandMotions.isEmpty()){
                WandMotion largestWandMotion = wandMotions.get(0);
                Gesture gesture = GestureDetector.getMostRecentGesture(largestWandMotion);
                spellbook.handle(gesture);
            }
            view.drawFrame(cameraFrame,wandMotions);
            updateFrames(cameraFrame);
        } else {
            initFrames(cameraFrame);
        }
    }

    void initFrames(Mat frame){
        if(previousImage == null){
            previousImage = frame;
        } else if(currentImage == null){
            currentImage = frame;
        } else if(nextImage == null){
            nextImage = frame;
        }
    }

    public void stop(){
        isRunning = false;
        camera.release();
    }

    private void updateFrames(Mat cameraFrame){

        currentImage.copyTo(previousImage);
        nextImage.copyTo(currentImage);
        blurredAndGrayscaleFrame(cameraFrame, nextImage);
    }

    private void scanForMotion() {
        try {
            Core.absdiff(nextImage, currentImage, temp1);
            Core.absdiff(currentImage, previousImage, temp2);
            Core.bitwise_and(temp1, temp2, motion);
            applyThreshold(motion, motion);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void initializeTempFrames() {
        temp1 = new Mat();
        temp2 = new Mat();
        motion = new Mat();
    }

    private boolean readyToProcessImages(){
        return previousImage != null &&
                currentImage != null &&
                nextImage != null;
    }

    private class NullView implements GrimoireViewInterface {

        @Override
        public void drawFrame(Mat frame, List<WandMotion> motion) {}
    }
}
