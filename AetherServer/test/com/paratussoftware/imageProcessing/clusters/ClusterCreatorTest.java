package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.ImageProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ClusterCreatorTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    int originalWidth, originalHeight;

    @Before
    public void setUp() throws Exception {
        originalWidth = Settings.IMAGE_WIDTH;
        originalHeight = Settings.IMAGE_HEIGHT;
    }

    @After
    public void tearDown() throws Exception {
        Settings.IMAGE_WIDTH = originalWidth;
        Settings.IMAGE_HEIGHT = originalHeight;
    }

    @Test
    public void testHandleCoordinates() throws Exception{
        ClusterCreator clusterCreator = new ClusterCreator();
        LinkedList<PointCluster> pointClusters = new LinkedList<>();

        clusterCreator.handle(5, 4, pointClusters);
        clusterCreator.handle(7, 8, pointClusters);

        clusterCreator.handle(29, 45, pointClusters);

        assertEquals(2, pointClusters.size());

        PointCluster firstCluster = pointClusters.getFirst();
        PointCluster secondCluster = pointClusters.getLast();

        assertEquals(5, firstCluster.left());
        assertEquals(7, firstCluster.right());
        assertEquals(4, firstCluster.top());
        assertEquals(8, firstCluster.bottom());

        assertEquals(29, secondCluster.left());
        assertEquals(29, secondCluster.right());
        assertEquals(45, secondCluster.top());
        assertEquals(45, secondCluster.bottom());
    }

    @Test
    public void testClusterPixels() throws Exception{
        ClusterCreator clusterCreator = new ClusterCreator();
        byte[] imageBytes = readTestImageBytes("./test-res/testFrame.jpg");

        List<PointCluster> clusters = clusterCreator.clusterPixels(imageBytes);
        assertEquals(75, clusters.size());
    }

    @Test
    public void testClusterPixels_blackImage() throws Exception{
        ClusterCreator clusterCreator = new ClusterCreator();
        byte[] imageBytes = readTestImageBytes("./test-res/blackFrame.jpg");

        List<PointCluster> clusters = clusterCreator.clusterPixels(imageBytes);
        assertTrue(clusters.isEmpty());
    }

    private byte[] readTestImageBytes(String filename){
        Settings.IMAGE_WIDTH = 800;
        Mat frame = Highgui.imread(filename);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(frame, frame, 25, 255, Imgproc.THRESH_BINARY);
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageBytes = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        frame.get(0,0,imageBytes);
        return imageBytes;
    }


}