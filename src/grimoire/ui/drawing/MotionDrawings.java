package grimoire.ui.drawing;

import grimoire.image_analysis.clusters.PointCluster;
import grimoire.image_analysis.processors.WandMotion;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.List;

public class MotionDrawings {

    public static void drawCluster(Graphics graphics, PointCluster cluster, Color color){
        graphics.setColor(color);
        graphics.drawRect(cluster.xCoordinate(), cluster.yCoordinate(), cluster.width(), cluster.height());
    }

    public static void drawMotionTail(List<PointCluster> clusters, Graphics graphics, PointCluster startingCluster) {
        PointCluster currentCuster = startingCluster;
        for (PointCluster eachCluster : clusters) {
            drawLineBetweenClusters(graphics, currentCuster, eachCluster);
            currentCuster = eachCluster;
        }
    }

    public static void drawLineBetweenClusters(Graphics graphics, PointCluster firstCluster, PointCluster lastCluster) {
        graphics.setColor(Color.MAGENTA);
        Point first = firstCluster.getCenterPoint();
        Point last = lastCluster.getCenterPoint();
        graphics.drawLine(first.x, first.y, last.x, last.y);
    }

    public static BufferedImage drawMotionFrame(List<WandMotion> wandMotions, Mat frameFromCamera){
        BufferedImage bufferedImage = matToBufferedImage(frameFromCamera);
        return drawMotionsTo(bufferedImage, wandMotions);
    }

    public static BufferedImage drawMotionsTo(BufferedImage image, List<WandMotion> wandMotions){
        Graphics graphics = image.getGraphics();
        for (WandMotion wandMotion : wandMotions) {
            PointCluster cluster = wandMotion.getCurrentWandPoint();
            MotionDrawings.drawCluster(graphics, cluster, Color.CYAN);
            MotionDrawings.drawMotionTail(wandMotion.getPastWandPoints(), graphics, cluster);
        }
        return image;
    }


    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final int IN_MEMORY_ARRAY_SIZE = FRAME_HEIGHT * FRAME_HEIGHT;
    private static final double[] IN_MEMORY_IMAGE = new double[IN_MEMORY_ARRAY_SIZE];
    private static final double[] IN_MEMORY_MOTION = new double[IN_MEMORY_ARRAY_SIZE];

//    private static

    public static BufferedImage matToBufferedImage(Mat frame){
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
}
