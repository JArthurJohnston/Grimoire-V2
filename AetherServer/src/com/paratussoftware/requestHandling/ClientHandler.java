package com.paratussoftware.requestHandling;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BlockingQueue<BufferedImage> imageQueue;
    private ImageProcessor imageProcessor;

    public ClientHandler(Socket socket, BlockingQueue<BufferedImage> imageQueue, ImageProcessor imageProcessor) {

        this.socket = socket;
        this.imageQueue = imageQueue;
        this.imageProcessor = imageProcessor;
    }

    public Socket getSocket() {
        return socket;
    }

    public BlockingQueue<BufferedImage> getImageQueue() {
        return imageQueue;
    }

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()){
            BufferedImage image = new BufferedImage(Settings.IMAGE_WIDTH, Settings.IMAGE_HEIGHT, Settings.IMAGE_TYPE);
            byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

            InputStream inputStream = null;
            try {
                inputStream = this.socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                dataInputStream.readFully(imageData);
            } catch (IOException e) {
            }
            try {
                this.imageQueue.put(image);
            } catch (InterruptedException e) {
            }
        }
    }
}
