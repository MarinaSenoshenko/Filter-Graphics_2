package view.windows;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

@Getter
public abstract class AbstractSimpleFilterWindow extends JPanel implements FilterWindow {
    protected boolean okPressed = false;

    protected void buildSimpleWindow(String title) {
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paramsPanel.add(new Label(title));
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("cancel");
        List.of(okBtn, cancelBtn).forEach(buttonPanel::add);
        okBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            okPressed = true;
            dialog.dispose();
        });
        cancelBtn.addActionListener(e -> {
            JDialog dialog = (JDialog)this.getRootPane().getParent();
            okPressed = false;
            dialog.dispose();
        });
        List.of(paramsPanel, buttonPanel).forEach(this::add);
    }
}
