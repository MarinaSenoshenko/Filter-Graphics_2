package filters.dither_filters;

import filters.matrix_filters.AbstractMatrixFilter;
import lombok.Getter;
import lombok.Setter;
import view.windows.parametred_windows.default_windows.OrderedDitherFilterWindow;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.*;

@Getter
@Setter
public class OrderedDitherFilter extends AbstractMatrixFilter {
    private int matrixRank = 0, rank;
    private OrderedDitherFilterWindow filterWindow = new OrderedDitherFilterWindow(this);

    public OrderedDitherFilter() {
        k = 0;
        checkMatrix();
    }

    private int dither(int bright, int x, int y, int quantumSize, int len) {
        int resultBright = round(round((bright + quantumSize *
                ((double)matrix[y % len][x % len] / k - 0.5)) / quantumSize) * quantumSize);
        return max(0, min(resultBright, 255));
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int sumA, sumR, sumG, sumB, len = matrix.length;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                sumA = (currentPixel & alpha) >> 24;
                sumR = dither((currentPixel & colorRed) >> 16, x, y, 255 / red, len);
                sumG = dither((currentPixel & colorGreen) >> 8, x, y, 255 / green, len);
                sumB = dither(currentPixel & colorBlue, x, y, 255 / blue, len);
                resultPixel = sumB | (sumG << 8) | (sumR << 16) | (sumA << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }

    private void checkMatrix() {
        this.rank = (int)floor(sqrt((double)255 / min(min(red, green), blue)));
        if (matrix == null || matrix.length != rank) {
            setMatrix();
        }
        k = matrix.length * matrix.length;
    }

    private void generatePart(int[][] oldMatrix, int numberOfPart) {
        int x, y, len = oldMatrix.length;
        try {
            switch (numberOfPart) {
                case 0 -> x = y = 0;
                case 1 -> x = y = 1;
                case 2 -> {
                    x = 1;
                    y = 0;
                }
                case 3 -> {
                    x = 0;
                    y = 1;
                }
                default -> throw new IllegalArgumentException();
            }
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    matrix[i + y * len][j + x * len] = 4 * oldMatrix[i][j] + numberOfPart;
                }
            }
        } catch (IllegalArgumentException ignored) {}
    }

    @Override
    protected void setMatrix() {
        rank = min(rank, 5);
        if (rank == 1) {
            matrix = new int[][]{{0, 2}, {3, 1}};
            matrixRank = rank;
            return;
        }
        if (matrixRank == rank) {
            return;
        }
        rank -= 1;
        setMatrix();
        int[][] oldMatrix = matrix;
        int oldMatrixLen = oldMatrix.length;
        matrix = new int[oldMatrixLen * 2][oldMatrixLen * 2];
        for (int i = 0; i < 4; i++) {
            generatePart(oldMatrix, i);
        }
        matrixRank = rank;
    }

    @Override
    public void setRed(int red) {
        super.setRed(red);
        checkMatrix();
    }

    @Override
    public void setGreen(int green) {
        super.setGreen(green);
        checkMatrix();
    }

    @Override
    public void setBlue(int blue) {
        super.setBlue(blue);
        checkMatrix();
    }
}
