package com.SyncPage;


import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

class MyButton extends JButton {

    private Color pressedBackgroundColor;

    public MyButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setOpaque(true);

        this.setUI(new BasicButtonUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                if(c.isOpaque()) {
                    if (getModel().isPressed()) {
                        g.setColor(getBackground().darker());
                    }
                    else if (getModel().isRollover()) {
                        g.setColor(getBackground().brighter());
                    }
                    else {
                        g.setColor(getBackground());
                    }
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                paint(g,c);
            }

        });
    }



    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}