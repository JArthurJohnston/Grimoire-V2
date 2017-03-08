package grimoire.ui.views;

import grimoire.image_analysis.processors.WandMotion;
import org.opencv.core.Mat;

import java.util.List;

public interface GrimoireViewInterface {

    void drawFrame(Mat frame, List<WandMotion> motion);
}
