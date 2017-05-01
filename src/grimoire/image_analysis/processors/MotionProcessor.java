package grimoire.image_analysis.processors;

import grimoire.Grimoire;
import grimoire.image_analysis.buffer.Buffer;
import grimoire.image_analysis.clusters.ClusterCreator;
import grimoire.image_analysis.clusters.PointCluster;
import grimoire.threads.ProcessedFrameData;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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

    public ProcessedFrameData scanFrame(Mat motionFrame){
        BufferedImage image = matToBufferedImage(motionFrame);
        List<WandMotion> wandMotions = scanFrame(image);
        return new ProcessedFrameData(image, wandMotions);
    }

    private List<WandMotion> scanFrame(BufferedImage motionFrame){
        LinkedList<PointCluster> clusters = createClustersFromImage(motionFrame);

        List<WandMotion> wandMotions = processFrameData(clusters);
        frameMotionBuffer.add(clusters);
        return wandMotions;
    }

    private LinkedList<PointCluster> createClustersFromImage(BufferedImage motionFrame) {
        ClusterCreator clusterCreator = new ClusterCreator();
        int width = motionFrame.getWidth();
        int height = motionFrame.getHeight();
        for (int columnIndex = 0; columnIndex < width; columnIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
            for (int rowIndex = 0; rowIndex < height; rowIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
                if (pixelIntensity(motionFrame.getRGB(columnIndex, rowIndex)) > Grimoire.UserSettings.INTENSITY_THRESHOLD) {
                    clusterCreator.handle(columnIndex, rowIndex);
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

    private BufferedImage inMemoryImage;
    private byte[] imageBuffer;

    private void initializeImageInmemory(Mat frame) {
        if(imageBuffer == null || inMemoryImage == null){
            int type = 0;
            if (frame.channels() == 1) {
                type = BufferedImage.TYPE_BYTE_GRAY;
            } else if (frame.channels() == 3) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            inMemoryImage = new BufferedImage(frame.width(), frame.height(), type);
            WritableRaster raster = inMemoryImage.getRaster();
            imageBuffer = ((DataBufferByte) raster.getDataBuffer()).getData();
        }
    }

    private BufferedImage matToBufferedImage(Mat frame){
        initializeImageInmemory(frame);
        frame.get(0, 0, imageBuffer);
        return inMemoryImage;
    }

}
