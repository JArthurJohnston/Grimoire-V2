package grimoire;

import grimoire.gesture_analysis.RuneKeeper;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.image_analysis.cameras.MotionCaptureDetector;
import grimoire.threads.CameraRunner;
import grimoire.threads.DetectionRunner;
import grimoire.threads.ThreadCommunicator;
import grimoire.threads.ViewRunner;
import grimoire.ui.cli.GrimoireCLI;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Grimoire {

    static {
//        String property = System.getProperty("java.library.path");
//        System.out.println("Library Path: " + property);
//        System.out.println("Working Direcotry: " + Paths.get("").toAbsolutePath());
        String libraryName = "libopencv_java2413.so";
        File file = new File("/lib/" + libraryName);
        if(file.getAbsoluteFile().exists()){

            System.load(file.getAbsolutePath());
            System.loadLibrary(libraryName);
        } else {
            System.out.println("Failed to load file");
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
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
        detectionHasStarted = false;
        stopUI();
        stopDetection();
        System.exit(0);
    }

    public static void stopUI(){
        viewRunner.stop();
    }

    public static void stopDetection(){
        cameraRunner.stop();
        detectionRunner.stop();
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

    public static void startDetection(int cameraId){
        detectionHasStarted = true;
        Spellbook spellbook = new Spellbook(RuneKeeper.readSpellsFromTome());
        BlockingQueue<Mat> matBlockingQueue = new ArrayBlockingQueue<>(UserSettings.BUFFER_SIZE);
        communicator = new ThreadCommunicator();

        cameraRunner= new CameraRunner(cameraId, matBlockingQueue);
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
        public static int BUFFER_SIZE = 30;
    }
}
