package grimoire.models;

import org.opencv.core.Mat;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ImageHelper {

    public static BufferedImage matToBufferedImage(Mat frame){
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    public static double pixelIntensity(double[] rgbValues){
        return rgbValues.length == 1 ? grayscaleBrightness(rgbValues) : colorBrightness(rgbValues);
    }

    public static double colorBrightness(double[] rgbValues){
        return (rgbValues[2] * 0.216 + rgbValues[1] * 0.7152 + rgbValues[0] * 0.0722) / 255.0;

    }

    public static double grayscaleBrightness(double[] pixelValues){
        return pixelValues[0] / 255.0;
    }
}
