package grimoire.image_analysis.cameras;

import grimoire.image_analysis.processors.MotionProcessor;
import grimoire.image_analysis.processors.WandMotion;
import grimoire.gesture_analysis.gestures.Gesture;
import grimoire.gesture_analysis.gestures.GestureDetector;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.image_viewing.views.CameraUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.*;
import java.util.List;

import static grimoire.image_analysis.ImageFilterer.*;
import static grimoire.image_analysis.cameras.CameraHelper.blurredAndGrayscaleFrame;

public class MotionCaptureDetector implements DetectorInterface{

    private final CameraInterface camera;
    private Spellbook spellbook;
    private boolean isRunning = false;
    private Mat frameFromCamera, previousImage, currentImage, nextImage, temp1, temp2, motion;
    private final MotionProcessor processor;
    private final CameraUI view;

    public MotionCaptureDetector(CameraUI view, CameraInterface camera, Spellbook spellbook){
        this.camera = camera;
        this.spellbook = spellbook;
        processor = new MotionProcessor();
        this.view = view;
    }

    public void start(){
        isRunning = true;
        camera.open();
        initializeFrames();
        while (isRunning){
            if(camera.read(frameFromCamera)){
                scanForMotion();
                List<WandMotion> wandMotions = processor.scanFrame(motion, frameFromCamera);
                wandMotions.sort(Comparator.naturalOrder());
                if(!wandMotions.isEmpty()){
                    WandMotion largestWandMotion = wandMotions.get(0);
                    Gesture gesture = GestureDetector.getMostRecentGesture(largestWandMotion);
                    spellbook.handle(gesture);
                }
                view.drawFrame(frameFromCamera,wandMotions);

                updateFrames();
            }
        }
    }
    public void stop(){
        isRunning = false;
        camera.release();
    }

    private void updateFrames() {
        currentImage.copyTo(previousImage);
        nextImage.copyTo(currentImage);
        blurredAndGrayscaleFrame(frameFromCamera, nextImage);
    }

    private void scanForMotion() {
        Core.absdiff(nextImage, currentImage, temp1);
        Core.absdiff(currentImage, previousImage, temp2);
        Core.bitwise_and(temp1, temp2, motion);
        applyThreshold(motion, motion);
    }

    private void initializeFrames(){
        frameFromCamera = new Mat();

        temp1 = new Mat();
        temp2 = new Mat();
        motion = new Mat();

        previousImage = new Mat();
        currentImage = new Mat();
        nextImage = new Mat();
        camera.read(previousImage);
        applyGrayscale(previousImage, previousImage);
        camera.read(currentImage);
        applyGrayscale(currentImage, currentImage);
        camera.read(nextImage);
        applyGrayscale(nextImage, nextImage);
    }
}
