package filters;

import view.windows.FilterWindow;

import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage filterToImage(BufferedImage image);
    FilterWindow getFilterWindow();
}
