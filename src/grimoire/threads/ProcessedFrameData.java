package grimoire.threads;

import grimoire.image_analysis.processors.WandMotion;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.util.List;

public class ProcessedFrameData {
    BufferedImage frame;
    List<WandMotion> motions;

    public ProcessedFrameData(BufferedImage frame, List<WandMotion> motions){
        this.frame = frame;
        this.motions = motions;
    }

    public List<WandMotion> getMotions() {
        return motions;
    }
}
