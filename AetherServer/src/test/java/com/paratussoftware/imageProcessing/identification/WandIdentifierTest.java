package com.paratussoftware.imageProcessing.identification;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import org.junit.Test;

import static org.junit.Assert.*;

public class WandIdentifierTest {

    @Test
    public void testIdentificationSettings() throws Exception{
        assertEquals(50, Settings.MAX_WAND_SIZE);
        assertEquals(1, Settings.MIN_WAND_SIZE);
    }

    @Test
    public void testIsPossibleWandPoint() throws Exception{
        PointCluster wandCluster = PointCluster.newWith(0, 0);
        wandCluster.addPoint(49, 49);

        assertTrue(WandIdentifier.isPossibleWandPoint(wandCluster));

        PointCluster notBigEnoughCluster = PointCluster.newWith(0, 0);
        notBigEnoughCluster.addPoint(51, 51);

        assertFalse(WandIdentifier.isPossibleWandPoint(notBigEnoughCluster));

        PointCluster notSmallEnoughCluster = PointCluster.newWith(0, 0);

        assertFalse(WandIdentifier.isPossibleWandPoint(notSmallEnoughCluster));

        PointCluster notSquarishCluster = PointCluster.newWith(0, 0);
        notSquarishCluster.addPoint(11, 49);

        assertFalse(WandIdentifier.isPossibleWandPoint(notSquarishCluster));
    }


}