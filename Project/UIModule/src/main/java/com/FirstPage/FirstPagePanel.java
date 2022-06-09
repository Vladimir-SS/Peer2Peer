package com.FirstPage;

import com.misc.FontState;
import javax.swing.*;
import java.awt.*;

/**
 * This class is supposed to assign according to some values ,the space left for the actual content,and leave everything else as empty
 */
public class FirstPagePanel extends JPanel {

    //Background color used
    private static final Color backgroundColor=Color.white;

    //The north,south,east and west percent margins
    private static final int northMarginPercent =10;
    private static final int southMarginPercent =10;
    private static final int eastMarginPercent =10;
    private static final int westMarginPercent =10;

    //The actual sizes for the margin after they are computed
    private final int northMargin;
    private final int southMargin;
    private final int eastMargin;
    private final int westMargin;

    private final FirstPageContentPanel firstPageContentPanel;

    /**
     * Constructor for the FirstPagePanel.This constructor will make a panel that will have an empty space on the east,west,north and south according to the values from the class
     * @param widthPage the width of the page
     * @param heightPage the height of the page
     * @param title the title for the page
     * @throws IllegalArgumentException if widthPage or heightPage are <= 0,or if title is null or empty
     */
    public FirstPagePanel(int widthPage,int heightPage,String title) throws IllegalArgumentException{

        if(widthPage<=0){
            throw new IllegalArgumentException("Not allowed to give the width of the page as negative or 0");
        }

        if(heightPage<=0){
            throw new IllegalArgumentException("Not allowed to give the height of the page as negative or 0");
        }

        if(title==null){
            throw new IllegalArgumentException("Not allowed to give the value for the title as null");
        }

        if(title.equals( "" )){
            throw new IllegalArgumentException("Not allowed to give the title as empty");
        }

        this.setPreferredSize( new Dimension(widthPage,heightPage) );
        this.setLayout( new BorderLayout() );

        Component emptySpace;

        //Add north empty space/margin
        northMargin=computePercentOf( heightPage , northMarginPercent );
        emptySpace=Box.createVerticalStrut( northMargin );
        emptySpace.setBackground( backgroundColor );
        this.add(emptySpace,BorderLayout.NORTH);

        //Add south empty space/margin
        southMargin=computePercentOf( heightPage,southMarginPercent );
        emptySpace=Box.createVerticalStrut( southMargin );
        emptySpace.setBackground( backgroundColor );
        this.add(emptySpace,BorderLayout.SOUTH);

        //Add east empty space/margin
        eastMargin=computePercentOf( widthPage,eastMarginPercent );
        emptySpace=Box.createHorizontalStrut( eastMargin );
        emptySpace.setBackground( backgroundColor );
        this.add(emptySpace ,BorderLayout.EAST );


        //Add west empty space/margin
        westMargin=computePercentOf( widthPage,westMarginPercent );
        emptySpace=Box.createHorizontalStrut( westMargin );
        emptySpace.setBackground( backgroundColor );
        this.add( emptySpace ,BorderLayout.WEST );

        //Center
        firstPageContentPanel=new FirstPageContentPanel( widthPage- eastMargin - westMargin ,heightPage- northMargin - southMargin , title );
        this.add( firstPageContentPanel ,BorderLayout.CENTER );
    }

    /**
     * Computes the % percent from a value.(for instance,50% of 200 would be 100)
     * @param length the value
     * @param percent the percent
     * @return an int value representing %percent from the length
     * @throws IllegalArgumentException if length is <= 0 or if percent doesn't belong to the interval [0,100]
     */
    private static int computePercentOf(int length,int percent) throws IllegalArgumentException{

        if(length<=0){
            throw new IllegalArgumentException("Not allowed to give the length as negative or null");
        }

        if(!(percent>=0&&percent<=100)){
            throw new IllegalArgumentException("The percent given has to be between [0,100]");
        }

        return percent*length/100;
    }

    /**
     * Gets the port field of the panel
     * @return an instance of the class TextFieldWithPrompt being the port input field
     */
    public TextFieldWithPrompt getPortField(){
        return firstPageContentPanel.getPortField();
    }
}
