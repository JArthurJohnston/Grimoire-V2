#include <stdio.h>
#include <thread>
#include <opencv2/opencv.hpp>
//#include "./threads/CaptureThread.cpp"
#include "./threads/ConcurrentBuffer.cpp"

using namespace cv;

int main(int argc, char** argv )
{
    ConcurrentBuffer buffer = new ConcurrentBuffer(8);
    if ( argc != 2 )
    {
        printf("usage: DisplayImage.out <Image_Path>\n");
        return -1;
    }

    Mat image;
    image = imread( argv[1], 1 );

    if ( !image.data )
    {
        printf("No image data \n");
        return -1;
    }

	cvtColor(image, image, cv::COLOR_BGR2GRAY);
	imwrite("./out2.jpg", image);

    //namedWindow("Display Image", WINDOW_AUTOSIZE );
    //imshow("Display Image", image);

    //waitKey(0);

    return 0;
}

void getImage(){
}
