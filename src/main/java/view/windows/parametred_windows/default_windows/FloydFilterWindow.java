package view.windows.parametred_windows.default_windows;

import filters.dither_filters.FloydDitherFilter;

public class FloydFilterWindow extends AbstractDefaultFilterWindow {
    public FloydFilterWindow(FloydDitherFilter floydDitherFilter) {
        colorParameterWindow(floydDitherFilter);
    }
}
