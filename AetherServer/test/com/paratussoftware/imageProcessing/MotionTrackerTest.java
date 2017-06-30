package com.paratussoftware.imageProcessing;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MotionTrackerTest {

    @Test
    public void testConstruction() throws Exception {
        final MotionTracker motionTracker = new MotionTracker();

        assertTrue(motionTracker.getTrackedMotions().isEmpty());
    }

    @Test
    public void testTracksClusters() throws Exception {
        final MotionTracker motionTracker = new MotionTracker();

        final LinkedList<PointCluster> pointClusters = new LinkedList<>();
        final PointCluster possibleWandPoint = createPointCluster(0, 0, 5, 5);
        final PointCluster clusterThantsTooSmall = PointCluster.newWith(10, 10);
        pointClusters.add(possibleWandPoint);
        pointClusters.add(clusterThantsTooSmall);

        motionTracker.track(pointClusters);

        assertEquals(1, motionTracker.getTrackedMotions().size());
        final WandMotion trackedMotion = motionTracker.getTrackedMotions().get(0);
        assertSame(possibleWandPoint, trackedMotion.getCurrentCluster());
        assertTrue(trackedMotion.getPastClusters().isEmpty());
    }

    @Test
    public void testMapsTrackedClustersToExistingMotions() throws Exception {
        assertEquals(40, Settings.MOTION_TRACKING_DISTANCE);

        final PointCluster firstCluster = PointCluster.newWith(0, 0);
        final WandMotion wandMotion = new WandMotion(firstCluster);
        final MotionTracker motionTracker = new MotionTracker();
        motionTracker.getTrackedMotions().add(wandMotion);

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