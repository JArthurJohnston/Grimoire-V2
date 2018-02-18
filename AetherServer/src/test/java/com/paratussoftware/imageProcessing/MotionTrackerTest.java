package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.PriorityBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.motions.WandMotion;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MotionTrackerTest {

    private MotionTracker motionTracker;
    private PriorityBuffer<WandMotion> wandMotionPriorityBuffer;


    @Before
    public void setUp(){
        wandMotionPriorityBuffer = new PriorityBuffer<>(4, (o1, o2) -> Double.compare(o2.length(), o1.length()));
        motionTracker = new MotionTracker(wandMotionPriorityBuffer);
    }

    @Test
    public void testConstruction() throws Exception {
        assertTrue(motionTracker.getTrackedMotions().isEmpty());
    }

    @Test
    public void testTracksClusters() throws Exception {
        final LinkedList<PointCluster> pointClusters = new LinkedList<>();
        final PointCluster possibleWandPoint = createPointCluster(0, 0, 5, 5);
        final PointCluster clusterThatsTooSmall = PointCluster.newWith(10, 10);
        pointClusters.add(possibleWandPoint);
        pointClusters.add(clusterThatsTooSmall);

        motionTracker.track(pointClusters);

        assertEquals(1, motionTracker.getTrackedMotions().size());
        final WandMotion trackedMotion = motionTracker.getTrackedMotions().get(0);
        assertSame(possibleWandPoint, trackedMotion.getCurrentCluster());
        assertTrue(trackedMotion.getPastClusters().isEmpty());
    }

    @Test
    public void testDefaultMotionTrackingDistance() {
        assertEquals(40, Settings.MOTION_TRACKING_DISTANCE);
    }

    @Test
    public void testMapsTrackedClustersToExistingMotions() throws Exception {
        final PointCluster firstCluster = PointCluster.newWith(0, 0);
        final WandMotion wandMotion = new WandMotion(firstCluster);
        motionTracker.getTrackedMotions().push(wandMotion);

        final PointCluster newCluster = PointCluster.newWith(1, 1);
        newCluster.addPoint(5, 5);
        motionTracker.track(Collections.singletonList(newCluster));

        assertEquals(1, motionTracker.getTrackedMotions().size());
        assertSame(newCluster, wandMotion.getCurrentCluster());
        assertEquals(firstCluster, wandMotion.getPastClusters().get(0));
    }
    
    private PointCluster createPointCluster(final int startX, final int startY, final int endX, final int endY) {
        final PointCluster pointCluster = PointCluster.newWith(startX, startY);
        pointCluster.addPoint(endX, endY);
        return pointCluster;
    }
}