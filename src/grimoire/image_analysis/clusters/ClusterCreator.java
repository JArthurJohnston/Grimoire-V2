package grimoire.image_analysis.clusters;

import java.util.LinkedList;

public class ClusterCreator {

    private final LinkedList<PointCluster> clusters;

    public ClusterCreator(){
        clusters = new LinkedList<>();
    }

    public void handle(int xCoord, int yCoord){
        for (PointCluster eachCluster :  this.clusters) {
            if (eachCluster.contains(xCoord, yCoord)){
                eachCluster.add(xCoord, yCoord);
                return;
            }
        }
        clusters.add(PointCluster.newWith(xCoord, yCoord));
    }

    public LinkedList<PointCluster> getClusters(){
        return this.clusters;
    }


}
