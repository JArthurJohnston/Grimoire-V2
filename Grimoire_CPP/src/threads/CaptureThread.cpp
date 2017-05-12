using namespace std;

class CaptureThread {
    VideoCapture capture;
    bool isRunning;

    void start(int cameraIndex) {
        capture = new VideoCapture();
        capture.open(cameraIndex);
        isRunning = true;
        while(isRunning){
            
        }
    }

    void stop();
};
