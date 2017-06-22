package com.paratussoftware.requestHandling;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.ImageProcessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(32, Settings.IMAGE_WIDTH * Settings.IMAGE_HEIGHT);

        ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);
        new Thread(imageProcessor).start();
        while (!this.socket.isClosed()){
            InputStream inputStream = null;
            try {
                inputStream = this.socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                dataInputStream.readFully(ringBuffer.push());
            } catch (IOException e) {}
        }
        imageProcessor.stop();
    }
}
