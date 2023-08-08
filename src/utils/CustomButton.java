package utils;

import ui.AbstractScreen;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String text, String setting) {
        super(text);
        if (setting == "m")
            initMainButton();
        else if (setting == "s")
            initSecondButton();

    }

    public CustomButton(String text) {
        super(text);
        initMainButton();


    }

    private void initSecondButton() {
        AbstractScreen.setColors(this, "s");
        setBorder( new LineBorder(AbstractScreen.SECOND_COLOR) );
        setOpaque(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(50, 50, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(AbstractScreen.SECOND_COLOR);
            }
        });
    }

    private void initMainButton() {
        AbstractScreen.setColors(this, "m");
        setBorder( new LineBorder(AbstractScreen.MAIN_COLOR) );
        setOpaque(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(AbstractScreen.TAB_HIGHLIGHTED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(AbstractScreen.TAB_COLOR);
            }
        });
    }

}
