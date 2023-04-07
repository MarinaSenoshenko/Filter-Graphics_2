package view.windows.one_slider_windows;

import filters.SepiaFilter;
import lombok.Getter;

@Getter
public class SepiaFilterWindow extends AbstractOneSliderWindow {
    public SepiaFilterWindow(SepiaFilter sepiaFilter) {
        super(sepiaFilter, "intensity");
    }
}
