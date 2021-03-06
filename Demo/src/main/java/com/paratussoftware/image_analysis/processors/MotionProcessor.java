package com.paratussoftware.image_analysis.processors;

import com.paratussoftware.Grimoire;
import com.paratussoftware.image_analysis.ImageAnalysisHelper;
import com.paratussoftware.image_analysis.buffer.Buffer;
import com.paratussoftware.image_analysis.clusters.PointCluster;
import com.paratussoftware.image_analysis.processors.Identification.WandFinder;
import com.paratussoftware.threads.ProcessedFrameData;
import com.paratussoftware.image_analysis.clusters.ClusterCreator;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.paratussoftware.image_analysis.ImageAnalysisHelper.pixelIntensity;

public class MotionProcessor {
    private final Buffer<LinkedList<PointCluster>> frameMotionBuffer;
    private static final int FPS = 30;
    private static final double TIME = 1.5;
    private static final int BUFFER_SIZE = (int)(FPS * TIME);
    private int imageWidth;
    private int imageHeight;
    private BufferedImage inMemoryImage;
    private byte[] imageBuffer;

    public MotionProcessor(){
        frameMotionBuffer = new Buffer<>(BUFFER_SIZE);
    }

    public ProcessedFrameData scanFrame(Mat motionFrame){
        //this takes almost no time at all, 1 milisecond at worst
        BufferedImage image = writeFrameToBuffer(motionFrame);

        //4-6 miliseconds sometimes jumping to 10-15
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
        //4 - 7 miliseconds
        ClusterCreator clusterCreator = new ClusterCreator();
        for (int columnIndex = 0; columnIndex < imageWidth; columnIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
            for (int rowIndex = 0; rowIndex < imageHeight; rowIndex += Grimoire.UserSettings.SCAN_RESOLUTION) {
                if (ImageAnalysisHelper.pixelIntensity(motionFrame.getRGB(columnIndex, rowIndex)) > Grimoire.UserSettings.INTENSITY_THRESHOLD) {
                    clusterCreator.handle(columnIndex, rowIndex);
                }
            }
        }
        return clusterCreator.getClusters();
    }

    private List<WandMotion> processFrameData(List<PointCluster> clusters){
        //almost nothing, 0 - 2 miliseconds
        List<WandMotion> wandMotions = new LinkedList<>();
        for (PointCluster pointCluster : clusters) {
            if(WandFinder.isPossibleWandPoint(pointCluster)){
                Iterator<LinkedList<PointCluster>> iterator = frameMotionBuffer.fifoIterator();
                LinkedList<PointCluster> pastWandClusters = WandFinder.findPastWandPointsFor(pointCluster, iterator);
                wandMotions.add(new WandMotion(pointCluster, pastWandClusters));
            }
        }
        return wandMotions;
    }

    public void initialize(Mat image){
        imageWidth = image.width();
        imageHeight = image.height();
        inMemoryImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = inMemoryImage.getRaster();
        imageBuffer = ((DataBufferByte) raster.getDataBuffer()).getData();
        image.get(0,0, imageBuffer);
    }

    private BufferedImage writeFrameToBuffer(Mat frame){
        frame.get(0, 0, imageBuffer);
        return inMemoryImage;
    }

}
