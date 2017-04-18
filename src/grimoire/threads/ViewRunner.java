package grimoire.threads;

import grimoire.ui.views.CameraUI;
import grimoire.ui.views.GrimoireViewInterface;

import java.util.concurrent.BlockingQueue;

public class ViewRunner implements Runnable{

    private CameraUI view;
    private BlockingQueue<ProcessedFrameData> processedDataQueue;
    private boolean isRunning;

    public ViewRunner(CameraUI view, BlockingQueue<ProcessedFrameData> processedDataQueue){
        isRunning = false;
        this.view = view;
        this.processedDataQueue = processedDataQueue;
    }

    @Override
    public void run() {
        isRunning = true;
        this.view.setVisible(true);
        while (isRunning){
            try {
                ProcessedFrameData processedData = this.processedDataQueue.take();
                this.view.drawFrame(processedData.frame, processedData.motions);
            } catch (InterruptedException e) {}
        }
    }

    public void stop(){
        isRunning = false;
    }
}
