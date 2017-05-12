package grimoire.threads;

import grimoire.Grimoire;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCommunicator {

    private BlockingQueue<ProcessedFrameData> processedFrameData;
    private boolean viewIsRunning;

    public ThreadCommunicator(){
        processedFrameData = new ArrayBlockingQueue<>(Grimoire.UserSettings.BUFFER_SIZE);
        viewIsRunning = false;
    }

    public void addData(ProcessedFrameData data) throws InterruptedException {
        if(viewIsRunning){
            processedFrameData.put(data);
        }
    }

    public ProcessedFrameData takeData() throws InterruptedException {
        return processedFrameData.take();
    }

    public void viewStarted(){
        viewIsRunning = true;
        processedFrameData.clear();
    }

    public void viewStopped(){
        viewIsRunning = false;
    }

}
