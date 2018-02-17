package com.paratussoftware.helpers;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import static com.paratussoftware.helpers.TestFileReader.openFile;

public class TestImageReader {

    public static byte[] readImageDataFromFile(String filename){
        File file = openFile(filename);
        Mat frame = Highgui.imread(file.getAbsolutePath());
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(frame, frame, 25, 255, Imgproc.THRESH_BINARY);
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageBytes = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        frame.get(0,0,imageBytes);
        return imageBytes;
    }
}
