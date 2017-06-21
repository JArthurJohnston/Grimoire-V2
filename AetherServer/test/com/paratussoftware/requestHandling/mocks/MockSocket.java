package com.paratussoftware.requestHandling.mocks;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MockSocket extends Socket{
    public boolean isClosedToReturn = false;
    public DataInputStream dataInputStreamToReturn = new DataInputStream(null);


    @Override
    public boolean isClosed(){
        return isClosedToReturn;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return dataInputStreamToReturn;
    }
}
