package grimoire.models.cameras;

import org.opencv.core.Mat;
import static grimoire.models.ImageFilterer.applyGaussianBlur;
import static grimoire.models.ImageFilterer.applyGrayscale;


public class CameraHelper {

    public static Mat blurredAndGrayscaleFrame(Mat input, Mat output){
        applyGrayscale(input, output);
        applyGaussianBlur(output, output);
        return output;
    }
}
