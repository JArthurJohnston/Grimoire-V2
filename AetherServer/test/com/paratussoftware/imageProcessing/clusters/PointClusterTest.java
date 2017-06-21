package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.config.Settings;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointClusterTest {

    @Test
    public void testConstruction() throws Exception{
        PointCluster pointCluster = PointCluster.newWith(5, 3);

        assertEquals(5, pointCluster.right());
        assertEquals(5, pointCluster.left());
        assertEquals(3, pointCluster.top());
        assertEquals(3, pointCluster.bottom());
    }

    @Test
    public void testAddPoint() throws Exception{
        PointCluster pointCluster = PointCluster.newWith(0,0);

        pointCluster.addPoint(5, 5);

        assertEquals(5, pointCluster.right());
        assertEquals(0, pointCluster.left());
        assertEquals(0, pointCluster.top());
        assertEquals(5, pointCluster.bottom());
    }

    @Test
    public void testCenterPoint() throws Exception{
        PointCluster pointCluster = PointCluster.newWith(0,0);
        pointCluster.addPoint(5, 5);

        assertEquals(3, pointCluster.centerPoint().getX(), 0.001);
        assertEquals(3, pointCluster.centerPoint().getY(), 0.001);
    }

    @Test
    public void testDistanceTo() throws Exception{
        PointCluster pointCluster = PointCluster.newWith(0,0);
        pointCluster.addPoint(5, 5);

        PointCluster pointCluster2 = PointCluster.newWith(10, 10);
        pointCluster2.addPoint(15, 15);

        assertEquals(14.142, pointCluster.distanceTo(pointCluster2), 0.001);
    }

    @Test
    public void testDistanceTo_Point() throws Exception{

        PointCluster pointCluster = PointCluster.newWith(0,0);
        pointCluster.addPoint(5, 5);

        assertEquals(16.97, pointCluster.distanceTo(15, 15), 0.001);
    }

    @Test
    public void testWidthAndHeight() throws Exception{
        PointCluster pointCluster = PointCluster.newWith(0,0);
        pointCluster.addPoint(5, 5);

        assertEquals(5, pointCluster.width());
        assertEquals(5, pointCluster.height());
    }

    @Test
    public void testContains() throws Exception{
        assertEquals(10, Settings.CLUSTER_CONTAINTS_DISTANCE);

        PointCluster pointCluster = PointCluster.newWith(0,0);

        assertFalse(pointCluster.contains(-11, -11));
        assertTrue(pointCluster.contains(0,0));
        assertTrue(pointCluster.contains(10,10));
        assertFalse(pointCluster.contains(11,11));
    }


}