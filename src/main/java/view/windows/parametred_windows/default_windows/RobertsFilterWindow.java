package view.windows.parametred_windows.default_windows;

import filters.matrix_filters.near_pixel_filters.grey_filters.RobertsFilter;

public class RobertsFilterWindow extends AbstractDefaultFilterWindow {
    public RobertsFilterWindow(RobertsFilter robertsFilter) {
        sliderTextWindow(robertsFilter, "Binarization:", 10, 255, 3);
    }
}
