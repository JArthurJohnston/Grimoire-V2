package grimoire;

import grimoire.gesture_analysis.RuneKeeper;
import grimoire.image_analysis.cameras.*;
import grimoire.gesture_analysis.spells.Spellbook;
import grimoire.threads.CameraRunner;
import grimoire.threads.DetectionRunner;
import grimoire.threads.ProcessedFrameData;
import grimoire.threads.ViewRunner;
import grimoire.ui.cli.GrimoireCLI;
import grimoire.ui.views.CameraUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Grimoire {

    static {
//        String property = System.getProperty("java.library.path");
//        System.out.println("Library Path: " + property);
        System.out.println("Working Direcotry: " + Paths.get("").toAbsolutePath());
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


    public static void main(String[] args){
        GrimoireCLI.startGrimoireCLI(args);
    }

    public static void startDefaultApp(String[] args){
        Spellbook spellbook = new Spellbook(RuneKeeper.readSpellsFromTome());

        BlockingQueue<Mat> matBlockingQueue = new LinkedBlockingQueue<Mat>();
        LinkedBlockingQueue<ProcessedFrameData> frameDataQueue = new LinkedBlockingQueue<>();

        cameraRunner= new CameraRunner(0, matBlockingQueue);
        detectionRunner = new DetectionRunner(new MotionCaptureDetector(setupCameraWithArgs(args),
                spellbook, frameDataQueue), matBlockingQueue);
        viewRunner = new ViewRunner(frameDataQueue);

        Thread cameraThread = new Thread(cameraRunner, "Camera-Thread");
        Thread detectionThread = new Thread(detectionRunner, "Detection-Thread");

        cameraThread.start();
        detectionThread.start();
    }

    public static void stop(){
        detectionRunner.stop();
        cameraRunner.stop();
    }

    public static void startUI(){
        if(!viewRunner.isRunning){
            Thread viewThread = new Thread(viewRunner, "View-Thread");
            viewThread.start();
        }
    }

    public static void stopUI(){
        viewRunner.stop();
    }

    private static CameraInterface setupCameraWithArgs(String[] args) {
        if(args.length == 0){
            return new CameraWrapper(0);
        } else if(args.length == 1) {
            return new CameraWrapper(Integer.parseInt(args[0]));
        }else {
            switch (args[0]){
                case "-demo":
                    String imageDirectory = args[1];
                    return new MockCamera(imageDirectory, false);
                default:
                    System.out.println("Invalid Input");
                    System.exit(1);
            }
        }
        return null;
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
    }
}
