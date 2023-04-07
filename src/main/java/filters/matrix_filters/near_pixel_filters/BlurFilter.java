package filters.matrix_filters.near_pixel_filters;

import lombok.Getter;
import view.windows.parametred_windows.BlurFilterWindow;
import java.awt.image.BufferedImage;

@Getter
public class BlurFilter extends AbstractNearPixelFilter {
    private int matrixSize = 5;
    private final BlurFilterWindow filterWindow = new BlurFilterWindow(this);

    public BlurFilter() {
        setMatrix();
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        return makeImageFiltration(image, 0);
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
        setMatrix();
    }

    @Override
    public void setMatrix() {
        if (matrixSize == 3) {
            matrix = new int[][]{{1, 1, 1}, {1, 2, 1}, {1, 1, 1},};
            k = 10;
        }
        else if (matrixSize == 5) {
            matrix = new int[][]{{1, 2, 3, 2, 1}, {2, 4, 5, 4, 2}, {3, 5, 6, 5, 3}, {2, 4, 5, 4, 2}, {1, 2, 3, 2, 1},};
            k = 74;
        }
        else if (matrixSize % 2 == 1) {
            matrix = new int[matrixSize][matrixSize];
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    matrix[i][j] = 1;
                }
            }
            k = matrixSize * matrixSize;
        }
    }
}
