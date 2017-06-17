package com.paratussoftware.errors;

import com.paratussoftware.Grimoire;
import org.junit.*;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.*;

public class ExceptionLoggerTest {

    private String testLogFilename = "./test.log";

    @BeforeClass
    public void setUp() throws Exception {
        File file = new File(testLogFilename);
        file.delete();
        file.createNewFile();
    }

    @AfterClass
    public void tearDown() throws Exception {
        File file = new File(testLogFilename);
        file.delete();
    }

    @Test
    public void testWritesStacktraceToExceptionsFile() throws Exception{
        assertEquals("./lib/exceptions.log", Grimoire.UserSettings.ERROR_LOG_LOCATION);

        Grimoire.UserSettings.ERROR_LOG_LOCATION = testLogFilename;

        Exception expectedException = new Exception("something went wrong");
        String expectedDateString = new Date().toString();
        String expectedStackTrace = getStacktraceAsString(expectedException);

        ExceptionLogger.scribble(expectedException);

        String actualLoggedError = readContentFromLog(testLogFilename);

        String expectedLog = expectedDateString + "\n\r" + expectedStackTrace + "\n\r";

        assertEquals(expectedLog, actualLoggedError);
    }

    private String getStacktraceAsString(Exception expectedException) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        expectedException.printStackTrace(new PrintStream(outputStream));
        return new String(outputStream.toByteArray());
    }

    private String readContentFromLog(String filename){
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            return "";
        }
    }

}