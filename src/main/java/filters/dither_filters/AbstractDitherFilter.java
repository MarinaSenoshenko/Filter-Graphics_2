package filters.dither_filters;

import filters.matrix_filters.AbstractMatrixFilter;

public abstract class AbstractDitherFilter extends AbstractMatrixFilter {
    public void setRed(int red) {
        this.red = red;
    }
    public void setGreen(int green) {
        this.green = green;
    }
    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    protected void setMatrix() {}
}
