package grimoire.models.processors;

import grimoire.models.UserSettings;
import grimoire.models.clusters.ClusterCreator;
import grimoire.models.clusters.PointCluster;
import org.opencv.core.Mat;
import java.util.LinkedList;
import java.util.List;

import static grimoire.models.ImageHelper.pixelIntensity;
import static grimoire.models.processors.Identification.WandFinder.isPossibleWandPoint;
import static grimoire.models.processors.Identification.WandFinder.nearestNearbyWandPointTo;

public class MotionProcessor {
    Buffer<LinkedList<PointCluster>> frameMotionBuffer;
    private static final int FPS = 30;
    private static final double TIME = 1.5;
    private static final int BUFFER_SIZE = (int)(FPS * TIME);

    public MotionProcessor(){
        frameMotionBuffer = new Buffer<>(BUFFER_SIZE);
    }

    /*
    rgb[0] is blue
    rgb[1] is green
    rgb[2] is red
     */
    public List<WandMotion> scanFrame(Mat motionFrame, Mat originalFrame){
        ClusterCreator clusterCreator = new ClusterCreator();

        for (int columnIndex = 0; columnIndex < motionFrame.cols(); columnIndex++) {
            for (int rowIndex = 0; rowIndex < motionFrame.rows(); rowIndex++) {
                if (motionFrame.get(rowIndex, columnIndex)[0] > 0) {
                    double pixelIntensity = pixelIntensity(originalFrame.get(rowIndex, columnIndex));
                    if(pixelIntensity > UserSettings.INTENSITY_THRESHOLD){
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
                List<PointCluster> pastWandClusters = findPastWandClusters(pointCluster);
                wandMotions.add(new WandMotion(pointCluster, pastWandClusters));
            }
        }
        return wandMotions;
    }

    private List<PointCluster> findPastWandClusters(PointCluster cluster) {
        LinkedList<PointCluster> pointClusters = new LinkedList<>();
        PointCluster currentCluster = cluster;
        BufferIterator<LinkedList<PointCluster>> iterator = frameMotionBuffer.iterator();
        while (iterator.hasNext()){
            LinkedList<PointCluster> previousClusters = iterator.next();
            PointCluster lastNearbyCluster = nearestNearbyWandPointTo(currentCluster, previousClusters);
            if(cluster.distanceTo(lastNearbyCluster) < UserSettings.MOTION_DETECTION_DISTANCE){
                pointClusters.add(lastNearbyCluster);
                currentCluster = lastNearbyCluster;
            }
        }
        return pointClusters;
    }

}
