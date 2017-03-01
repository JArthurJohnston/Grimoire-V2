package grimoire.models.cameras;

import grimoire.models.clusters.PointCluster;
import grimoire.models.processors.MotionProcessor;
import grimoire.models.processors.WandMotion;
import grimoire.models.processors.drawing.MotionDrawings;
import grimoire.models.processors.gestures.Gesture;
import grimoire.models.processors.gestures.GestureDetector;
import grimoire.spells.Spellbook;
import grimoire.views.CameraUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static grimoire.models.ImageFilterer.*;
import static grimoire.models.ImageHelper.matToBufferedImage;
import static grimoire.models.cameras.CameraHelper.blurredAndGrayscaleFrame;

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
                view.drawFrame(drawMotionFrame(wandMotions));

                updateFrames();
            }
        }
    }

    private BufferedImage drawMotionFrame(List<WandMotion> wandMotions){
        BufferedImage bufferedImage = matToBufferedImage(frameFromCamera);
        drawMotionsTo(bufferedImage, wandMotions);
        return bufferedImage;
    }

    private void drawMotionsTo(BufferedImage image, List<WandMotion> wandMotions){
        Graphics graphics = image.getGraphics();
        for (WandMotion wandMotion : wandMotions) {
            PointCluster cluster = wandMotion.getCurrentWandPoint();
            MotionDrawings.drawCluster(graphics, cluster, Color.CYAN);
            MotionDrawings.drawMotionTail(wandMotion.getPastWandPoints(), graphics, cluster);
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
