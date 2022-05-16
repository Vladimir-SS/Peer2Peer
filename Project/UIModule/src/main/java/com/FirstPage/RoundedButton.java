package com.FirstPage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * Represents a button that has a rounded border
 */
public class RoundedButton extends JButton {

    /**
     * Constructor for the RoundedButton class
     * @param text the text for the button
     * @param radius the radius for the button which will be used when drawing the background and border rounded
     * @param drawRoundRectOverRect if true,the paintComponent function will firstly draw a rectangle with the background color of the parent,after which the rounded rectangle background,otherwise it will draw only the rounded background and border
     * @throws IllegalArgumentException thrown if the text is null or empty,or if the radius is smaller or equal to 0
     */
    public RoundedButton(String text,int radius,boolean drawRoundRectOverRect) throws IllegalArgumentException{
        super(text);

        if(text==null){
            throw new IllegalArgumentException("Not allowed to give the name(text) for the button as null");
        }

        if(text.equals( "" )){
            throw new IllegalArgumentException("Not allowed to give the name(text) for the button as empty");
        }

        if(radius<=0){
            throw new IllegalArgumentException("Not allowed to give the radius as smaller or equal to 0");
        }

        this.setUI(new BasicButtonUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                if (c.isOpaque()) {

                    Color fillColor = c.getBackground();
                    if(drawRoundRectOverRect) {
                        //Drawing the normal rectangle with the background color of the parent first
                        g.setColor( getParent().getBackground() );
                        g.fillRect( 0 , 0 , c.getWidth() , c.getWidth() );
                    }

                    //Then,drawing the rounded rectangle with the proper color over the parent
                    //Doing this so that the background at the corners works fine
                    g.setColor(fillColor);
                    g.fillRoundRect(0, 0, c.getWidth()-1,c.getHeight()-1, radius, radius);
                }
                paint(g, c);
            }
        });

        this.setBorder( new RoundedBorder( radius ) );
    }
}

/**
 * Class that deals with drawing a rounded border
 */
class RoundedBorder implements Border {

    private final int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets( Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}


