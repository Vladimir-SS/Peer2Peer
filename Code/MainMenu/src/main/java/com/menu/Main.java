package com.menu;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final int appInitialWidth =1280;
    private static final int appInitialHeight =720;

    private static final int mainMenuInitialWidth=200;
    private static final int mainMenuInitialHeight=720;

    public static void main(String[] args){
        JFrame frame=new JFrame("My app");

        frame.setPreferredSize( new Dimension( appInitialWidth , appInitialHeight ) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new BorderLayout() );

        List<String> listButtonsName=new ArrayList<>();
        listButtonsName.add( "Connections" );
        listButtonsName.add( "Files" );
        listButtonsName.add( "Synchronization" );
        listButtonsName.add( "Settings" );

        MainMenu menuPanel=new MainMenu(mainMenuInitialWidth,mainMenuInitialHeight, listButtonsName );

        frame.add( menuPanel,BorderLayout.WEST );

        frame.pack();

        frame.setVisible( true );
    }
}
