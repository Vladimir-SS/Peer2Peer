package com.FirstPage;

import javax.swing.*;
import java.awt.*;

public class Main {

    //Application initial width and height
    private static final int appInitialWidth = 1280;
    private static final int appInitialHeight = 720;

    //First page width and height
    private static final int pageWidth=appInitialWidth;
    private static final int pageHeight=appInitialHeight;

    public static void main(String[] args){
        try {
            JFrame frame = new JFrame( "MyP2P" );

            frame.setPreferredSize( new Dimension( appInitialWidth , appInitialHeight ) );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setLayout( new BorderLayout() );
            frame.setResizable( false );

            //First page menu
            frame.add( new FirstPagePanel( pageWidth , pageHeight , "Welcome to MyP2P" ) );

            frame.pack();

            frame.setVisible( true );
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
