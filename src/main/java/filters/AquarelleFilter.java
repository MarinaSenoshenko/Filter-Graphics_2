package filters;

import filters.matrix_filters.near_pixel_filters.SharpFilter;
import view.windows.AquarelleFilterWindow;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static colors.ColorsConstants.*;

@Setter
@Getter
public class AquarelleFilter extends AbstractParametredFilter {
    private int index = 0;
    private AquarelleFilterWindow filterWindow = new AquarelleFilterWindow();

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int sumA, len = 25;

        redArr = new int[len];
        greenArr = new int[len];
        blueArr = new int[len];

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                sumA = (currentPixel & alpha) >> 24;

                for (int i = -2; i <= 2; i++) {
                    if (x + i >= 0 & x + i < imageWidth) {
                        for (int j = -2; j <= 2; j++) {
                            if (y + j >= 0 & y + j < imageHeight) {
                                nearPixel = image.getRGB(x + i, y + j);
                                redArr[index] = (nearPixel & colorRed) >> 16;
                                greenArr[index] = (nearPixel & colorGreen) >> 8;
                                blueArr[index] = (nearPixel & colorBlue);
                                index++;
                            }
                        }
                    }
                }
                Arrays.sort(redArr, 0, index);
                Arrays.sort(greenArr, 0, index);
                Arrays.sort(blueArr, 0, index);

                index = index / 2;

                resultPixel = (blueArr[index]) | ((greenArr[index]) << 8) | ((redArr[index]) << 16) | (sumA << 24);
                resultImage.setRGB(x, y, resultPixel);
                index = 0;
            }
        }
        return new SharpFilter().filterToImage(resultImage);
    }
}