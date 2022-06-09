package com.FirstPage;

import com.misc.FontState;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a panel that holds the content of the first page(such as the title,text,input fields,buttons)
 */
public class FirstPageContentPanel extends JPanel {

    //Colors used for background colors of the panel,field(text field) and button
    private static final Color backgroundColor=Color.decode( "#69A2B8" );
    private static final Color fieldBackgroundColor=Color.decode( "#CCDEE2" );
    private static final Color buttonBackgroundColor=Color.decode( "#DC965A" );

    //The margin of the title,or separating distance on the bottom and top of the title
    private final int marginTitle;
    private static final int marginTitlePercent=10;

    //Percentage used to compute the empty space from the east,west and south
    private static final int marginContentLeftPercent=20;
    private static final int marginContentRightPercent=20;
    private static final int marginContentBottomPercent=40;

    //Percentage used to create an empty space between fields
    private static final int emptySpaceHeightPercent=12;

    //The maximum percentage for the text input field(needed since a JTextField will expand as much as it can)
    private static final int inputMaximumWidthPercent=45;
    private static final int inputMaximumHeightPercent=12;

    //The font used for the fields
    private static final Font contentPanelFont=FontState.getFont(2);

    //The preferred size for the button
    private static final int connectButtonWidthPercent=30;
    private static final int connectButtonHeightPercent=15;

    //The connect button
    private RoundedButton connectButton;

    private final TextFieldWithPrompt portField;

    /**
     * Constructor for the FirstPageContentPanel class.It should display the elements similar to:
     * --------------
     * ----Title-----
     * --------------
     * ---TextField--
     * --------------
     * ---Password---
     * --------------
     * ---Connect----
     * --------------
     * @param width the width of the panel
     * @param height the height of the panel
     * @param title the value to be written for the title
     * @throws IllegalArgumentException if width or height are <= 0 or if the title is null or empty
     */
    public FirstPageContentPanel(int width,int height, String title) throws IllegalArgumentException{

        if(width<=0){
            throw new IllegalArgumentException("Not allowed to give the width of the page as negative or 0");
        }

        if(height<=0){
            throw new IllegalArgumentException("Not allowed to give the height of the page as negative or 0");
        }

        if(title==null){
            throw new IllegalArgumentException("Not allowed to give the value for the title as null");
        }

        if(title.equals( "" )){
            throw new IllegalArgumentException("Not allowed to give the title as empty");
        }

        //Set first page panel options
        this.setBackground( backgroundColor );
        this.setLayout( new BoxLayout( this,BoxLayout.PAGE_AXIS ) );
        this.setPreferredSize( new Dimension(width,height) );

        //Compute the marginTitle using the marginTitlePercent
        marginTitle=computePercentOf( height,marginTitlePercent );

        //Add title margin
        this.add( Box.createVerticalStrut( marginTitle ) );

        //Set and add title for the first page at north
        JLabel titleLabel=new JLabel(title);

        titleLabel.setForeground( Color.white );
        titleLabel.setFont(FontState.getFont(1)FontState.getFont(6));
        titleLabel.setAlignmentX( Component.CENTER_ALIGNMENT );

        this.add( titleLabel );

        //Add stub(empty space) between title and content
        this.add( Box.createVerticalStrut( marginTitle ) );

        //Create panel for content
        JPanel contentPanel=new JPanel();

        contentPanel.setLayout( new BoxLayout( contentPanel,BoxLayout.PAGE_AXIS ) );
        contentPanel.setBackground( backgroundColor );
        contentPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        contentPanel.setAlignmentY( Component.CENTER_ALIGNMENT );

        //TODO
        //Width of the content panel is computed properly
        //Height of the panel is not right(title.getHeight() is 0 since the panel which contains contentPanel decides the size of the contentPanel(i think)
        int contentWidth = computePercentOf( width , 100 - ( marginContentLeftPercent + marginContentRightPercent ) );
        int contentHeight = computePercentOf( height - titleLabel.getHeight() - 2 * marginTitle , 100 - marginContentBottomPercent );

        //Create label for text which describes the options
        JLabel textLabel=new JLabel("Choose a port for the application");
        textLabel.setForeground( Color.black );
        textLabel.setFont( contentPanelFont );
        textLabel.setAlignmentX( Component.CENTER_ALIGNMENT );

        contentPanel.add( textLabel );

        //Create empty space between
        contentPanel.add( Box.createVerticalStrut( computePercentOf( contentHeight ,emptySpaceHeightPercent ) ) );

        //Create Field input
        portField=new TextFieldWithPrompt( "Port number",contentPanelFont );
        portField.setBackground( fieldBackgroundColor );
        portField.setFont( contentPanelFont );
        portField.setAlignmentX( Component.CENTER_ALIGNMENT );
        portField.setMaximumSize( new Dimension(computePercentOf( contentWidth ,inputMaximumWidthPercent ),computePercentOf( contentHeight ,inputMaximumHeightPercent )) );
        portField.setFocusable( true );

        contentPanel.add( portField );

        //Create empty space between
        contentPanel.add( Box.createVerticalStrut(  computePercentOf( contentHeight ,emptySpaceHeightPercent ) ) );

        //Create connect button and a panel with GridLayout to contain it
        //Reason why used a panel(GridLayout) that contains the button is that ,the button itself disregards the setMinimum,setPreferred or setSetMaximum Size settings,
        //so this should make it expand

        //Create connect button
        connectButton=new RoundedButton( "Connect",20 ,true); //new JButton("Connect");
        connectButton.setBorder( null );
        connectButton.setForeground( Color.black );
        connectButton.setBackground( buttonBackgroundColor );
        connectButton.setAlignmentX( Component.CENTER_ALIGNMENT );
        connectButton.setFont( contentPanelFont );
        connectButton.setBorder( null );

        //Create connect button panel
        JPanel connectPanel=new JPanel();
        connectPanel.setBackground( backgroundColor );
        connectPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        connectPanel.setAlignmentY( Component.CENTER_ALIGNMENT );
        connectPanel.setLayout( new GridLayout(1,2) );
        connectPanel.setMaximumSize( new Dimension(computePercentOf( contentWidth, connectButtonWidthPercent ),computePercentOf( contentHeight,connectButtonHeightPercent )) );

        connectPanel.add( connectButton );
        contentPanel.add( connectPanel );


        //Panel to include the content panel which will choose how much space the panel will have left

        JPanel borderPanel=new JPanel();
        borderPanel.setLayout( new BorderLayout() );
        borderPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        borderPanel.setAlignmentY( Component.CENTER_ALIGNMENT );
        borderPanel.setBackground( backgroundColor );

        //Add left empty space
        borderPanel.add( Box.createHorizontalStrut( computePercentOf( width,marginContentLeftPercent ) ),BorderLayout.WEST );

        //Add right empty space
        borderPanel.add( Box.createHorizontalStrut( computePercentOf( width,marginContentRightPercent ) ),BorderLayout.EAST );

        //Add bottom/south empty space
        int heightSpaceLeft=height-titleLabel.getHeight()-2*marginTitle;
        borderPanel.add( Box.createVerticalStrut( computePercentOf( heightSpaceLeft,marginContentBottomPercent ) ),BorderLayout.SOUTH );

        //Add at center the content panel
        borderPanel.add( contentPanel,BorderLayout.CENTER );

        this.add( borderPanel );
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

    public RoundedButton getConnectButton(){
        return connectButton;
    }

    public TextFieldWithPrompt getPortField(){
        return portField;
    }
}
