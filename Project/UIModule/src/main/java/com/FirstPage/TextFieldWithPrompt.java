package com.FirstPage;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a text input field that will have a placeholder(a string which will remain in the field) as
 * long as the user hasn't written a string
 */
public class TextFieldWithPrompt extends JTextField {

    private final String placeholderText;
    private final Font font;

    //Used to place some empty space between where the placeholder is drawn and the west border of the text field
    private static final int leftPadding=5;

    //The color of the placeholder text
    private static final Color textColor=Color.gray;
    /**
     * Constructor for the TextFieldWithPrompt class
     * @param placeholderText the text which will be drawn in the text field
     * @param font the font of the placeholder text
     * @throws IllegalArgumentException if placeholderText is null or empty,or if font is null
     */
    public TextFieldWithPrompt(String placeholderText,Font font) throws IllegalArgumentException{
        super();

        if(placeholderText==null){
            throw new IllegalArgumentException("Not allowed to give the placeholder text as null");
        }

        if(placeholderText.equals( "" )){
            throw new IllegalArgumentException("Not allowed to give the placeholder text as empty");
        }

        if(font==null){
            throw new IllegalArgumentException("Not allowed to give the font as null");
        }

        this.placeholderText=placeholderText;
        this.font=font;
    }

    /**
     * Custom paintComponent that will draw(if the text field currently has no value introduced by the user) the placeholder string
     * @param g graphics
     */
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if(getText().isEmpty()){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setFont(getFont().deriveFont(Font.PLAIN));
            g2.setColor( textColor );

            //Compute the needed y coordinate so that the placeholder text is centered
            FontMetrics metrics=g.getFontMetrics(font);
            int yCoordinate=(this.getHeight()-metrics.getHeight())/2+metrics.getAscent();

            g2.drawString(placeholderText, leftPadding ,yCoordinate );
            g2.dispose();
        }
    }
}

