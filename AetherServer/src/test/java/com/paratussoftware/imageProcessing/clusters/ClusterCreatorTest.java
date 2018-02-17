package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.config.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import java.util.LinkedList;
import java.util.List;

import static com.paratussoftware.helpers.TestImageReader.readImageDataFromFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClusterCreatorTest {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private int originalWidth;
    private ClusterCreator clusterCreator;

    @Before
    public void setUp() {
        originalWidth = Settings.IMAGE_WIDTH;
        Settings.IMAGE_WIDTH = 640;
        clusterCreator = new ClusterCreator();
    }

    @After
    public void tearDown() {
        Settings.IMAGE_WIDTH = originalWidth;
    }

    @Test
    public void testHandleCoordinates(){
        LinkedList<PointCluster> pointClusters = new LinkedList<>();

        clusterCreator.handle(5, 4, pointClusters);
        clusterCreator.handle(7, 8, pointClusters);

        clusterCreator.handle(29, 45, pointClusters);

        assertEquals(2, pointClusters.size());

        PointCluster firstCluster = pointClusters.getFirst();
        PointCluster secondCluster = pointClusters.getLast();

        checkClusterProperties(firstCluster, 5, 7, 4, 8);
        checkClusterProperties(secondCluster, 29, 29, 45, 45);
    }

    private void checkClusterProperties(PointCluster cluster, int left, int right, int top, int bottom){
        assertEquals("Left side doesn't match", left, cluster.left());
        assertEquals("Right side doesn't match",right, cluster.right());
        assertEquals("Top side doesn't match",top, cluster.top());
        assertEquals("Bottom side doesn't match",bottom, cluster.bottom());
    }

    @Test
    public void testClusterPixels(){
        byte[] imageData = readImageDataFromFile("testFrame.jpg");

        List<PointCluster> clusters = clusterCreator.clusterPixels(imageData);
        assertEquals(7, clusters.size());
    }

    @Test
    public void testClusterPixels_blackImage(){
        byte[] imageData = readImageDataFromFile("blackFrame.jpg");

        List<PointCluster> clusters = clusterCreator.clusterPixels(imageData);
        assertTrue(clusters.isEmpty());
    }



}