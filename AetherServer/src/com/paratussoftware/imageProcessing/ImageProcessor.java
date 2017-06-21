package com.paratussoftware.imageProcessing;

import com.paratussoftware.imageProcessing.clusters.ClusterCreator;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageProcessor {

    public List<PointCluster> scan(BufferedImage image){
        ClusterCreator clusterCreator = new ClusterCreator();
        for(int y = 0; y < image.getHeight(); y += 2){
            for (int x = 0; x < image.getWidth(); x += 2) {
                if(pixelIntensity(image.getRGB(x, y)) > 0){
                    clusterCreator.handle(x, y);
                }
            }
        }
        return clusterCreator.getClusters();
    }

    public static int IMAGE_WIDTH = 848;

    public List<PointCluster> scan(byte[] image){
        ClusterCreator clusterCreator = new ClusterCreator();
        for (int i = 0; i < image.length; i++) {
            if((image[i] & 255 )> 0){// java bytes are signed, C bytes arent. so white values will be negative
                int y = i/IMAGE_WIDTH;
                int x = i - IMAGE_WIDTH * y;
                clusterCreator.handle(x, y);
            }
        }
        return clusterCreator.getClusters();
    }

    private static final float RED_SENSITIVITY = 0.2126F;
    private static final float GREEN_SENSITIVITY = 0.7152F;
    private static final float BLUE_SENSITIVITY = 0.0722F;

    public static float pixelIntensity(int rgbValue){
        int redValue = (rgbValue >>> 16) & 0xFF;
        int greenValue = (rgbValue >>> 8) & 0xFF;
        int blueValue = (rgbValue >>> 0) & 0xFF;
        return ((redValue * RED_SENSITIVITY +
                greenValue * GREEN_SENSITIVITY +
                blueValue * BLUE_SENSITIVITY) / 255);
    }
}
