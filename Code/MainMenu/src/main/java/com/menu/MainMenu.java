package com.menu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JPanel {

    private final int buttonHeight;
    private final int buttonSpacing;

    private final int emptySpaceHeight;
    private final int emptySpaceWidth;

    private JPanel buttonPanel;

    private final List<JButton> listButtons;
    public MainMenu(int width ,int height , List<String> listButtonsName){

        super();

        this.setLayout( new BorderLayout() );

        this.setPreferredSize( new Dimension(width,height) );

        this.setBackground( Color.decode( "#69A2B8" ) );

        buttonSpacing=20;
        buttonHeight=100;

        int numberButtons=listButtonsName.size();
        emptySpaceWidth=10;
        emptySpaceHeight=(height-numberButtons*buttonHeight-(numberButtons-1)*buttonSpacing)/2;

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
            listButtons.add( new JButton( buttonName ) );
        }

        for(JButton button:listButtons){

            setButtonProperties( button );

            buttonPanel.add( button );
        }

        //Don't forget to add panel to this panel

        this.add( buttonPanel,BorderLayout.CENTER );
    }

    private void setButtonProperties(JButton button){

        button.setFocusable( false );

        button.setFont(new Font( "Comic sans",Font.PLAIN,20 ));
        button.setForeground( Color.black );

        button.setBackground( Color.decode( "#CCDDE2" ) );

        button.setBorder(new RoundedBorder( 20,"#69A2B8" ));
    }
}

class RoundedBorder implements Border {

    private int radius;
    private String colorAsHexadecimal;

    public RoundedBorder( int radius , String colorAsHexadecimal ) {
        this.radius = radius;
        this.colorAsHexadecimal = colorAsHexadecimal;
    }

    public Insets getBorderInsets( Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor( Color.decode( colorAsHexadecimal ) );
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}


