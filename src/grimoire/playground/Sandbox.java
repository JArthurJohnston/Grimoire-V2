package grimoire.playground;

import grimoire.views.CameraUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Sandbox {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args){

        Mat grayedFisheyedImage = new Mat();
        Mat fisheyedImage = Highgui.imread("/home/arthur/Downloads/fisheye.jpg");
//        Imgproc.cvtColor(fisheyedImage, grayedFisheyedImage, Imgproc.COLOR_BGR2GRAY);

        CameraUI cameraUI = new CameraUI();
        cameraUI.drawFrame(fisheyedImage);
        cameraUI.setVisible(true);
    }
}
