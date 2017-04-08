package grimoire.image_analysis.cameras;

import grimoire.ui.views.GrimoireViewInterface;
import org.opencv.core.Mat;

public interface DetectorInterface {

    void stop();

    void detect(Mat frame);

    void viewOpened(GrimoireViewInterface view);

}
