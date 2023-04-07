package view.windows.parametred_windows.default_windows;

import filters.matrix_filters.near_pixel_filters.grey_filters.SobelFilter;

public class SobelFilterWindow extends AbstractDefaultFilterWindow {
    public SobelFilterWindow(SobelFilter sobelFilter) {
        sliderTextWindow(sobelFilter, "Binarization:", 10, 255, 3);
    }
}
