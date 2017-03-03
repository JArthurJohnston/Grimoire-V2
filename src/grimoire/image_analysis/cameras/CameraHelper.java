package grimoire.image_analysis.cameras;

import org.opencv.core.Mat;
import static grimoire.image_analysis.ImageFilterer.applyGaussianBlur;
import static grimoire.image_analysis.ImageFilterer.applyGrayscale;


class CameraHelper {

    static Mat blurredAndGrayscaleFrame(Mat input, Mat output){
        applyGrayscale(input, output);
        applyGaussianBlur(output, output);
        return output;
    }
}
