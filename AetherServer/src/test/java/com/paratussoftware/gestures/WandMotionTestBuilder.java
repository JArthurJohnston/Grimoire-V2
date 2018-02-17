package com.paratussoftware.gestures;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;

public class WandMotionTestBuilder {

    public static WandMotion createDownwardsLeftWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(0, 0);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(10, 10);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createDownwardsRightWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(10, 10);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(0, 0);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createUpwardsLeftWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(10, 0);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(0, 10);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createUpwardsRightWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(0, 10);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(10, 0);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createUpwardsWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(5, 5);
        PointCluster secondFramePoint = PointCluster.newWith(5, 0);
        PointCluster thirdFramePoint = PointCluster.newWith(5, 0);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createDownwardsWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(5, 5);
        PointCluster secondFramePoint = PointCluster.newWith(5, 10);
        PointCluster thirdFramePoint = PointCluster.newWith(5, 10);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createRightwardsWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(0, 5);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(10, 5);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }

    public static WandMotion createLeftwardsWandMotion() throws InterruptedException {
        PointCluster firstFramePoint = PointCluster.newWith(15, 5);
        PointCluster secondFramePoint = PointCluster.newWith(5, 5);
        PointCluster thirdFramePoint = PointCluster.newWith(0, 5);

        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(secondFramePoint);
        clusterBuffer.put(firstFramePoint);

        return new WandMotion(thirdFramePoint, clusterBuffer);
    }
}
