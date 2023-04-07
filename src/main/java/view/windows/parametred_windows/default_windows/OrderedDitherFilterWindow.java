package view.windows.parametred_windows.default_windows;

import filters.dither_filters.OrderedDitherFilter;

public class OrderedDitherFilterWindow extends AbstractDefaultFilterWindow {

    public OrderedDitherFilterWindow(OrderedDitherFilter orderedDitherFilter) {
        colorParameterWindow(orderedDitherFilter);
    }
}
