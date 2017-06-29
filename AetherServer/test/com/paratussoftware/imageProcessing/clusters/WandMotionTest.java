package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.buffers.RingBuffer;
import org.junit.Test;

import static org.junit.Assert.*;

public class WandMotionTest {

    @Test
    public void testConstruction() throws Exception{
        PointCluster currentCluster = PointCluster.newWith(0, 0);
        RingBuffer<PointCluster> pointClusters = new RingBuffer<>(4);
        pointClusters.write(PointCluster.newWith(1,1));
        pointClusters.write(PointCluster.newWith(2,2));

        WandMotion wandMotion = new WandMotion(currentCluster, pointClusters);

        assertSame(currentCluster, wandMotion.getCurrentCluster());
        assertSame(pointClusters, wandMotion.getPastClusters());
    }

    @Test
    public void testConstructedWithCluster() throws Exception{
        PointCluster currentCluster = PointCluster.newWith(0, 0);
        WandMotion wandMotion = new WandMotion(currentCluster);
        
        assertSame(currentCluster, wandMotion.getCurrentCluster());
        assertTrue(wandMotion.getPastClusters().isEmpty());
    }

    @Test
    public void testCompareTo() throws Exception{
        PointCluster currentCluster1 = PointCluster.newWith(0, 0);
        RingBuffer<PointCluster> pointClusters1 = new RingBuffer<>(4);
        pointClusters1.write(PointCluster.newWith(0,5));
        pointClusters1.write(PointCluster.newWith(0,10));
        WandMotion wandMotion = new WandMotion(currentCluster1, pointClusters1);

        PointCluster currentCluster2 = PointCluster.newWith(0, 0);
        RingBuffer<PointCluster> pointClusters2 = new RingBuffer<>(4);
        pointClusters2.write(PointCluster.newWith(0,13));
        pointClusters2.write(PointCluster.newWith(0,16));
        WandMotion wandMotion2 = new WandMotion(currentCluster2, pointClusters2);

        assertEquals(-1, wandMotion.compareTo(wandMotion2));
        assertEquals(1, wandMotion2.compareTo(wandMotion));
        assertEquals(0, wandMotion.compareTo(wandMotion));
    }

    @Test
    public void testLength() throws Exception{
        PointCluster currentCluster = PointCluster.newWith(0, 0);
        RingBuffer<PointCluster> pointClusters = new RingBuffer<>(4);
        pointClusters.write(PointCluster.newWith(0,5));
        pointClusters.write(PointCluster.newWith(0,10));

        WandMotion wandMotion = new WandMotion(currentCluster, pointClusters);

        assertEquals(10, wandMotion.length(), 0.001);
    }

    @Test
    public void testAddCluster() throws Exception{
        PointCluster firstCluster = PointCluster.newWith(0, 0);
        WandMotion wandMotion = new WandMotion(firstCluster);
        PointCluster addedCluster = PointCluster.newWith(5, 5);

        wandMotion.addCluster(addedCluster);

        assertEquals(1, wandMotion.getPastClusters().size());
        assertSame(firstCluster, wandMotion.getPastClusters().get(0));

        assertSame(addedCluster, wandMotion.getCurrentCluster());
    }


}