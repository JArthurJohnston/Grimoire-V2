package grimoire.image_analysis.processors;

import grimoire.Grimoire;
import grimoire.image_analysis.clusters.ClusterCreator;
import grimoire.image_analysis.clusters.PointCluster;
import grimoire.image_analysis.buffer.Buffer;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
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
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final int IN_MEMORY_ARRAY_SIZE = FRAME_HEIGHT * FRAME_HEIGHT;
    private static final double[] IN_MEMORY_IMAGE = new double[IN_MEMORY_ARRAY_SIZE];
    private static final double[] IN_MEMORY_MOTION = new double[IN_MEMORY_ARRAY_SIZE];

    public MotionProcessor(){
        frameMotionBuffer = new Buffer<>(BUFFER_SIZE);
    }

    public List<WandMotion> scanFrame(Mat motionFrame, Mat originalFrame){
        LinkedList<PointCluster> clusters = createClustersFromImage(motionFrame, originalFrame);

        List<WandMotion> wandMotions = processFrameData(clusters);
        frameMotionBuffer.add(clusters);
        return wandMotions;
    }

    public List<WandMotion> scanFrame(BufferedImage motionFrame, BufferedImage originalFrame){
        LinkedList<PointCluster> clusters = createClustersFromImage(motionFrame, originalFrame);

        List<WandMotion> wandMotions = processFrameData(clusters);
        frameMotionBuffer.add(clusters);
        return wandMotions;
    }

    private LinkedList<PointCluster> createClustersFromImage(Mat motionFrame, Mat originalFrame) {
        ClusterCreator clusterCreator = new ClusterCreator();
        int width = motionFrame.cols();
        int height = motionFrame.rows();
        for (int columnIndex = 0; columnIndex < width; columnIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
            for (int rowIndex = 0; rowIndex < height; rowIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
                if (motionFrame.get(rowIndex, columnIndex)[0] > 0) {
                    double pixelIntensity = pixelIntensity(originalFrame.get(rowIndex, columnIndex));
                    if(pixelIntensity > Grimoire.UserSettings.INTENSITY_THRESHOLD){
                        clusterCreator.handle(columnIndex, rowIndex);
                    }
                }
            }
        }
        return clusterCreator.getClusters();
    }

    private LinkedList<PointCluster> iterateOverImageInMemory(Mat motion, Mat original){
        ClusterCreator clusterCreator = new ClusterCreator();
        loadImagesIntoMemory(motion, original);

        for (int i = 0; i < IN_MEMORY_ARRAY_SIZE; i++) {
            if (IN_MEMORY_MOTION[i] > 0) {
                double pixelIntensity = pixelIntensity(IN_MEMORY_IMAGE[i]);
                if(pixelIntensity > Grimoire.UserSettings.INTENSITY_THRESHOLD){
                    int y = yFromArray(i);
                    int x = xFromArray(i, y);
                    clusterCreator.handle(x, y);
                }
            }
        }
        return clusterCreator.getClusters();
    }

    private int yFromArray(int arrayIndex){
        return arrayIndex / FRAME_WIDTH;
    }

    private int xFromArray(int arrayIndex, int yPosition){
        return arrayIndex - (FRAME_WIDTH * yPosition);
    }

    private void loadImagesIntoMemory(Mat motion, Mat image){
        image.convertTo(image, CvType.CV_64FC3);
        motion.convertTo(motion, CvType.CV_64FC3);
        image.get(0, 0, IN_MEMORY_IMAGE);
        motion.get(0, 0, IN_MEMORY_MOTION);
    }

    private LinkedList<PointCluster> createClustersFromImage(BufferedImage motionFrame, BufferedImage originalFrame) {
        ClusterCreator clusterCreator = new ClusterCreator();
        int width = motionFrame.getWidth();
        int height = motionFrame.getHeight();
        for (int columnIndex = 0; columnIndex < width; columnIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
            for (int rowIndex = 0; rowIndex < height; rowIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
                if (motionFrame.getRGB(columnIndex, rowIndex) > 0) {
                    double pixelIntensity = pixelIntensity(originalFrame.getRGB(rowIndex, columnIndex));
                    if(pixelIntensity > Grimoire.UserSettings.INTENSITY_THRESHOLD){
                        clusterCreator.handle(columnIndex, rowIndex);
                    }
                }
            }
        }
        return clusterCreator.getClusters();
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
