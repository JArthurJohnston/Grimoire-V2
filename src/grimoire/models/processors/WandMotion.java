package grimoire.models.processors;

import grimoire.models.clusters.PointCluster;
import java.util.List;

public class WandMotion {

    private final PointCluster cluster;
    private final List<PointCluster> pastWandPoints;

    public WandMotion(PointCluster cluster, List<PointCluster> pastWandPoints){
        this.cluster = cluster;
        this.pastWandPoints = pastWandPoints;
    }

    public PointCluster getCluster() {
        return cluster;
    }

    public List<PointCluster> getPastWandPoints() {
        return pastWandPoints;
    }
}
