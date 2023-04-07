package view.windows.one_slider_windows;

import filters.AbstractParametredFilter;
import lombok.Getter;
import view.windows.FilterWindow;
import view.windows.TextAndSlider;
import view.windows.listener.Listener;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.List;

@Getter
public class AbstractOneSliderWindow extends JPanel implements FilterWindow {
    private static final int d = 10;
    protected boolean okPressed = false;
    private final JTextField valueText;

    public AbstractOneSliderWindow(AbstractParametredFilter filter, String paramName) {
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        TextAndSlider textAndSlider = createTextAndSLider(filter.getParam());
        textAndSlider.getText().addPropertyChangeListener(new Listener(0.1, 10, textAndSlider.getText(),
                textAndSlider.getSlider(), 1, 0));
        paramsPanel.add(new Label("Choose the " + paramName + " parameter:"));
        valueText = textAndSlider.getText();
        java.util.List.of(textAndSlider.getText(), textAndSlider.getSlider()).forEach(paramsPanel::add);
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("cancel");
        java.util.List.of(okBtn, cancelBtn).forEach(buttonPanel::add);

        okBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            filter.setParam((double)Math.round(Double.parseDouble(valueText.getText()) * 10) / 10);
            valueText.setText(String.valueOf(filter.getParam()));
            okPressed = true;
            dialog.dispose();
        });
        cancelBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            valueText.setText(String.valueOf(filter.getParam()));
            okPressed = false;
            dialog.dispose();
        });
        List.of(paramsPanel, buttonPanel).forEach(this::add);
    }

    protected TextAndSlider createTextAndSLider(double defaultValue) {
        JFormattedTextField text = new JFormattedTextField();
        text.setPreferredSize(new Dimension(35,20));
        JSlider slider = new JSlider((int)(0.1 * d), (int)((double) 10 * d), (int)(defaultValue * d));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        text.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
                if (str.length() + getLength() > 4){
                    return;
                }
                for (int i = 0; i < str.length(); ++i){
                    if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '-' && str.charAt(i) != '.') {
                        return;
                    }
                }
                super.insertString(offs,str,a);
            }
        });
        text.setText(String.valueOf(defaultValue));
        text.setPreferredSize(new Dimension(35,20));
        text.setVisible(true);
        slider.addChangeListener(changeEvent -> {
            int value = (slider.getValue());
            text.setText(String.valueOf((double)value / d));
        });
        return new TextAndSlider(text, slider);
    }
}
