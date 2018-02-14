package com.paratussoftware;

import com.paratussoftware.gesture_analysis.spells.Spellbook;
import com.paratussoftware.image_analysis.buffer.RingBuffer;
import com.paratussoftware.image_analysis.cameras.MotionCaptureDetector;
import com.paratussoftware.ui.cli.GrimoireCLI;
import com.paratussoftware.gesture_analysis.RuneKeeper;
import com.paratussoftware.threads.CameraRunner;
import com.paratussoftware.threads.DetectionRunner;
import com.paratussoftware.threads.ThreadCommunicator;
import com.paratussoftware.threads.ViewRunner;
import org.opencv.core.Core;
import org.opencv.core.Mat;

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
        public static String ERROR_LOG_LOCATION = "./res/exceptions.log";
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
        public static String SPELLFILE_LOCATION = "./res/spells.grim";
        public static int SCAN_RESOLUTION = 2;
        public static int BUFFER_SIZE = 32;
    }
}
