package view.windows.parametred_windows;

import view.windows.AbstractSimpleFilterWindow;
import view.windows.TextAndSlider;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public abstract class AbstractFilterWindow extends AbstractSimpleFilterWindow {
    protected TextAndSlider createTextAndSLider(int minValue, int maxValue, int maxLength, int defaultValue) {
        JFormattedTextField text = new JFormattedTextField();
        text.setPreferredSize(new Dimension(35,20));
        JSlider jSlider = new JSlider(minValue, maxValue, defaultValue);
        text.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str.length() + getLength() > maxLength){
                    return;
                }
                for (int i = 0; i < str.length(); ++i) {
                    if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-'){
                        return;
                    }
                }
                super.insertString(offs, str, a);
            }
        });
        text.setText(String.valueOf(defaultValue));
        text.setPreferredSize(new Dimension(35,20));
        text.setText(String.valueOf(defaultValue));
        text.setVisible(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        jSlider.addChangeListener(changeEvent -> {
            int value = (jSlider.getValue());
            text.setText(String.valueOf(value));
        });
        return new TextAndSlider(text, jSlider);
    }
}
