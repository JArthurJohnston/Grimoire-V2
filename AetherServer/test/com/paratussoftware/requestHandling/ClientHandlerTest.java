package com.paratussoftware.requestHandling;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.ImageProcessor;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void testConstruction() throws Exception{
        Socket mockSocket = new Socket();

        ClientHandler clientHandler = new ClientHandler(mockSocket);

        assertSame(mockSocket, clientHandler.getSocket());
    }
}