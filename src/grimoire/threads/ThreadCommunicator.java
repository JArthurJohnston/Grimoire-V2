package grimoire.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadCommunicator {

    private BlockingQueue<ProcessedFrameData> processedFrameData;
    private boolean viewIsRunning;

    public ThreadCommunicator(){
        processedFrameData = new LinkedBlockingQueue<>();
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
