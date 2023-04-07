package view;

import filters.dither_filters.*;
import filters.matrix_filters.*;
import filters.matrix_filters.near_pixel_filters.*;
import filters.matrix_filters.near_pixel_filters.grey_filters.*;
import lombok.*;
import view.windows.FilterWindow;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import filters.*;

import static java.awt.BasicStroke.*;
import static java.awt.Cursor.*;
import static javax.swing.JOptionPane.*;
import static view.Panel.CurrentFilter.NONE;

public class Panel extends JPanel {
    private final int IMAGE_OFFSET = 4;
    private BufferedImage imageToPaint, refactoredImage, originalImage;
    private Filter filter;
    public static CurrentFilter currentFilter;
    private int minWidth = 640, minHeight = 480;
    private boolean isInEffect = false;
    protected Logger logger;
    private static final FiltersCollection filtersCollection = new FiltersCollection(
            new GammaFilter(), new GreyFilter(), new ScaleFilter(), new InversionFilter(),
            new BlurFilter(), new SharpFilter(), new EmbossFilter(), new OrderedDitherFilter(),
            new AquarelleFilter(), new FloydDitherFilter(), new SobelFilter(), new RobertsFilter(),
            new RotateFilter(), new NothingFilter(), new BuildUpFilter(), new SepiaFilter());

    public Panel(Logger logger) {
        this.logger = logger;
        filter = null;
        currentFilter = NONE;
        refactoredImage = null;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onMouseClick();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setCursor(getPredefinedCursor(WAIT_CURSOR));
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(1, CAP_BUTT, JOIN_ROUND, 1.0f, new float[]{2f, 0f, 2f}, 2f));

