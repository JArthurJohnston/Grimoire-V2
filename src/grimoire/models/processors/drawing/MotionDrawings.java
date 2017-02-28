package grimoire.models.processors.drawing;

import grimoire.models.clusters.PointCluster;
import java.awt.*;
import java.util.List;

public class MotionDrawings {

    public static void drawCluster(Graphics graphics, PointCluster cluster, Color color){
        graphics.setColor(color);
        graphics.drawRect(cluster.xCoord(), cluster.yCoord(), cluster.width(), cluster.height());
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
}
