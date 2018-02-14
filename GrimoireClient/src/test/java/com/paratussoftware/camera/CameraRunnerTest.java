package com.paratussoftware.camera;

import com.paratussoftware.TestHelper;
import com.paratussoftware.data.RingBuffer;
import com.paratussoftware.mocks.MockCapture;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.junit.Assert.*;

public class CameraRunnerTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test
    public void testConstruction() throws Exception {
        final RingBuffer<Mat> imageBuffer = new RingBuffer<>(32);
        final int cameraIndex = 34;

        final CameraRunner cameraRunner = new CameraRunner(cameraIndex, imageBuffer);

        assertEquals(cameraIndex, cameraRunner.getCameraIndex());
        assertSame(imageBuffer, cameraRunner.getImageBuffer());
        assertFalse(cameraRunner.isRunning());
    }

    @Test
    public void testRun() throws Exception {
        final RingBuffer<Mat> imageBuffer = new RingBuffer<>(32);
        final int cameraIndex = 34;
        final MockCapture mockCapture = new MockCapture();
        final CameraRunner cameraRunner = new CameraRunner(cameraIndex, imageBuffer);
        TestHelper.setPrivateField(cameraRunner, "capture", mockCapture);

        final Thread thread = new Thread(cameraRunner);
        thread.start();

        TestHelper.setPrivateField(cameraRunner, "running", false);
        thread.join(100);

        assertEquals(cameraIndex, mockCapture.deviceIndexPassedIn);
        assertNotNull(mockCapture.framePassedToRead);
        assertTrue(imageBuffer.size() > 0);
    }
}