package filters.matrix_filters.near_pixel_filters;

import filters.matrix_filters.AbstractMatrixFilter;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;

public abstract class AbstractNearPixelFilter extends AbstractMatrixFilter {
    protected static int sumA, sumB, sumR, sumG;

    protected BufferedImage makeImageFiltration(BufferedImage image, int colorOffset) {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int shift = matrix.length / 2, iShift, jShift;
        boolean isPixelFilled;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                sumR = sumG = sumB = colorOffset;
                sumA = (currentPixel & alpha) >> 24;
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
                        if (isPixelFilled) {
                            iShift = i + shift;
                            jShift = j + shift;
                            sumR += ((nearPixel & colorRed) >> 16) * matrix[iShift][jShift];
                            sumG += ((nearPixel & colorGreen) >> 8) * matrix[iShift][jShift];
                            sumB += (nearPixel & colorBlue) * matrix[iShift][jShift];
                        }
                    }
                }
                sumR = Math.min(sumR / k, 255);
                sumG = Math.min(sumG / k, 255);
                sumB = Math.min(sumB / k, 255);
                sumR = Math.max(sumR, 0);
                sumG = Math.max(sumG, 0);
                sumB = Math.max(sumB, 0);
                resultPixel = sumB | (sumG << 8) | (sumR << 16) | (sumA << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }
}
