package grimoire.image_analysis.cameras;

import org.opencv.core.Mat;

public interface DetectorInterface {

    void stop();

    void detect(Mat frame);

}
