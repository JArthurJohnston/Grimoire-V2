package grimoire.image_analysis.processors.Identification;

import grimoire.Grimoire;
import grimoire.image_analysis.clusters.PointCluster;
import grimoire.image_analysis.buffer.Buffer;
import org.junit.Test;

import java.util.LinkedList;

import static grimoire.image_analysis.processors.Identification.WandFinder.*;
import static org.junit.Assert.*;

public class WandFinderTest {

    @Test
    public void isPossibleWandPoint_size() throws Exception {
        Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT = 5;
        Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT = 2;

        PointCluster pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(4, 4);

        assertTrue(isPossibleWandPoint(pointCluster));

        pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(5, 5);

        assertFalse(isPossibleWandPoint(pointCluster));

        pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(1, 1);

        assertFalse(isPossibleWandPoint(pointCluster));

        pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(2, 2);

        assertFalse(isPossibleWandPoint(pointCluster));
    }

    @Test
    public void testIsPossibleWandPoint_squarish() throws Exception{
        Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT = 15;
        Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT = 5;

        PointCluster pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(7, 7);

        assertTrue(isPossibleWandPoint(pointCluster));

        pointCluster = PointCluster.newWith(0, 0);
        pointCluster.add(6, 14);

        assertFalse(isPossibleWandPoint(pointCluster));
    }

    @Test
    public void testNearestWandPointTo_empty() throws Exception{
        PointCluster cluster1 = PointCluster.newWith(1, 1);
        cluster1.add(4, 4);

        PointCluster nearestCluster = nearestNearbyWandPointTo(cluster1, new LinkedList<>());

        assertSame(cluster1, nearestCluster);
    }


    @Test
    public void testNearestWandPointTo() throws Exception{
        Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT = 5;
        Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT = 0;

        PointCluster cluster1 = PointCluster.newWith(1, 1);
        cluster1.add(4, 4);

        PointCluster cluster2 = PointCluster.newWith(3, 3);
        cluster2.add(7, 7);

        PointCluster cluster3 = PointCluster.newWith(4, 4);
        cluster2.add(8, 8);

        LinkedList<PointCluster> pointClusters = new LinkedList<>();
        pointClusters.add(cluster2);
        pointClusters.add(cluster3);

        PointCluster nearestCluster = nearestNearbyWandPointTo(cluster1, pointClusters);

        assertSame(nearestCluster, cluster2);

        pointClusters = new LinkedList<>();
        pointClusters.add(cluster1);
        pointClusters.add(cluster3);

        nearestCluster = nearestNearbyWandPointTo(cluster2, pointClusters);

        assertSame(cluster1, nearestCluster);

        pointClusters = new LinkedList<>();
        pointClusters.add(cluster1);
        pointClusters.add(cluster2);

        nearestCluster = nearestNearbyWandPointTo(cluster3, pointClusters);

        assertSame(cluster2, nearestCluster);
    }

    @Test
    public void testFindPastWandPointsForCluster() throws Exception{
        Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT = 1;
        Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT = 5;

        LinkedList<PointCluster> frame1Clusters = new LinkedList<>();
        PointCluster frame1WandPoint = createCluster(4, 3, 8, 7);
        PointCluster noise1Cluster = createCluster(11, 10, 15, 14);
        frame1Clusters.add(frame1WandPoint);
        frame1Clusters.add(noise1Cluster);

        LinkedList<PointCluster> frame2Clusters = new LinkedList<>();
        PointCluster frame2WandPoint = createCluster(6, 3, 10, 7);
        PointCluster noise2Cluster = createCluster(8, 10, 12, 14);
        frame2Clusters.add(frame2WandPoint);
        frame2Clusters.add(noise2Cluster);

        LinkedList<PointCluster> frame3Clusters = new LinkedList<>();
        PointCluster frame3WandPoint = createCluster(8, 4, 12, 8);
        PointCluster frame3Noise = createCluster(8, 11, 12, 15);
        frame3Clusters.add(frame3WandPoint);
        frame3Clusters.add(frame3Noise);

        LinkedList<PointCluster> frame4Clusters = new LinkedList<>();
        PointCluster frame4WandPoint = createCluster(11, 5, 15, 9);
        PointCluster frame4Noise = createCluster(11, 11, 15, 15);
        frame4Clusters.add(frame4WandPoint);
        frame4Clusters.add(frame4Noise);

        Buffer<LinkedList<PointCluster>> listBuffer = new Buffer<>(4);
        listBuffer.add(frame1Clusters);
        listBuffer.add(frame2Clusters);
        listBuffer.add(frame3Clusters);
        listBuffer.add(frame4Clusters);

        PointCluster currentCluster = createCluster(1, 2, 5, 6);

        LinkedList<PointCluster> pastWandPoints = findPastWandPointsFor(currentCluster, listBuffer.fifoIterator());

        assertEquals(4, pastWandPoints.size());
        assertSame(frame1WandPoint, pastWandPoints.get(0));
        assertSame(frame2WandPoint, pastWandPoints.get(1));
        assertSame(frame3WandPoint, pastWandPoints.get(2));
        assertSame(frame4WandPoint, pastWandPoints.get(3));
    }

    private PointCluster createCluster(int leftBoundary, int topBoundary, int rightBoundary, int bottonBoundary){
        PointCluster pointCluster = PointCluster.newWith(leftBoundary, topBoundary);
        pointCluster.add(rightBoundary, bottonBoundary);
        return pointCluster;
    }



}