package grimoire;

import grimoire.gesture_analysis.RuneKeeper;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.image_analysis.buffer.RingBuffer;
import grimoire.image_analysis.cameras.MotionCaptureDetector;
import grimoire.threads.CameraRunner;
import grimoire.threads.DetectionRunner;
import grimoire.threads.ThreadCommunicator;
import grimoire.threads.ViewRunner;
import grimoire.ui.cli.GrimoireCLI;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Grimoire {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static CameraRunner cameraRunner;
    private static DetectionRunner detectionRunner;
    private static ViewRunner viewRunner;

    private static ThreadCommunicator communicator;
    private static boolean detectionHasStarted = false;


    public static void main(String[] args){
        GrimoireCLI.startGrimoireCLI(args);
    }

    public static void stop(){
        stopUI();
        stopDetection();
        System.exit(0);
    }

    public static void stopUI(){
        if(viewRunner != null){
            viewRunner.stop();
        }
    }

    public static void stopDetection(){
        detectionHasStarted = false;
        if(cameraRunner != null){
            cameraRunner.stop();
        }
        if (detectionRunner != null){
            detectionRunner.stop();
        }
    }

    public static void startUI(){
        if(detectionHasStarted){
            viewRunner = new ViewRunner(communicator);
            if(!viewRunner.isRunning){
                Thread viewThread = new Thread(viewRunner, "View-Thread");
                viewThread.start();
            }
        }
    }

    public static void initialize(Mat image){
        detectionRunner.initialize(image);
    }

    public static void startDetection(int cameraId){
        detectionHasStarted = true;
        Spellbook spellbook = new Spellbook(RuneKeeper.readSpellsFromTome());
        RingBuffer<Mat> matBlockingQueue = new RingBuffer<>(UserSettings.BUFFER_SIZE);
        communicator = new ThreadCommunicator();

        cameraRunner = new CameraRunner(cameraId, matBlockingQueue);
        detectionRunner = new DetectionRunner(new MotionCaptureDetector(
                spellbook, communicator), matBlockingQueue);

        Thread cameraThread = new Thread(cameraRunner, "Camera-Thread");
        Thread detectionThread = new Thread(detectionRunner, "Detection-Thread");

        cameraThread.start();
        detectionThread.start();
    }

    public static class UserSettings {
        public static double MOTION_THRESHOLD = 25;
        public static int GAUSSIAN_KERNEL_SIZE = 9;
        public static double INTENSITY_THRESHOLD = 0.2;
        public static int CLUSTER_CONTAINS_DISTANCE = 10;
        public static int MOTION_DETECTION_DISTANCE = 40;
        public static int MAX_MOTION_SIZE_LIMIT = 50;
        public static int MIN_MOTION_SIZE_LIMIT = 1;
        public static int GESTURE_DETECTION_DISTANCE = 40;
        public static int SPELLCAST_COOLDOWN_TIME = 3 * 1000;
        public static int SPELLCASTING_THRESHOLD = 5;
        public static String SPELLFILE_LOCATION = "./lib/spells.grim";
        public static int SCAN_RESOLUTION = 2;
        public static int BUFFER_SIZE = 32;
    }
}
