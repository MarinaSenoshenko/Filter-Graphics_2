package view.windows.parametred_windows.default_windows;

import filters.RotateFilter;

public class RotateFilterWindow extends AbstractDefaultFilterWindow {
    public RotateFilterWindow(RotateFilter rotateFilter) {
        sliderTextWindow(rotateFilter, "Turn:", -180, 180, 4);
    }
}
