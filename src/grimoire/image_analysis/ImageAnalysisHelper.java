package grimoire.image_analysis;

public class ImageAnalysisHelper {
    private static final float RED_SENSITIVITY = 0.2126F;
    private static final float GREEN_SENSITIVITY = 0.7152F;
    private static final float BLUE_SENSITIVITY = 0.0722F;

    public static double pixelIntensity(double[] rgbValues){
        return rgbValues.length == 1 ? grayscaleBrightness(rgbValues) : colorBrightness(rgbValues);
    }

    private static double colorBrightness(double[] rgbValues){
        return (rgbValues[2] * 0.216 + rgbValues[1] * 0.7152 + rgbValues[0] * 0.0722) / 255.0;
    }

    public static float pixelIntensity(int rgbValue){
        int redValue = (rgbValue >>> 16) & 0xFF;
        int greenValue = (rgbValue >>> 8) & 0xFF;
        int blueValue = (rgbValue >>> 0) & 0xFF;
        return ((redValue * RED_SENSITIVITY +
                greenValue * GREEN_SENSITIVITY +
                blueValue * BLUE_SENSITIVITY) / 255);
    }

    private static double grayscaleBrightness(double[] pixelValues){
        return pixelValues[0] / 255.0;
    }
}
