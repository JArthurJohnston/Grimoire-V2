package grimoire.models.cameras;

import org.opencv.core.Mat;

public interface CameraInterface {

    boolean open();

    boolean read(Mat frame);

    void release();
}
