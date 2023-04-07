package filters.matrix_filters;

import lombok.Getter;
import view.windows.BuildUpFilterWindow;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static colors.ColorsConstants.*;

@Getter
public class BuildUpFilter extends AbstractMatrixFilter {
    private final BuildUpFilterWindow filterWindow = new BuildUpFilterWindow();

    public BuildUpFilter() {
        setMatrix();
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int sumA, shift = matrix.length / 2, index = 0, len = 13;

        redArr = new int[len];
        greenArr = new int[len];
        blueArr = new int[len];

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                sumA = (currentPixel & alpha) >> 24;

                for (int i = -shift; i <= shift; i++) {
                    for (int j = -shift; j <= shift; j++) {
                        if (x + i >= 0 & x + i < imageWidth & y + j >= 0 & y + j < imageHeight) {
                            if (matrix[i + shift][j + shift] == 1) {
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
                Arrays.sort(greenArr,0, index);
                Arrays.sort(blueArr, 0, index);
                index -= 1;

                resultPixel = (blueArr[index]) | (greenArr[index] << 8) | (redArr[index] << 16) | (sumA << 24);
                resultImage.setRGB(x, y, resultPixel);

                index = 0;
            }
        }
        return resultImage;
    }

    @Override
    protected void setMatrix() {
        matrix = new int[][]{{0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {0 ,0, 1, 0, 0},};
    }
}
