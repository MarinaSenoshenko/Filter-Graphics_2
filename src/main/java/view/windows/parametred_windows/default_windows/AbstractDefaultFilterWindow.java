package view.windows.parametred_windows.default_windows;

import filters.AbstractParametredFilter;
import view.windows.TextAndSlider;
import view.windows.parametred_windows.AbstractFilterWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class AbstractDefaultFilterWindow extends AbstractFilterWindow {
    protected JFormattedTextField valueText;
    protected JTextField blueText, greenText, redText;

    protected TextAndSlider createDefault(int minvalue, int maxvalue, int maxLength, int defaultValue) {
        TextAndSlider textAndSlider = createTextAndSLider(minvalue, maxvalue, maxLength, defaultValue);
        textAndSlider.getText().addPropertyChangeListener(evt -> {
            if ("".equals(textAndSlider.getText().getText())) {
                textAndSlider.getText().setText(String.valueOf(1));
                return;
            }
            int inputValue = Integer.parseInt(textAndSlider.getText().getText());
            if (minvalue > inputValue) {
                textAndSlider.getText().setText(String.valueOf(minvalue));
                JOptionPane.showMessageDialog(null,"Enter a numerical value from " +
                        minvalue + " to " + maxvalue);
                return;
            }
            if (maxvalue < inputValue) {
                textAndSlider.getText().setText(String.valueOf(maxvalue));
                JOptionPane.showMessageDialog(null,"Enter a numerical value from " +
                        minvalue + " to " + maxvalue);
                return;
            }
            int value = Integer.parseInt(textAndSlider.getText().getText());
            textAndSlider.getSlider().setValue(value);
        });
        return textAndSlider;
    }

    protected void sliderTextWindow(AbstractParametredFilter filter, String title, int minValue, int maxValue, int maxLength) {
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        TextAndSlider textAndSlider = createDefault(minValue, maxValue, maxLength, filter.getBinParameter());
        paramsPanel.add(new Label(title));
        valueText = textAndSlider.getText();
        List.of(textAndSlider.getText(), textAndSlider.getSlider()).forEach(paramsPanel::add);
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("cancel");
        List.of(okBtn, cancelBtn).forEach(buttonPanel::add);

        okBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            if (minValue == -180) {
                filter.setAngle(Integer.parseInt(valueText.getText()));
            }
            else {
                filter.setBinParameter(Integer.parseInt(valueText.getText()));
            }
            okPressed = true;
            dialog.dispose();
        });
        cancelBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            valueText.setText(String.valueOf(filter.getBinParameter()));
            okPressed = false;
            dialog.dispose();
        });
        List.of(paramsPanel, buttonPanel).forEach(this::add);
    }

    protected void colorParameterWindow(AbstractParametredFilter filter) {
        setLayout(new GridLayout(2, 2));
        JPanel paramsPanel = new JPanel(new GridLayout(3, 3));
        JPanel buttonPanel = new JPanel();
        TextAndSlider textAndSlider = createDefault(2, 128, 3, filter.getRed());
        paramsPanel.add(new Label("Red:"));
        redText = textAndSlider.getText();
        List.of(redText, textAndSlider.getSlider()).forEach(paramsPanel::add);

        textAndSlider = createDefault(2, 128, 3, filter.getGreen());
        paramsPanel.add(new Label("Green:"));
        greenText = textAndSlider.getText();
        List.of(greenText, textAndSlider.getSlider()).forEach(paramsPanel::add);

        textAndSlider = createDefault(2,128, 3, filter.getBlue());
        paramsPanel.add(new Label("Blue:"));
        blueText = textAndSlider.getText();
        List.of(blueText, textAndSlider.getSlider()).forEach(paramsPanel::add);

        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("cancel");
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);

        okBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            filter.setBlue(Integer.parseInt(blueText.getText()));
            filter.setGreen(Integer.parseInt(greenText.getText()));
            filter.setRed(Integer.parseInt(redText.getText()));
            okPressed = true;
            dialog.dispose();
        });
        cancelBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            blueText.setText(String.valueOf(filter.getBlue()));
            greenText.setText(String.valueOf(filter.getGreen()));
            redText.setText(String.valueOf(filter.getRed()));
            okPressed = false;
            dialog.dispose();
        });
        List.of(paramsPanel, buttonPanel).forEach(this::add);
    }
}
