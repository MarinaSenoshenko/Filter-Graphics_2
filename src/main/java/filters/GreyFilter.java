package filters;

import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.round;

public class GreyFilter extends AbstractParametredFilter {
    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int resultGrey;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                resultGrey = (int)round(0.299 * ((currentPixel & colorRed) >> 16) +
                        0.587 * ((currentPixel & colorGreen) >> 8) + 0.114 * (currentPixel & colorBlue));
                resultPixel = resultGrey | (resultGrey << 8) | (resultGrey << 16) | (currentPixel & alpha);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }
}
