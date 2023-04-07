package filters;

import lombok.Getter;
import lombok.Setter;
import view.windows.ScalerFilterWindow;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import static java.awt.image.AffineTransformOp.*;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Math.min;

@Getter
@Setter
public class ScaleFilter extends AbstractParametredFilter {
    private final ScalerFilterWindow filterWindow = new ScalerFilterWindow(this);
    private int transformType = TYPE_BILINEAR;
    protected boolean fitToScreen = true;

    public BufferedImage scale(BufferedImage image, int width, int height) {
        if (image == null) {
            return null;
        }
        double scaleWidth, scaleHeight, scale;
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();

        resultImage = new BufferedImage(imageWidth, imageHeight, TYPE_INT_ARGB);
        if (fitToScreen) {
            if (imageWidth > width || imageHeight > height) {
                AffineTransform affineTransform = new AffineTransform();

                scaleWidth = (imageWidth > width) ? (double)width / imageWidth : 1;
                scaleHeight = (imageHeight > height) ? (double)height / imageHeight : 1;

                scale = min(scaleWidth, scaleHeight);
                affineTransform.scale(scale, scale);

                AffineTransformOp scaleTransform = new AffineTransformOp(affineTransform, transformType);
                resultImage = scaleTransform.filter(image, new BufferedImage((int)(imageWidth * scale),
                        (int)(imageHeight * scale), TYPE_INT_ARGB));
                return resultImage;
            }
            resultImage = new BufferedImage(width, height, TYPE_INT_ARGB);
        }
        resultImage.getGraphics().drawImage(image,0,0,null);
        return resultImage;
    }

    public void setTransformType(TransformType transformType) {
        switch (transformType) {
            case BICUBIC -> this.transformType = TYPE_BICUBIC;
            case BILINEAR -> this.transformType = TYPE_BILINEAR;
            case NEAREST_NEIGHBOR -> this.transformType = TYPE_NEAREST_NEIGHBOR;
        }
    }

    public enum TransformType {NEAREST_NEIGHBOR, BILINEAR, BICUBIC}
}