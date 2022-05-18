package com.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * Represents a button that has a rounded border
 */
class RoundedButton extends JButton {

    /**
     * Constructor for the RoundedButton class
     * //   * @param text the text for the button
     * //    * @param radius the radius for the button which will be used when drawing the background and border rounded
     *
     * @throws IllegalArgumentException thrown if the text is null or empty,or if the radius is smaller or equal to 0
     */


    public RoundedButton(String text, int radius) throws IllegalArgumentException {
        super(text);

        if (text == null) {
            throw new IllegalArgumentException("Not allowed to give the name(text) for the button as null");
        }

        if (text.equals("")) {
            throw new IllegalArgumentException("Not allowed to give the name(text) for the button as empty");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("Not allowed to give the radius as smaller or equal to 0");
        }


        this.setUI(new BasicButtonUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                if (c.isOpaque()) {
                    if(getModel().isPressed()){
                        g.clearRect(0, 0, c.getWidth(), c.getHeight());
                        g.setColor(Color.decode("#69A2B8")); //the bckground color of the menu
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());

                        Color fillColor = c.getBackground();

                        g.setColor(fillColor.darker());
                        g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
                    }
                    else if(getModel().isRollover()){
                        /// it was a bug with the repaint of the menu buttons
                        g.clearRect(0, 0, c.getWidth(), c.getHeight());
                        g.setColor(Color.decode("#69A2B8"));
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());

                        Color fillColor = c.getBackground();

                        g.setColor(fillColor.brighter());
                        g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
                    }
                    else {
                        /// it was a bug with the repaint of the menu buttons
                        g.clearRect(0, 0, c.getWidth(), c.getHeight());
                        g.setColor(Color.decode("#69A2B8"));
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());

                        Color fillColor = c.getBackground();

                        g.setColor(fillColor);
                        g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
                    }

                }
                paint(g, c);
            }
        });

        this.setBorder(new RoundedBorder(radius));

    }
}

/**
 * Class that deals with drawing a rounded border
 */

class RoundedBorder implements Border {

    private final int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets( Component c) {
        return new Insets(this.radius, this.radius, this.radius, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
