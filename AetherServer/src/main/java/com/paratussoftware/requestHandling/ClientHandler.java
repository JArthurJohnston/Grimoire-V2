package com.paratussoftware.requestHandling;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.gestures.Gesture;
import com.paratussoftware.imageProcessing.ImageProcessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final RingBuffer<Gesture> gestureBuffer;

    public ClientHandler(final Socket socket) {
        this.socket = socket;
        gestureBuffer = new RingBuffer<>(32);
    }

    @Override
    public void run() {
        final ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(32, Settings.IMAGE_WIDTH * Settings.IMAGE_HEIGHT);

        final ImageProcessor imageProcessor = startProcessingThread(ringBuffer);

        while (!this.socket.isClosed()) {
            InputStream inputStream;
            try {
                inputStream = this.socket.getInputStream();
                final DataInputStream dataInputStream = new DataInputStream(inputStream);
                dataInputStream.readFully(ringBuffer.push());
            } catch (final IOException e) {
                System.out.println("-------------Error reading image from buffer");
                e.printStackTrace();
            }
        }

        imageProcessor.stop();
    }

    private ImageProcessor startProcessingThread(final ByteArrayRingBuffer ringBuffer) {
        final ImageProcessor imageProcessor = new ImageProcessor(ringBuffer, gestureBuffer);
        new Thread(imageProcessor).start();
        return imageProcessor;
    }

    public RingBuffer<Gesture> getGestureBuffer() {
        return gestureBuffer;
    }

    public Socket getSocket() {
        return this.socket;
    }

}
