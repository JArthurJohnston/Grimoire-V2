package com.paratussoftware.imageProcessing.clusters;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class TestFileReader {

    public static String TEST_RESOURCES_DIRCTORY = "./src/test/resources/";

    public static File openFile(String fileName){
        String testResourcesFileName = TEST_RESOURCES_DIRCTORY + fileName;
        File file = new File(testResourcesFileName);
        assertTrue("file: '" + testResourcesFileName + "' could not be found", file.exists());
        return file;
    }

    /**
     * The java equivalent of the pwd command. Usefull for debugging file IO problems.
     *
     * @return java's current working directory
     */
    public static String workingDirectory(){
        return Paths.get("").toAbsolutePath().toString();
    }
}
