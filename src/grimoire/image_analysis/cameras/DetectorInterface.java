package grimoire.image_analysis.cameras;

import org.opencv.core.Mat;

public interface DetectorInterface {

    void detect(Mat frame);

}
