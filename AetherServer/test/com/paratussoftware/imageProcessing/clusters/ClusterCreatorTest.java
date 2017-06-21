package com.paratussoftware.imageProcessing.clusters;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ClusterCreatorTest {

    @Test
    public void testConstruction() throws Exception{
        ClusterCreator clusterCreator = new ClusterCreator();

        assertTrue(clusterCreator.getClusters().isEmpty());
    }

    @Test
    public void testHandleCoordinates() throws Exception{
        ClusterCreator clusterCreator = new ClusterCreator();

        clusterCreator.handle(5, 4);
        clusterCreator.handle(7, 8);

        clusterCreator.handle(29, 45);

        LinkedList<PointCluster> clusters = clusterCreator.getClusters();
        assertEquals(2, clusters.size());

        PointCluster firstCluster = clusters.getFirst();
        PointCluster secondCluster = clusters.getLast();

        assertEquals(5, firstCluster.left());
        assertEquals(7, firstCluster.right());
        assertEquals(4, firstCluster.top());
        assertEquals(8, firstCluster.bottom());

        assertEquals(29, secondCluster.left());
        assertEquals(29, secondCluster.right());
        assertEquals(45, secondCluster.top());
        assertEquals(45, secondCluster.bottom());


    }

}