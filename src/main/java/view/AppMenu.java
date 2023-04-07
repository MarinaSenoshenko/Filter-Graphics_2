package view;

import javax.swing.*;

public class AppMenu extends JFrame {
    protected JMenu fileMenu = new JMenu("File"), modesMenu = new JMenu("Modes"), filterMenu = new JMenu("Filter"), helpMenu = new JMenu("Help");

    protected String[] fileOpt = {"Open", "Save", "Apply", "Exit"}, helpOpt = {"About"}, modesOpt = {"FitSettings"},
            filterOpt = {"Sharp", "Blur", "Emboss", "Aquarelle", "Inversion", "Grey", "Gamma", "Roberts",
                    "Sobel", "FloydDither", "BuildUp", "OrderedDither", "FitToScreen", "Rotate", "Sepia"};
    protected JRadioButtonMenuItem menuBlur, menuRoberts, menuInversion, menuSobel, menuEmboss, menuGamma, menuOrdered,
            menuAqua, menuFloyd, menuGrey, menuBuildUp, menuSharp, menuRotate, fitToScreenMenuItem, menuSepia;
    protected final JToggleButton sharp = new JToggleButton(new ImageIcon("src/main/resources/sharp.png"));
    protected final JToggleButton blur = new JToggleButton(new ImageIcon("src/main/resources/blur.png"));
    protected final JToggleButton emboss = new JToggleButton(new ImageIcon("src/main/resources/emboss.png"));
    protected final JToggleButton aqua = new JToggleButton(new ImageIcon("src/main/resources/aquarelle.png"));
    protected final JToggleButton inversion = new JToggleButton(new ImageIcon("src/main/resources/invert.png"));
    protected final JToggleButton grey = new JToggleButton((new ImageIcon("src/main/resources/grey.png")));
    protected final JToggleButton gamma = new JToggleButton((new ImageIcon("src/main/resources/gamma.png")));
    protected final JToggleButton roberts = new JToggleButton(new ImageIcon("src/main/resources/roberts.png"));
    protected final JToggleButton sobel = new JToggleButton(new ImageIcon("src/main/resources/sobel.png"));
    protected final JToggleButton floyd = new JToggleButton(new ImageIcon("src/main/resources/floyd.png"));
    protected final JToggleButton buildUp = new JToggleButton(new ImageIcon("src/main/resources/buildup.png"));
    protected final JToggleButton ordered = new JToggleButton(new ImageIcon("src/main/resources/ordered.png"));
    protected final JToggleButton sepia = new JToggleButton(new ImageIcon("src/main/resources/brown.png"));
    protected final JToggleButton rotate = new JToggleButton(new ImageIcon("src/main/resources/rotate.png"));
    protected final JToggleButton fitToScreen = new JToggleButton(new ImageIcon("src/main/resources/resize.png"));
}
