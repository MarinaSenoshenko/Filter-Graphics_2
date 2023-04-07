package view.windows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@AllArgsConstructor
@Getter
@Setter
public class TextAndSlider {
    private JFormattedTextField text;
    private JSlider slider;
}
