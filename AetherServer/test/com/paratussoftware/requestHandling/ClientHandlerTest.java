package com.paratussoftware.requestHandling;

import com.paratussoftware.imageProcessing.ImageProcessor;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void testConstruction() throws Exception{
        Socket mockSocket = new Socket();
        LinkedBlockingQueue<BufferedImage> bufferedImages = new LinkedBlockingQueue<>();
        ImageProcessor imageProcessor = new ImageProcessor();

        ClientHandler clientHandler = new ClientHandler(mockSocket, bufferedImages, imageProcessor);

        assertSame(mockSocket, clientHandler.getSocket());
        assertSame(bufferedImages, clientHandler.getImageQueue());
        assertSame(imageProcessor, clientHandler.getImageProcessor());
    }
    
    @Test
    public void testRun() throws Exception{
        
    }
    

}