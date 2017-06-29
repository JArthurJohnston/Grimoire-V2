package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.ClusterCreator;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class ImageProcessor implements Runnable{

    private ByteArrayRingBuffer ringBuffer;
    private boolean isRunning;
    private ClusterCreator clusterCreator;
    private RingBuffer<List<PointCluster>> bufferedClusters;

    public ImageProcessor(ByteArrayRingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.clusterCreator = new ClusterCreator();
        this.bufferedClusters = new RingBuffer<>(32);
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            byte[] framePixels = this.ringBuffer.read();
            processFrame(framePixels);
        }
    }

    protected void processFrame(byte[] framePixels) {
        LinkedList<PointCluster> pointClusters = clusterCreator.clusterPixels(framePixels);
        try {
            this.bufferedClusters.put(pointClusters);
        } catch (InterruptedException e) {
        }
    }

    public RingBuffer<List<PointCluster>> getBufferedClusters(){
        return this.bufferedClusters;
    }

    public ClusterCreator getClusterCreator() {
        return clusterCreator;
    }

    public ByteArrayRingBuffer getRingBuffer() {
        return ringBuffer;
    }
}
