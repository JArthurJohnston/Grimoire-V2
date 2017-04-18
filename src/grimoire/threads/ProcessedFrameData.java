package grimoire.threads;

import grimoire.image_analysis.processors.WandMotion;
import org.opencv.core.Mat;

import java.util.List;

public class ProcessedFrameData {
    Mat frame;
    List<WandMotion> motions;

    public ProcessedFrameData(Mat frame, List<WandMotion> motions){
        this.frame = frame;
        this.motions = motions;
    }
}
