package view.windows.parametred_windows;

import filters.matrix_filters.near_pixel_filters.BlurFilter;
import view.windows.listener.Listener;
import view.windows.TextAndSlider;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BlurFilterWindow extends AbstractFilterWindow {
    private final JTextField valueText;

    public BlurFilterWindow(BlurFilter blurFiler) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setLayout(new GridLayout(2, 1));
        TextAndSlider textAndSlider = createTextAndSLider(3, 11, 2, blurFiler.getMatrixSize());
        int system = 0;
        Listener propertyListener = new Listener(3, 11, textAndSlider.getText(), textAndSlider.getSlider(), system, 0);
        textAndSlider.getText().addPropertyChangeListener(propertyListener);
        paramsPanel.add(new Label("Choose the parameter:"));
        List.of(textAndSlider.getText(), textAndSlider.getSlider()).forEach(paramsPanel::add);
        valueText = textAndSlider.getText();
        List.of(textAndSlider.getText(), textAndSlider.getSlider()).forEach(paramsPanel::add);
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("cancel");
        List.of(okBtn, cancelBtn).forEach(buttonPanel::add);
        okBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            blurFiler.setMatrixSize(Integer.parseInt(valueText.getText()));
            okPressed = true;
            dialog.dispose();
        });
        cancelBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            valueText.setText(String.valueOf(blurFiler.getMatrixSize()));
            okPressed = false;
            dialog.dispose();
        });
        List.of(paramsPanel, buttonPanel).forEach(this::add);
    }
}
