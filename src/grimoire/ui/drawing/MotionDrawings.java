package grimoire.ui.drawing;

import grimoire.image_analysis.clusters.PointCluster;
import grimoire.image_analysis.processors.WandMotion;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    public static BufferedImage drawMotionsTo(BufferedImage image, List<WandMotion> wandMotions){
        Graphics graphics = image.getGraphics();
        for (WandMotion wandMotion : wandMotions) {
            PointCluster cluster = wandMotion.getCurrentWandPoint();
            MotionDrawings.drawCluster(graphics, cluster, Color.CYAN);
            MotionDrawings.drawMotionTail(wandMotion.getPastWandPoints(), graphics, cluster);
        }
        return image;
    }

}
