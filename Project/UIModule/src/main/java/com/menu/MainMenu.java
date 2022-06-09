package com.menu;

import com.FirstPage.RoundedButton;
import com.misc.FontState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a main menu for the application that contains 0 or more buttons
 */
public class MainMenu extends JPanel {

    private static final Color backgroundColor=Color.decode( "#CCDDE2" );
    private static final Color whenSelectedBackgroundColor =Color.decode( "#DC965A" );

    private static final int buttonHeight=75;
    private static final int buttonSpacing=40;

    private final int emptySpaceHeight;
    private static final int emptySpaceWidth=10;

    private final JPanel buttonPanel;

    private JButton lastButtonPressed=null;

    private final List<JButton> listButtons;

    /**
     * Constructor for the MainMenu class
     * @param width the width of the main menu
     * @param height the height of the main menu
     * @param buttonCornerRadius the radius which will curve the buttons border and background accordingly
     * @param listButtonsName the list for the names(text) of the buttons
     * @throws IllegalArgumentException thrown if the width,height or buttonCornerRadius are less or equal than 0,if the listButtonsName is null or if a button name is null or empty
     */
    public MainMenu(int width ,int height ,int buttonCornerRadius , List<String> listButtonsName) throws IllegalArgumentException{

        super();

        if(width<=0){
            throw new IllegalArgumentException("Not allowed to give the width of the main menu as negative or zero");
        }

        if(height<=0){
            throw new IllegalArgumentException("Not allowed to give the height of the main menu as negative or zero");
        }

        if(buttonCornerRadius<=0){
            throw new IllegalArgumentException("Not allowed to give the radius for the buttons as negative or null");
        }

        if(listButtonsName==null){
            throw new IllegalArgumentException("Not allowed to give the list of buttons names as null");
        }

        for(String name:listButtonsName){
            if(name==null){
                throw new IllegalArgumentException("Not allowed to give the name of a button as null");
            }

            if(name.equals( "" )) {
                throw new IllegalArgumentException("Not allowed to give the name of a button as empty");
            }
        }

        this.setLayout( new BorderLayout() );

        this.setPreferredSize( new Dimension(width,height) );

        this.setBackground( Color.decode( "#69A2B8" ) );

        //Compute the empty space left for the height
        int numberButtons=listButtonsName.size();
        emptySpaceHeight=(height-numberButtons*buttonHeight-(numberButtons-1)*buttonSpacing)/2;

        //Add to the panel the empty space(the one below/above the buttons and at the right/left of the buttons)
        this.add( Box.createVerticalStrut( emptySpaceHeight ),BorderLayout.NORTH );
        this.add( Box.createVerticalStrut( emptySpaceHeight ),BorderLayout.SOUTH );
        this.add( Box.createHorizontalStrut( emptySpaceWidth),BorderLayout.WEST );
        this.add( Box.createHorizontalStrut( emptySpaceWidth ),BorderLayout.EAST );

        //Button panel creation
        buttonPanel=new JPanel();
        buttonPanel.setBackground( Color.decode( "#69A2B8" ) );
        buttonPanel.setPreferredSize( new Dimension(width,height-2*emptySpaceHeight) );
        buttonPanel.setLayout( new GridLayout(numberButtons,1,0,buttonSpacing) );

        listButtons=new ArrayList<>();
        for ( String buttonName : listButtonsName ) {
            listButtons.add( new RoundedButton( buttonName, buttonCornerRadius,true ) );
        }

        for(JButton button:listButtons){

            setButtonProperties( button );

            addButtonObserver(button);

            buttonPanel.add( button );
        }

        //Don't forget to add panel to this panel

        this.add( buttonPanel,BorderLayout.CENTER );
    }

    /**
     * Sets the properties of a button(such as background color,font...)
     * @param button the button for which the properties are to be set
     * @throws IllegalArgumentException thrown if the button given is null
     */
    private void setButtonProperties(JButton button) throws IllegalArgumentException{

        if(button==null){
            throw new IllegalArgumentException("Not allowed to give the button as null");
        }

        button.setFocusable( false );

        button.setFont(FontState.getFont(2));
        button.setForeground( Color.black );

        button.setBackground( backgroundColor );
    }

    /**
     * Adds an action listener to a button
     * @param button the button given
     * @throws IllegalArgumentException thrown if the button given is null
     */
    private void addButtonObserver(JButton button) throws IllegalArgumentException{

        if(button==null){
            throw new IllegalArgumentException("Not allowed to give the button as null");
        }

        button.addActionListener( this::updateCurrentButton );
    }

    /**
     * Updates the background color of the button clicked to the selected button
     * @param actionEvent the action event
     * @throws IllegalArgumentException thrown if the actionEvent is null
     */
    private void updateCurrentButton(ActionEvent actionEvent) throws IllegalArgumentException{

        if(actionEvent==null){
            throw new IllegalArgumentException("Not allowed to have the actionEvent as null");
        }

        if(lastButtonPressed!=null){
            lastButtonPressed.setBackground( backgroundColor );
        }

        lastButtonPressed= (JButton) actionEvent.getSource();

        lastButtonPressed.setBackground( whenSelectedBackgroundColor );
    }

    public JButton getButtonWithName(String name){
        for(JButton button:listButtons){
            if(button.getText().equals( name )){
                return button;
            }
        }
        return null;
    }

    public void setLastButtonPressed(String name){

        if(lastButtonPressed!=null){
            lastButtonPressed.setBackground( backgroundColor );
        }

        lastButtonPressed=getButtonWithName( name );

        lastButtonPressed.setBackground( whenSelectedBackgroundColor );
    }
}