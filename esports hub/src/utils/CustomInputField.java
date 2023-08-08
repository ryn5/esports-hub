package utils;

import ui.AbstractScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomInputField extends JTextField {
    public CustomInputField(String text) {
        super(text);
        AbstractScreen.setColors(this, "s");
        setForeground(Color.GRAY);
        setPreferredSize(new Dimension(100, 20));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setText("");
                setForeground(AbstractScreen.TEXT_COLOR);
            }

            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(text);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

}
