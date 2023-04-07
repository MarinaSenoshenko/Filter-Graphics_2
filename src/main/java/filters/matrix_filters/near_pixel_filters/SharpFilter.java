package filters.matrix_filters.near_pixel_filters;

import java.awt.image.BufferedImage;

public class SharpFilter extends AbstractNearPixelFilter {
    public SharpFilter() {
        setMatrix();
        k = 1;
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        return makeImageFiltration(image, 0);
    }

    @Override
    protected void setMatrix() {
        matrix = new int[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0},};
    }
}
