package view.windows;

import filters.ScaleFilter;
import lombok.Getter;

import javax.swing.*;
import java.util.Objects;

import static filters.ScaleFilter.TransformType.*;

@Getter
public class ScalerFilterWindow extends JPanel implements FilterWindow {
    private final boolean okPressed = true;
    public ScalerFilterWindow(ScaleFilter scalerFilter) {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"NEAREST NEIGHBOR", "BILINEAR", "BICUBIC"});
        comboBox.setSelectedIndex(2);
        comboBox.addActionListener(e -> {
            switch (Objects.requireNonNull((String)((JComboBox)e.getSource()).getSelectedItem())) {
                case "BICUBIC" -> scalerFilter.setTransformType(BICUBIC);
                case "BILINEAR" -> scalerFilter.setTransformType(BILINEAR);
                case "NEAREST NEIGHBOR" -> scalerFilter.setTransformType(NEAREST_NEIGHBOR);
            }
        });
        add(comboBox);
    }
}
