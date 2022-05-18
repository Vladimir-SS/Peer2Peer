package com.ConnectionPage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

public class DrawRoundRectangle extends JPanel {

    public DrawRoundRectangle(int radius) {
        this.setUI(new BasicPanelUI(){
            @Override
            public void update(Graphics g, JComponent c) {
                if (c.isOpaque()) {
                    Color fillColor = Color.decode("#69A2B8");
                    g.setColor(fillColor);
                    g.fillRoundRect(0, 0, c.getWidth() -1, c.getHeight() -1 , radius, radius);
                }
                paint(g, c);
            }
        });
        this.setBorder(new RoundedRectBorder(radius));
    }
}

