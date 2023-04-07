package filters.matrix_filters.near_pixel_filters.grey_filters;

import view.windows.parametred_windows.default_windows.SobelFilterWindow;
import lombok.*;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.*;
import static java.lang.Math.abs;

@Getter
@Setter
public class SobelFilter extends AbstractBorderFilter {
    private final SobelFilterWindow filterWindow = new SobelFilterWindow(this);

    public SobelFilter() {
        setMatrix();
        k = 4;
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        boolean isPixelFilled;
        int shift = matrix.length / 2, iShift, jShift;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                mA = (currentPixel & alpha) >> 24;
                curA = curB = 0;
                for (int i = -shift; i <= shift; i++) {
                    for (int j = -shift; j <= shift; j++) {
                        isPixelFilled = false;
                        if (x + i >= 0 & x + i < imageWidth & y + j >= 0 & y + j < imageHeight) {
                            nearPixel = image.getRGB(x + i, y + j);
                            isPixelFilled = true;
                        }
                        else if ((x + i < 0 && y + j < 0) || (x + i < 0 && y + j > imageHeight) ||
                                (x + i >= imageWidth && y + j >= imageHeight) ||
                                (x + i >= imageWidth && y + j < 0)) {
                            nearPixel = image.getRGB(x - i, y - j);
                            isPixelFilled = true;
                        }
                        else if ((x + i < 0 && y + j >= 0 && y + j < imageHeight) ||
                                (x + i <= imageWidth && y + j >= 0 && y + j < imageHeight)) {
                            nearPixel = image.getRGB(x - i, y + j);
                            isPixelFilled = true;
                        }
                        else if ((y + j < 0 && x + i >= 0 && x + i < imageWidth) ||
                                (y + j >= imageHeight && x + i >= 0 && x + i < imageWidth)) {
                            nearPixel = image.getRGB(x + i, y - j);
                            isPixelFilled = true;
                        }
                        iShift = i + shift;
                        jShift = j + shift;
                        if (isPixelFilled) {
                            sumR += ((nearPixel & colorRed) >> 16) * matrix[iShift][jShift];
                            sumG += ((nearPixel & colorGreen) >> 8) * matrix[iShift][jShift];
                            sumB += (nearPixel & colorBlue) * matrix[iShift][jShift];
                        }
                        bright = (int)round(0.299 * ((nearPixel & colorRed) >> 16) +
                                    0.587 * ((nearPixel & colorGreen) >> 8) + 0.114 * (nearPixel & colorBlue));
                        curA += bright * matrix[iShift][jShift];
                        curB += bright * matrix[jShift][iShift];
                    }
                }
                resultGrey = (min((abs(curA) + abs(curB)) / (2 * k), 255) > binParameter) ? 255 : 0;
                resultPixel = resultGrey | (resultGrey << 8) | (resultGrey << 16) | (mA << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }

    @Override
    protected void setMatrix() {
        matrix = new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1},};
    }
}
