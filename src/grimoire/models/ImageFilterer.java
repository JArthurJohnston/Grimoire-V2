package grimoire.models;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImageFilterer {

    public static Mat applyGrayscale(Mat inputFrame, Mat outputFrame){
        Imgproc.cvtColor(inputFrame, outputFrame, Imgproc.COLOR_BGR2GRAY);
        return outputFrame;
    }

    public static Mat applyThreshold(Mat inputFrame, Mat outputFrame){
        Imgproc.threshold(inputFrame, outputFrame, UserSettings.MOTION_THRESHOLD, 255, Imgproc.THRESH_BINARY);
        return outputFrame;
    }

    public static Mat applyGaussianBlur(Mat inputFrame, Mat outpurtFrame){
        int kernelSize = UserSettings.GAUSSIAN_KERNEL_SIZE;
        if(kernelSize > 1){
            kernelSize = kernelSize % 2 ==0 ? kernelSize++ : kernelSize;
            Imgproc.GaussianBlur(inputFrame,outpurtFrame, new Size(kernelSize, kernelSize), 1.5);
        }
        //WOW this is REALLY slow
//        Photo.fastNlMeansDenoising(frame, frame, 3, 7, 13);
        return outpurtFrame;
    }

    public static Mat absoluteDiff(Mat background, Mat inputImage, Mat outputImage){
        Core.absdiff(background, inputImage, outputImage);
        return outputImage;
    }
}
