package grimoire.models.cameras;

import grimoire.views.CameraUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CircleDetectingDetector implements DetectorInterface{

    private final CameraUI view;
    private final CameraInterface camera;
    private boolean isRunning;
    Mat frameFromCamera, grayscaledFrame, circlesFrame;

    public CircleDetectingDetector(CameraUI view, CameraInterface camera){
        this.view = view;
        this.camera = camera;
        isRunning = false;
    }

    @Override
    public void start() {
        frameFromCamera = new Mat();
        circlesFrame = new Mat();
        grayscaledFrame = new Mat();
        isRunning = camera.open();
        while (isRunning){
            if(camera.read(frameFromCamera)){
                detectCircles(frameFromCamera);
                view.drawFrame(grayscaledFrame);
            }
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        camera.release();
    }

    private void detectCircles(Mat frame){
        CameraHelper.blurredAndGrayscaleFrame(frame, grayscaledFrame);
        Imgproc.threshold(grayscaledFrame, grayscaledFrame, 230, 255, Imgproc.THRESH_BINARY);
//        Imgproc.dilate(grayscaledFrame, grayscaledFrame, );
        Imgproc.equalizeHist(grayscaledFrame, grayscaledFrame);
        Imgproc.HoughCircles(grayscaledFrame, circlesFrame, Imgproc.CV_HOUGH_GRADIENT, 3.0, 50, 100, 100, 4, 15);
        drawCircles(frame);
    }

    private void drawCircles(Mat frame) {
        if(circlesFrame.cols() > 0){
            for (int x = 0; x < circlesFrame.cols(); x++)
            {
                double vCircle[] = circlesFrame.get(0,x);

                if (vCircle == null)
                    break;

                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
                int radius = (int)Math.round(vCircle[2]);

                // draw the found circle
                Core.circle(frame, pt, radius, new Scalar(0,255,0), 2);
                Core.circle(frame, pt, 3, new Scalar(0,0,255), 2);
            }
        }
    }
}
