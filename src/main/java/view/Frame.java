package view;

import view.windows.FilterWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;

import static java.awt.BorderLayout.PAGE_START;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingUtilities.getAncestorOfClass;
import static view.Panel.CurrentFilter.*;

import view.Panel.CurrentFilter;


public class Frame extends AppMenu {
    private final JPanel panelWithScroll = new JPanel();
    private final Panel panel;
    private int lastX, lastY;
    private JScrollPane scrollPane;
    private boolean isFitToScreenMode = false;
    private final JMenuBar menuBar = new JMenuBar();
    private MouseAdapter mouseAdapter;
    private final ButtonGroup buttonGroup = new ButtonGroup(), menu = new ButtonGroup();
    private final String fileAbout;

    public Frame(String fileAbout, Logger logger) {
        setIconImage(new ImageIcon("src/main/resources/blur.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new Panel(logger);

        setJMenuBar(menuBar);
        JToolBar toolBar = new JToolBar("Main toolbar");
        toolBar.setRollover(true);
        add(toolBar, PAGE_START);
        setSize(1000, 1000);
        setLocationByPlatform(true);
        setTitle("Filter");

        this.fileAbout = fileAbout;

        addToJMenu(fileMenu, fileOpt);
        addToJMenu(modesMenu, modesOpt);
        addToJMenu(filterMenu, filterOpt);
        addToJMenu(helpMenu, helpOpt);

        sharp.addActionListener(e -> selectFilter(sharp, menuSharp, SHARP));
        blur.addActionListener(e -> selectFilter(blur, menuBlur, BLUR));
        emboss.addActionListener(e -> selectFilter(emboss, menuEmboss, EMBOSS));
        aqua.addActionListener(e -> selectFilter(aqua, menuAqua,AQUARELLE));
        inversion.addActionListener(e -> selectFilter(inversion, menuInversion, INVERSION));
        grey.addActionListener(e -> selectFilter(grey, menuGrey, GREY));
        gamma.addActionListener(e -> selectFilter(gamma, menuGamma, GAMMA));
        roberts.addActionListener(e  -> selectFilter(roberts, menuRoberts, ROBERTS));
        sobel.addActionListener(e -> selectFilter(sobel, menuSobel, SOBEL));
        floyd.addActionListener(e -> selectFilter(floyd, menuFloyd, FLOYD));
        buildUp.addActionListener(e -> selectFilter(buildUp, menuBuildUp, BUILDUP));
        ordered.addActionListener(e -> selectFilter(ordered, menuOrdered, ORDERED));
        sepia.addActionListener(e -> selectFilter(sepia, menuSepia, SEPIA));
        rotate.addActionListener(e  -> {
            selectFilter(rotate, menuRotate, ROTATE);
            setScreen();
        });

        fitToScreen.addActionListener(e -> menuFitToScreen());

        JButton about = new JButton(new ImageIcon("src/main/resources/about.png"));
        about.addActionListener(e  -> menuAbout());
        JButton save = new JButton(new ImageIcon("src/main/resources/save.png"));
        save.addActionListener(e -> menuSave());
        JButton exit = new JButton(new ImageIcon("src/main/resources/exit.png"));
        exit.addActionListener(e -> System.exit(0));

        save.setToolTipText("Save your image");
        exit.setToolTipText("Exit the program");
        about.setToolTipText("Check information about the program");
        fitToScreen.setToolTipText("Make resize");
        rotate.setToolTipText("Rotate the image");
        ordered.setToolTipText("Ordered filter");
        buildUp.setToolTipText("Build up filter");
        floyd.setToolTipText("Floyd filter");
        sobel.setToolTipText("Sobel filter");
        roberts.setToolTipText("Roberts filter");
        gamma.setToolTipText("Gamma filter");
        grey.setToolTipText("Grey filter");
        inversion.setToolTipText("Inversion filter");
        aqua.setToolTipText("Aqua filter");
        emboss.setToolTipText("Emboss filter");
        blur.setToolTipText("Blur filter");
        sharp.setToolTipText("Sharp filter");
        sepia.setToolTipText("Sepia filter");

        List.of(sharp, blur, emboss, aqua, inversion, grey, gamma, roberts, sobel,
                floyd, buildUp, ordered, rotate, sepia).forEach(buttonGroup::add);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ignored) {}

        List.of(menuSharp, menuBlur, menuEmboss, menuAqua, menuInversion, menuGrey, menuGamma,
                menuRoberts, menuSobel, menuFloyd, menuBuildUp, menuOrdered,
                menuRotate, menuSepia).forEach(menu::add);

        List.of(about, exit, save, sharp, blur, emboss, aqua, buildUp, inversion, grey, gamma, sepia, roberts, sobel,
                floyd, ordered, rotate, fitToScreen).forEach(toolBar::add);

        panelWithScroll.setLayout(new BorderLayout());
        panelWithScroll.add(panel);
        add(panelWithScroll);
        setFitToScreen(isFitToScreenMode);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel.setImageSize();
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        setVisible(true);
        pack();
    }

    private JRadioButtonMenuItem addRB(JMenu menu, String element) {
        JRadioButtonMenuItem object = new JRadioButtonMenuItem(element);
        menu.add(object);
        object.addActionListener(new MenuHandler());
        return object;
    }

    private void addToJMenu(JMenu menu, String[] arr) {
        for (String element : arr) {
            switch (element) {
                case "Sharp" -> menuSharp = addRB(menu, element);
                case "Blur" ->  menuBlur = addRB(menu, element);
                case "Emboss" -> menuEmboss = addRB(menu, element);
                case "Aquarelle" -> menuAqua = addRB(menu, element);
                case "Inversion" -> menuInversion = addRB(menu, element);
                case "Grey" -> menuGrey = addRB(menu, element);
                case "Gamma" -> menuGamma = addRB(menu, element);
                case "Roberts" -> menuRoberts = addRB(menu, element);
                case "Sobel" -> menuSobel = addRB(menu, element);
                case "FloydDither" -> menuFloyd = addRB(menu, element);
                case "BuildUp" -> menuBuildUp = addRB(menu, element);
                case "OrderedDither" -> menuOrdered = addRB(menu, element);
                case "Sepia" -> menuSepia = addRB(menu, element);
                case "Rotate" -> menuRotate = addRB(menu, element);
                case "FitToScreen" -> fitToScreenMenuItem = addRB(menu, element);
                default ->  {
                    JMenuItem menuItem = new JMenuItem(element);
                    menu.add(menuItem);
                    menuItem.addActionListener(new MenuHandler());
                }
            }
        }
        menuBar.add(menu);
    }

    public void menuAbout() {
        JOptionPane.showMessageDialog(null, fileAbout,
                "About Init", INFORMATION_MESSAGE);
    }

    public void menuSave() {
        File file = FileOperations.getSaveImageFileName(this);
        try {
            if (file != null) {
                panel.save(file);
                return;
            }
            throw new FileNotFoundException("File not found");
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", ERROR_MESSAGE);
        }
    }

    private void selectFilter(JToggleButton btn, JRadioButtonMenuItem menuItem,
                              CurrentFilter currentFilter) {
        if (currentFilter == panel.getFilter()) {
            menu.clearSelection();
            buttonGroup.clearSelection();
            panel.setFilter(CurrentFilter.NONE);
            panel.useFilter();
            return;
        }
        if (onParameters(currentFilter)) {
            menu.clearSelection();
            buttonGroup.clearSelection();
            btn.setSelected(true);
            menuItem.setSelected(true);
            panel.setFilter(currentFilter);
            panel.useFilter();
        } else {
            setButton(panel.getFilter());
        }
    }

    private void selectFilterButton(JToggleButton btn1, JRadioButtonMenuItem btn2) {
        btn1.setSelected(true);
        btn2.setSelected(true);
    }

    private void setButton(CurrentFilter currentFilter) {
        buttonGroup.clearSelection();
        menu.clearSelection();
        switch (currentFilter) {
            case AQUARELLE: selectFilterButton(aqua, menuAqua);
            case BLUR: selectFilterButton(blur, menuBlur);
            case EMBOSS: selectFilterButton(emboss, menuEmboss);
            case FLOYD: selectFilterButton(floyd, menuFloyd);
            case GAMMA: selectFilterButton(gamma, menuGamma);
            case GREY: selectFilterButton(grey, menuGrey);
            case INVERSION: selectFilterButton(inversion, menuInversion);
            case ORDERED: selectFilterButton(ordered, menuOrdered);
            case ROBERTS: selectFilterButton(roberts, menuRoberts);
            case ROTATE: selectFilterButton(rotate, menuRotate);
            case BUILDUP: selectFilterButton(buildUp, menuBuildUp);
            case SHARP: selectFilterButton(sharp, menuSharp);
            case SOBEL: selectFilterButton(sobel, menuSobel);
            case SEPIA: selectFilterButton(sepia, menuSepia);
        }
    }

    public void menuOpen() {
        panel.open(FileOperations.getOpenImageFileName(this));
        panel.setFilter(NONE);
        setButton(NONE);
        if (scrollPane != null) {
            scrollPane.revalidate();
        }
    }

    public boolean onParameters(FilterWindow filterWindow) {
        if (filterWindow != null) {
            JDialog dialog = new JDialog(this,"Set parameters",true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.add((Component)filterWindow);
            dialog.pack();
            dialog.setBounds((int)(screenSize.getWidth() / 2 - dialog.getWidth() / 2),
                    (int)(screenSize.getHeight() / 2 - dialog.getHeight() / 2),
                    dialog.getWidth(), dialog.getHeight());
            dialog.setVisible(true);
            return filterWindow.isOkPressed();
        }
        return true;
    }

    public boolean onParameters() {
        return onParameters(panel.getParametersPanel());
    }


    public boolean onParameters(CurrentFilter currentFilter) {
        return onParameters(currentFilter.getFilter().getFilterWindow());
    }

    public void menuFitToScreen() {
        isFitToScreenMode = !isFitToScreenMode;
        setFitToScreen(isFitToScreenMode);
    }

    public void setScreen() {
        panelWithScroll.setPreferredSize(panelWithScroll.getSize());
        pack();
        panel.setImage();
        panelWithScroll.setPreferredSize(null);
        repaint();
    }

    void setFitToScreen(boolean isFitToScreenMode) {
        fitToScreen.setSelected(isFitToScreenMode);
        fitToScreenMenuItem.setSelected(isFitToScreenMode);
        if (isFitToScreenMode) {
            panel.showFitDialog();
            removeScrolls();
        } else {
            addScrolls();
        }
        panel.setFitToScreen(isFitToScreenMode);
        panelWithScroll.setPreferredSize(panelWithScroll.getSize());
        pack();
        panel.setImage();
        panelWithScroll.setPreferredSize(null);
        repaint();
    }

    private void addScrolls() {
        panelWithScroll.remove(panel);
        panel.setAutoscrolls(true);
        scrollPane = new JScrollPane(panel);
        panelWithScroll.add(scrollPane);
        mouseAdapter = new MouseAdapter() {
            private Point origin;
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
                lastX = e.getX();
                lastY = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport)getAncestorOfClass(JViewport.class, panel);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();
                        Point scroll = viewPort.getViewPosition();
                        scroll.x += (lastX - e.getX());
                        scroll.y += (lastY - e.getY());

                        Rectangle rect = viewPort.getViewRect();
                        rect.x += deltaX;
                        rect.y += deltaY;
                        panel.scrollRectToVisible(rect);
                    }
                }
            }
        };
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        scrollPane.revalidate();
    }

    private void removeScrolls() {
        panelWithScroll.remove(scrollPane);
        panel.setAutoscrolls(false);
        panel.removeMouseListener(mouseAdapter);
        panel.removeMouseMotionListener(mouseAdapter);
        panelWithScroll.add(panel);
    }

    private class MenuHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            switch (event.getActionCommand()) {
                case "Open" -> menuOpen();
                case "Save" -> menuSave();
                case  "Apply" -> {
                    panel.applyChanges();
                    panel.setFilter(NONE);
                    setButton(NONE);
                }
                case "Exit" -> System.exit(0);
                case "Parameters" -> {
                    if (onParameters()) {
                        panel.useFilter();
                    }
                }
                case "FitSettings" -> {
                    panel.showFitDialog();
                    if (isFitToScreenMode) {
                        panel.setImage();
                    }
                }
                case "Rotate" -> {
                    selectFilter(rotate, menuRotate, ROTATE);
                    setScreen();
                }
                case "FitToScreen" -> menuFitToScreen();
                case "Sharp" -> selectFilter(sharp, menuSharp, SHARP);
                case "Blur" -> selectFilter(blur, menuBlur, BLUR);
                case "Emboss" -> selectFilter(emboss, menuEmboss, EMBOSS);
                case "Aquarelle" -> selectFilter(aqua, menuAqua, AQUARELLE);
                case "Inversion"-> selectFilter(inversion, menuInversion, INVERSION);
                case "Grey"->  selectFilter(grey, menuGrey, GREY);
                case "Gamma" -> selectFilter(gamma, menuGamma, GAMMA);
                case "Roberts" -> selectFilter(roberts, menuRoberts, ROBERTS);
                case "Sobel" -> selectFilter(sobel, menuSobel, SOBEL);
                case "FloydDither" -> selectFilter(floyd, menuFloyd, FLOYD);
                case "BuildUp" -> selectFilter(buildUp, menuBuildUp, BUILDUP);
                case "Sepia" -> selectFilter(sepia, menuSepia, SEPIA);
                case "OrderedDither" -> selectFilter(ordered, menuOrdered, ORDERED);
                case "About" -> menuAbout();
            }
        }
    }
}
