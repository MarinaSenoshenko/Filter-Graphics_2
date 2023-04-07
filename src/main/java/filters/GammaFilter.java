package filters;

import lombok.Getter;
import lombok.Setter;
import view.windows.one_slider_windows.GammaFilterWindow;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.*;

@Getter
@Setter
public class GammaFilter extends AbstractParametredFilter {
    private final GammaFilterWindow filterWindow = new GammaFilterWindow(this);

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int sumR, sumG, sumB, sumA;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                sumA = (currentPixel & alpha) >> 24;
                currentPixel = image.getRGB(x, y);
                sumR = (int)(255 * min(pow((double)((currentPixel & colorRed) >> 16) / 255, param), 1));
                sumG = (int)(255 * min(pow((double)((currentPixel & colorGreen) >> 8) / 255, param), 1));
                sumB = (int)(255 * min(pow((double)((currentPixel & colorBlue)) / 255, param), 1));
                resultPixel = sumB | (sumG << 8) | (sumR << 16) | (sumA << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }
}
