package filters;

import lombok.Getter;
import lombok.Setter;
import view.windows.one_slider_windows.SepiaFilterWindow;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.min;

@Setter
@Getter
public class SepiaFilter extends AbstractParametredFilter {
    private final SepiaFilterWindow filterWindow = new SepiaFilterWindow(this);

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int resultGrey, sumR, sumG, sumB, sumA;
        double depth = 20.0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                sumA = (currentPixel & alpha) >> 24;
                resultGrey = (((currentPixel & colorRed) >> 16) + ((currentPixel & colorGreen) >> 8) +
                        (currentPixel & colorBlue)) / 3;
                sumR = (int)min((resultGrey + 2 * depth) * param, 255);
                sumG = (int)min((resultGrey + depth) * param, 255);
                sumB = (int)min(resultGrey * param, 255);
                resultPixel = (sumA << 24) | (sumR << 16) | (sumG << 8) | sumB;
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }
}
