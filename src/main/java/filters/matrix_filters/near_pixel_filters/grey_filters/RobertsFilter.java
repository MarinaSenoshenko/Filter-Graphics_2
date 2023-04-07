package filters.matrix_filters.near_pixel_filters.grey_filters;

import lombok.*;
import view.windows.parametred_windows.default_windows.RobertsFilterWindow;
import java.awt.image.BufferedImage;

import static colors.ColorsConstants.*;
import static java.lang.Math.*;

@Getter
@Setter
public class RobertsFilter extends AbstractBorderFilter {
    private RobertsFilterWindow filterWindow = new RobertsFilterWindow(this);
    public RobertsFilter() {
        setMatrix();
        k = 1;
    }

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        resultImage = new BufferedImage(imageWidth, imageHeight, image.getType());
        int shift = 2;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                currentPixel = image.getRGB(x, y);
                mA = (currentPixel & alpha) >> 24;
                curA = curB = 0;
                for (int i = 0; i < shift; i++) {
                    for (int j = 0; j < shift; j++) {
                        if (x + i < imageWidth & y + j < imageHeight) {
                            nearPixel = image.getRGB(x + i, y + j);
                            bright = (int)round(0.299 * ((nearPixel & colorRed) >> 16) +
                                    0.587 * ((nearPixel & colorGreen) >> 8) + 0.114 * (nearPixel & colorBlue));
                        }
                        curA += bright * matrix[i][j];
                        curB += bright * matrix[j][i];
                    }
                }
                resultGrey = (min((abs(curA) + abs(curB)) / (2 * k), 255) > binParameter) ? 255 : 0;
                resultPixel = resultGrey | (resultGrey << 8) | (resultGrey << 16) | (mA << 24);
                resultImage.setRGB(x, y, resultPixel);
            }
        }
        return resultImage;
    }

    @Override
    protected void setMatrix() {
        matrix = new int[][]{{1, 0}, {0, -1},};
    }
}
