package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.config.Settings;

import java.util.LinkedList;

public class ClusterCreator {

    public LinkedList<PointCluster> clusterPixels(byte[] pixels){
        LinkedList<PointCluster> pointClusters = new LinkedList<>();
        for (int i = 0; i < pixels.length; i++) {
            if((pixels[i] & 255) > 0){// java bytes are signed, C bytes arent. so white values will be negative
                int y = i/ Settings.IMAGE_WIDTH;
                int x = i - Settings.IMAGE_WIDTH * y;
                this.handle(x, y, pointClusters);
            }
        }
        return pointClusters;
    }

    protected void handle(int xCoord, int yCoord, LinkedList<PointCluster> clusters){
        for (PointCluster eachCluster :  clusters) {
            if (eachCluster.contains(xCoord, yCoord)){
                eachCluster.addPoint(xCoord, yCoord);
                return;
            }
        }
        clusters.add(PointCluster.newWith(xCoord, yCoord));
    }
}
