package grimoire.image_analysis.processors;

import grimoire.Grimoire;
import grimoire.image_analysis.clusters.ClusterCreator;
import grimoire.image_analysis.clusters.PointCluster;
import grimoire.image_analysis.buffer.Buffer;
import org.opencv.core.Mat;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static grimoire.image_analysis.ImageAnalysisHelper.pixelIntensity;
import static grimoire.image_analysis.processors.Identification.WandFinder.findPastWandPointsFor;
import static grimoire.image_analysis.processors.Identification.WandFinder.isPossibleWandPoint;

public class MotionProcessor {
    private final Buffer<LinkedList<PointCluster>> frameMotionBuffer;
    private static final int FPS = 30;
    private static final double TIME = 1.5;
    private static final int BUFFER_SIZE = (int)(FPS * TIME);

    public MotionProcessor(){
        frameMotionBuffer = new Buffer<>(BUFFER_SIZE);
    }

    public List<WandMotion> scanFrame(Mat motionFrame, Mat originalFrame){
        ClusterCreator clusterCreator = new ClusterCreator();

        for (int columnIndex = 0; columnIndex < motionFrame.cols(); columnIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
            for (int rowIndex = 0; rowIndex < motionFrame.rows(); rowIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
                if (motionFrame.get(rowIndex, columnIndex)[0] > 0) {
                    double pixelIntensity = pixelIntensity(originalFrame.get(rowIndex, columnIndex));
                    if(pixelIntensity > Grimoire.UserSettings.INTENSITY_THRESHOLD){
                        clusterCreator.handle(columnIndex, rowIndex);
                    }
                }
            }
        }
        LinkedList<PointCluster> clusters = clusterCreator.getClusters();
        List<WandMotion> wandMotions = processFrameData(clusters);
        frameMotionBuffer.add(clusters);
        return wandMotions;
    }

    private List<WandMotion> processFrameData(List<PointCluster> clusters){
        List<WandMotion> wandMotions = new LinkedList<>();
        for (PointCluster pointCluster : clusters) {
            if(isPossibleWandPoint(pointCluster)){
                Iterator<LinkedList<PointCluster>> iterator = frameMotionBuffer.fifoIterator();
                LinkedList<PointCluster> pastWandClusters = findPastWandPointsFor(pointCluster, iterator);
                wandMotions.add(new WandMotion(pointCluster, pastWandClusters));
            }
        }
        return wandMotions;
    }

}
