package filters;

import lombok.Getter;
import lombok.Setter;
import view.windows.FilterWindow;

import java.awt.image.BufferedImage;

@Getter
@Setter
public abstract class AbstractParametredFilter implements Filter {
    protected int red = 2, green = 2, blue = 2;
    protected int binParameter = 10;
    protected int imageHeight, imageWidth;
    protected int angle = 10;
    protected int[] redArr, greenArr, blueArr;
    protected int currentPixel, nearPixel;
    protected static int resultPixel;
    protected FilterWindow filterWindow = null;
    protected BufferedImage resultImage;
    protected boolean fitToScreen = true;
    protected double param = 1.5;

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        return null;
    }
}
