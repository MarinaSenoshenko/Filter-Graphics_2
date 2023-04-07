package filters;

import lombok.*;
import view.windows.parametred_windows.default_windows.RotateFilterWindow;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

@Setter
@Getter
public class RotateFilter extends AbstractParametredFilter {
    private RotateFilterWindow filterWindow = new RotateFilterWindow(this);

    @Override
    public BufferedImage filterToImage(BufferedImage image) {
        double radians = (180 - angle) * PI / 180;
        double cos = cos(radians), sin = sin(radians), oldX, oldY;
        int oldCenterX = image.getWidth() / 2, oldCenterY = image.getHeight() / 2;

        radians = (angle <= 45) ? angle * PI / 180 : radians;

        resultImage = getResultImage(image, radians, oldCenterX, oldCenterY);

        int imageHeight = resultImage.getHeight(), imageWidth = resultImage.getWidth();
        int newCenterX = imageWidth / 2, newCenterY = imageHeight / 2;

        Graphics2D g2d = (Graphics2D)resultImage.getGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Float(0,0, imageWidth, imageHeight));
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                oldX = x - newCenterX;
                oldY = y - newCenterY;
                int X = (int)(oldX * cos - oldY * sin);
                int Y = (int)(oldX * sin + oldY * cos);
                if (X <= -oldCenterX || X >= oldCenterX || Y <= -oldCenterY || Y >= oldCenterY) {
                    continue;
                }
                resultImage.setRGB(x, y, image.getRGB(oldCenterX - X, oldCenterY - Y));
            }
        }
        return resultImage;
    }

    private BufferedImage getResultImage(BufferedImage image, double radians, int x, int y) {
        double cos = cos(radians), sin = sin(radians);
        int newWidth = (int)(2 * max(abs(-cos * x + sin * y), abs(-cos * x - sin * y))) + 1;
        int newHeight = (int)(2 * max(abs(-sin * x + cos * y), abs(-sin * x - cos * y))) + 1;
        return new BufferedImage(newWidth, newHeight, image.getType());
    }
}
