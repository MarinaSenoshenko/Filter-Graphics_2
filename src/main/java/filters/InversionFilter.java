package filters;

import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;

public class InversionFilter extends AbstractParametredFilter {
    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                resultPixel = (255 - (currentPixel & colorBlue)) | ((255 - ((currentPixel & colorGreen) >> 8)) << 8) |
                        ((255 - ((currentPixel & colorRed) >> 16)) << 16) | (((currentPixel & alpha) >> 24) << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }
}
