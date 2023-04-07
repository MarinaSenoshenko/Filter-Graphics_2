package filters.matrix_filters.near_pixel_filters;

import java.awt.image.BufferedImage;

public class EmbossFilter extends AbstractNearPixelFilter {
    public EmbossFilter() {
        setMatrix();
        k = 1;
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        return makeImageFiltration(image, 128);
    }

    @Override
    protected void setMatrix() {
        matrix = new int[][]{{0, 1, 0}, {1, 0, -1}, {0, -1, 0},};
    }
}
