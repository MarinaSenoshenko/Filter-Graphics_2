package filters.matrix_filters.near_pixel_filters.grey_filters;

import filters.matrix_filters.near_pixel_filters.AbstractNearPixelFilter;


public abstract class AbstractBorderFilter extends AbstractNearPixelFilter {
    protected int bright, mA, curA, curB, resultGrey;
}
