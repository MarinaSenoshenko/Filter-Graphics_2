package filters;

import java.awt.image.BufferedImage;

public class NothingFilter extends AbstractParametredFilter {
    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        return image;
    }
}
