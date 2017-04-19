package grimoire.threads;

import grimoire.ui.views.CameraUI;
import grimoire.ui.views.GrimoireViewInterface;

import java.util.concurrent.BlockingQueue;

public class ViewRunner implements Runnable{

    private CameraUI view;
    private BlockingQueue<ProcessedFrameData> processedDataQueue;
    public boolean isRunning;

    public ViewRunner(BlockingQueue<ProcessedFrameData> processedDataQueue){
        isRunning = false;
        this.processedDataQueue = processedDataQueue;
    }

    @Override
    public void run() {
        this.processedDataQueue.clear();
        this.view = new CameraUI();
        isRunning = true;
        this.view.setVisible(true);
        while (isRunning && view != null){
            try {
                ProcessedFrameData processedData = this.processedDataQueue.take();
                this.view.drawFrame(processedData.frame, processedData.motions);
            } catch (InterruptedException e) {}
        }
    }

    public void stop(){
        isRunning = false;
        this.view.setVisible(false);
        this.view.dispose();
        this.view = null;
    }
}
