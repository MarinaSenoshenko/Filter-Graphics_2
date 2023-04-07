package view.windows.listener;

import lombok.AllArgsConstructor;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@AllArgsConstructor
public class Listener implements PropertyChangeListener {
    private final double minvalue, maxvalue;
    private final JFormattedTextField text;
    private final JSlider slider;
    private final int system;
    private double inputValue;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (system == 1) {
            if (changer()) {
                return;
            }
            slider.setValue((int)(Double.parseDouble(text.getText()) * 10));

        }
        else {
            if (changer()) {
                return;
            }
            if (inputValue % 2 == 0) {
                text.setText(String.valueOf(slider.getValue() + 1));
                return;
            }
            slider.setValue(Integer.parseInt(text.getText()));
        }
    }

    private boolean changer() {
        if ("".equals(text.getText())) {
            text.setText(String.valueOf(slider.getValue() + 1));
            return true;
        }
        inputValue = Double.parseDouble(text.getText());
        if (minvalue > inputValue) {
            text.setText(String.valueOf(minvalue));
            return true;
        }
        if (maxvalue < inputValue) {
            text.setText(String.valueOf(maxvalue));
            return true;
        }
        return false;
    }
}
