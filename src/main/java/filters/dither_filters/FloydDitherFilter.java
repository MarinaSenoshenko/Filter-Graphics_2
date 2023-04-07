package filters.dither_filters;

import lombok.Getter;
import view.windows.parametred_windows.default_windows.FloydFilterWindow;
import java.awt.image.BufferedImage;
import java.awt.*;

import static colors.ColorsConstants.*;
import static java.lang.Math.*;

@Getter
public class FloydDitherFilter extends AbstractDitherFilter {
    private final FloydFilterWindow filterWindow;

    public FloydDitherFilter() {
        red = green = blue = 2;
        this.filterWindow = new FloydFilterWindow(this);
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        double[] error = new double[3];
        int[] spreadSpace = new int[3], initRgb = new int[3],
                errorProp = new int[3], newRgb = new int[3];

        spreadSpace[0] = 255 / (red - 1);
        spreadSpace[1] = 255 / (green - 1);
        spreadSpace[2] = 255 / (blue - 1);

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                currentPixel = resultImage.getRGB(i, j);

                initRgb[0] = ((currentPixel & colorRed) >> 16);
                initRgb[1] = ((currentPixel & colorGreen) >> 8);
                initRgb[2] = currentPixel & colorBlue;

                setNearestColorAndFindError(initRgb, newRgb, spreadSpace, error, i, j);

                if (i + 1 < image.getWidth()) {
                    if (j + 1 < image.getHeight()) {
                        setErrorPropagationColor(error, errorProp, 1, i + 1, j + 1);
                    }
                    setErrorPropagationColor(error, errorProp,7, i + 1, j);
                }
                if (j + 1 < image.getHeight()) {
                    if (i - 1 >= 0) {
                        setErrorPropagationColor(error, errorProp, 3, i - 1, j + 1);
                    }
                    setErrorPropagationColor(error, errorProp,5, i, j + 1);
                }
            }
        }
        return resultImage;
    }

    private void setErrorPropagationColor(double[] error, int[] errorRgb, int koef, int x, int y) {
        int initPixel = resultImage.getRGB(x, y);

        errorRgb[0] = min(max((int)(((initPixel & colorRed) >> 16) + error[0] * koef / 16), 0), 255);
        errorRgb[1] = min(max((int)(((initPixel & colorGreen) >> 8) + error[1] * koef / 16), 0), 255);
        errorRgb[2] = min(max((int)((initPixel & colorBlue) + error[2] * koef / 16), 0), 255);

        resultPixel = (errorRgb[0] << 16) | (errorRgb[1] << 8) | errorRgb[2];
        resultImage.setRGB(x, y, resultPixel);
    }

    private void setNearestColorAndFindError(int[] initRgb, int[] newRgb, int[] spreadSpace, double[] error,
                                             int x, int y) {
        for (int i = 0; i < 3; i++) {
            newRgb[i] = min(max((int)(round((double)initRgb[i] / spreadSpace[i]) * spreadSpace[i]), 0), 255);
            error[i] = (double)initRgb[i] - newRgb[i];
        }
        resultPixel = (newRgb[0] << 16) | (newRgb[1] << 8) | newRgb[2];
        resultImage.setRGB(x, y, resultPixel);
    }
}