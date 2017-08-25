package com.paratussoftware.requestHandling;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.ImageProcessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(final Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void run() {
        final ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(32, Settings.IMAGE_WIDTH * Settings.IMAGE_HEIGHT);

        final ImageProcessor imageProcessor = startProcessingThread(ringBuffer);

        while (!this.socket.isClosed()) {
            InputStream inputStream = null;
            try {
                inputStream = this.socket.getInputStream();
                final DataInputStream dataInputStream = new DataInputStream(inputStream);
                dataInputStream.readFully(ringBuffer.push());
            } catch (final IOException e) {
            }
        }

        imageProcessor.stop();
    }

    private ImageProcessor startProcessingThread(final ByteArrayRingBuffer ringBuffer) {
        final ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);
        new Thread(imageProcessor).start();
        return imageProcessor;
    }
}
