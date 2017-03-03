package grimoire.image_analysis;

public class ImageAnalysisHelper {

    public static double pixelIntensity(double[] rgbValues){
        return rgbValues.length == 1 ? grayscaleBrightness(rgbValues) : colorBrightness(rgbValues);
    }

    /**
     *
     * @param rgbValues 0 = blue 1 = green 2 = red
     * @return brightness percentage
     */
    private static double colorBrightness(double[] rgbValues){
        return (rgbValues[2] * 0.216 + rgbValues[1] * 0.7152 + rgbValues[0] * 0.0722) / 255.0;

    }

    private static double grayscaleBrightness(double[] pixelValues){
        return pixelValues[0] / 255.0;
    }
}