        if (isInEffect) {
            imageToPaint = filtersCollection.scalerFilter().scale(refactoredImage, minWidth, minHeight);
            this.setCursor(getPredefinedCursor(DEFAULT_CURSOR));
        }
        else {
            imageToPaint = filtersCollection.scalerFilter().scale(originalImage, minWidth, minHeight);
            this.setCursor(getPredefinedCursor(DEFAULT_CURSOR));
        }
        if (imageToPaint != null) {
            g2d.drawRect(IMAGE_OFFSET, IMAGE_OFFSET, imageToPaint.getWidth() + 1, imageToPaint.getHeight() + 1);
            g.drawImage(imageToPaint, IMAGE_OFFSET + 1, IMAGE_OFFSET + 1, null);
        }
        else {
            g2d.drawRect(IMAGE_OFFSET, IMAGE_OFFSET, getWidth() - 2 * IMAGE_OFFSET, getHeight() - 2 * IMAGE_OFFSET);
        }
    }

    private void openError() {
        JOptionPane.showMessageDialog(this, "Open error", "Error", ERROR_MESSAGE);
    }

    public void open(File file) {
        BufferedImage newImage;
        if (file == null) {
            return;
        }
        try {
            newImage = ImageIO.read(file);
        } catch (IOException e) {
            openError();
            return;
        }
        if (newImage == null) {
            openError();
            return;
        }
        isInEffect = false;
        originalImage = newImage;
        setImage();
    }

    public void setImage() {
        minWidth = getWidth() - 2 * IMAGE_OFFSET - 2;
        minHeight = getHeight() - 2 * IMAGE_OFFSET - 2;
        paintImage();
        if (!filtersCollection.scalerFilter().isFitToScreen() && originalImage != null) {
            if (!isInEffect) {
                setPreferredSize(new Dimension(originalImage.getWidth() + 2 * IMAGE_OFFSET + 1,
                        originalImage.getHeight() + 2 * IMAGE_OFFSET + 1));
            }
            else {
                setPreferredSize(new Dimension(refactoredImage.getWidth() + 2 * IMAGE_OFFSET + 1,
                        refactoredImage.getHeight() + 2 * IMAGE_OFFSET + 1));
            }
        }
    }

    private void paintImage() {
        if (isInEffect) {
            imageToPaint = filtersCollection.scalerFilter().scale(refactoredImage,
                    refactoredImage.getWidth(), refactoredImage.getHeight());
        }
        else {
            if (originalImage == null) {
                return;
            }
            imageToPaint = filtersCollection.scalerFilter().scale(originalImage, minWidth, minHeight);
        }
        repaint();
    }

    private void onMouseClick() {
        if (!isInEffect) {
            if (refactoredImage == null) {
                if (filter != null && originalImage != null) {
                    refactoredImage = filter.filterToImage(originalImage);
                }
            }
        }
        isInEffect = !isInEffect;
        paintImage();
    }

    public void useFilter() {
        if (filter != null && originalImage != null) {
            this.setCursor(getPredefinedCursor(WAIT_CURSOR));
            long begin = System.currentTimeMillis();
            refactoredImage = filter.filterToImage(originalImage);
            long time = System.currentTimeMillis() - begin;
            logger.log(Level.INFO, currentFilter.toString() + " "+ time + " milliseconds");
            isInEffect = true;
        }
        paintImage();
        this.setCursor(getPredefinedCursor(DEFAULT_CURSOR));
    }

    public void setFilter(CurrentFilter cur_filter) {
        isInEffect = false;
        refactoredImage = null;
        Panel.currentFilter = cur_filter;
        filter = cur_filter.getFilter();
    }

    public CurrentFilter getFilter() {
        return currentFilter;
    }

    public FilterWindow getParametersPanel() {
        return filter.getFilterWindow();
    }

    public void setImageSize() {
        if (filtersCollection.scalerFilter().isFitToScreen() && originalImage != null) {
            this.minWidth = getWidth() - 2 * IMAGE_OFFSET - 2;
            this.minHeight = getHeight() - 2 * IMAGE_OFFSET - 2;
            paintImage();
        }
    }

    public void setFitToScreen(boolean isFitToScreenMode) {
        filtersCollection.scalerFilter().setFitToScreen(isFitToScreenMode);
        setImage();
    }

    public void showFitDialog() {
        JOptionPane.showConfirmDialog(null, filtersCollection.scalerFilter().getFilterWindow(),
                "Choose the parameters", DEFAULT_OPTION);
    }

    public void applyChanges() {
        if (isInEffect & refactoredImage != originalImage) {
            originalImage = refactoredImage;
        }
        isInEffect = false;
    }

    public void save(File image) throws IOException {
        if (isInEffect) {
            ImageIO.write(refactoredImage, "png" , image);
        }
        else if (originalImage != null) {
            ImageIO.write(originalImage, "png" , image);
        }
        else {
            JOptionPane.showMessageDialog(this, "no image", "error", ERROR_MESSAGE);
        }
    }

    @AllArgsConstructor
    @Getter
    protected enum CurrentFilter {
        AQUARELLE(filtersCollection.aquarelleFilter()), BLUR(filtersCollection.blurFilter()),
        EMBOSS(filtersCollection.embossFilter()), FLOYD(filtersCollection.floydDitherFilter()),
        GAMMA(filtersCollection.gammaFilter()), GREY(filtersCollection.greyFilter()),
        INVERSION(filtersCollection.inversionFilter()), ORDERED(filtersCollection.orderedDitherFilter()),
        ROBERTS(filtersCollection.robertsFilter()), ROTATE(filtersCollection.rotateFilter()),
        SHARP(filtersCollection.sharpFilter()), SOBEL(filtersCollection.sobelFilter()),
        NONE(filtersCollection.nothingFilter()), BUILDUP(filtersCollection.buildUpFilter()),
        SEPIA(filtersCollection.sepiaFilter());
        final Filter filter;
    }

    private record FiltersCollection(GammaFilter gammaFilter, GreyFilter greyFilter, ScaleFilter scalerFilter,
                                    InversionFilter inversionFilter, BlurFilter blurFilter, SharpFilter sharpFilter,
                                    EmbossFilter embossFilter, OrderedDitherFilter orderedDitherFilter,
                                    AquarelleFilter aquarelleFilter, FloydDitherFilter floydDitherFilter,
                                    SobelFilter sobelFilter, RobertsFilter robertsFilter, RotateFilter rotateFilter,
                                    NothingFilter nothingFilter, BuildUpFilter buildUpFilter, SepiaFilter sepiaFilter){}
}
