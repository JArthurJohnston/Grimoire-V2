package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.imageProcessing.clusters.ClusterCreator;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class ImageProcessor implements Runnable{

    private ByteArrayRingBuffer ringBuffer;
    private boolean isRunning;
    private ClusterCreator clusterCreator;

    public ImageProcessor(ByteArrayRingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.clusterCreator = new ClusterCreator();
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
    }

    public ClusterCreator getClusterCreator() {
        return clusterCreator;
    }

    public ByteArrayRingBuffer getRingBuffer() {
        return ringBuffer;
    }
}
