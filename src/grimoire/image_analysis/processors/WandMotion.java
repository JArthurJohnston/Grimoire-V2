package grimoire.image_analysis.processors;

import grimoire.image_analysis.clusters.PointCluster;

import java.util.LinkedList;

import static grimoire.image_analysis.processors.PointMath.distanceBetween;

public class WandMotion implements Comparable<WandMotion>{

    private final PointCluster currentWandPoint;
    private final LinkedList<PointCluster> pastWandPoints;

    WandMotion(PointCluster cluster, LinkedList<PointCluster> pastWandPoints){
        this.currentWandPoint = cluster;
        this.pastWandPoints = pastWandPoints;
    }

    public PointCluster getCurrentWandPoint() {
        return currentWandPoint;
    }

    public LinkedList<PointCluster> getPastWandPoints() {
        return pastWandPoints;
    }

    double length(){
        double length = 0;
        PointCluster currentPoint = currentWandPoint;
        for (PointCluster eachPastPoint : pastWandPoints) {
            length += distanceBetween(currentPoint.getCenterPoint(), eachPastPoint.getCenterPoint());
            currentPoint = eachPastPoint;
        }
        return length;
    }

    @Override
    public int compareTo(WandMotion other) {
        return Double.compare(this.length(), other.length());
    }
}
