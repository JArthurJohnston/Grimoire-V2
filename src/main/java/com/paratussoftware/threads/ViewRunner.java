package com.paratussoftware.threads;

import com.paratussoftware.ui.views.CameraUI;

public class ViewRunner implements Runnable{

    public boolean isRunning;
    private ThreadCommunicator communicator;

    public ViewRunner(ThreadCommunicator communicator){
        this.communicator = communicator;
        isRunning = false;
    }

    @Override
    public void run() {
        communicator.viewStarted();
        CameraUI view = new CameraUI();
        isRunning = true;
        view.setVisible(true);
        while (isRunning){
            try {
                ProcessedFrameData processedData = this.communicator.takeData();
                view.drawFrame(processedData.frame, processedData.motions);
            } catch (InterruptedException e) {
            }
        }
        communicator.viewStopped();
        view.setVisible(false);
        view.dispose();
    }

    public void stop(){
        communicator.viewStopped();
        isRunning = false;
    }
}
