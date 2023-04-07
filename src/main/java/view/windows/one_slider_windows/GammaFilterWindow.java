package view.windows.one_slider_windows;

import filters.GammaFilter;
import lombok.Getter;

@Getter
public class GammaFilterWindow extends AbstractOneSliderWindow {
    public GammaFilterWindow(GammaFilter gammaFilter) {
        super(gammaFilter, "gamma");
    }
}
