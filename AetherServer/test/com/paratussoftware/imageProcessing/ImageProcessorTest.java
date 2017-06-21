package com.paratussoftware.imageProcessing;

import com.paratussoftware.imageProcessing.clusters.PointCluster;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.List;

import static org.junit.Assert.*;

public class ImageProcessorTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test
    public void testScanImage() throws Exception{
        ImageProcessor imageProcessor = new ImageProcessor();
        BufferedImage image = readTestImage("./test-res/testFrame.jpg");

        List<PointCluster> clusters = imageProcessor.scan(image);

        assertEquals(77, clusters.size());
    }
    @Test
    public void testScanBlackImage() throws Exception{
        ImageProcessor imageProcessor = new ImageProcessor();
        BufferedImage image = readTestImage("./test-res/blackFrame.jpg");

        List<PointCluster> clusters = imageProcessor.scan(image);

        assertEquals(0, clusters.size());
    }

    @Test
    public void testScanByteArray() throws Exception{
        ImageProcessor imageProcessor = new ImageProcessor();
        byte[] imageBytes = readTestImageBytes("./test-res/testFrame.jpg");

        List<PointCluster> clusters = imageProcessor.scan(imageBytes);
        assertEquals(77, clusters.size());
    }

    @Test
    public void testScanByteArray_blackImage() throws Exception{
        ImageProcessor imageProcessor = new ImageProcessor();
        byte[] imageBytes = readTestImageBytes("./test-res/blackFrame.jpg");

        List<PointCluster> clusters = imageProcessor.scan(imageBytes);
        assertTrue(clusters.isEmpty());
    }


    private byte[] readTestImageBytes(String filename){
        ImageProcessor.IMAGE_WIDTH = 800;
        Mat frame = Highgui.imread(filename);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(frame, frame, 25, 255, Imgproc.THRESH_BINARY);
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageBytes = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        frame.get(0,0,imageBytes);
        return imageBytes;
    }

    private BufferedImage readTestImage(String filename){
        Mat frame = Highgui.imread(filename);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(frame, frame, 25, 255, Imgproc.THRESH_BINARY);
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageBytes = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        frame.get(0,0,imageBytes);
        return image;

    }

}