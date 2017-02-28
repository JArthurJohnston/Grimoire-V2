package grimoire;

import grimoire.models.cameras.*;
import grimoire.models.processors.gestures.Gesture;
import grimoire.runes.Rune;
import grimoire.runes.RuneInterface;
import grimoire.views.CameraUI;
import org.opencv.core.Core;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class Grimoire {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    static CameraUI view;
    static DetectorInterface recorder;
    static CameraInterface camera;

    public static void main(String[] args){
        System.out.println("*****GRIMOIRE*****");
//        Scanner userInputScanner = new Scanner(new InputStreamReader(System.in));
//        System.out.println("Enter Camera Index: ");
//        String camIndex = userInputScanner.nextLine();

        camera = setupCameraWithArgs(args);

        view = new CameraUI();
        Thread videoThread = new Thread(() -> {
            view.addWindowListener(closeListener(recorder));
            view.setVisible(true);
        });

        ArrayList<RuneInterface> runes = new ArrayList<>();
        runes.add(new Rune("rhythmbox-client --play", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT));
        runes.add(new Rune("rhythmbox-client --play", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.DOWNWARDS_LEFT));
        runes.add(new Rune("rhythmbox-client --pause", Gesture.UPWARDS, Gesture.DOWNWARDS_RIGHT, Gesture.UPWARDS));

        RuneKeeper runeKeeper = new RuneKeeper(runes);

        recorder = new MotionCaptureDetector(view, camera, runeKeeper);

        videoThread.start();
        recorder.start();
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

    static WindowListener closeListener(DetectorInterface capture){
        return new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                capture.stop();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }
}
