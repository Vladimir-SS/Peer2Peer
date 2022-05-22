package com.menu;

import com.SyncPage.*;


import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int appWidth =1280;
    private static final int appHeight =720;

    private static final int mainMenuInitialWidth=200;
    private static final int mainMenuInitialHeight=720;
    private static final int buttonRadius=20;
    private static final int panelRadius = 50;

    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            JFrame frame = new JFrame( "My app" );

            frame.setPreferredSize( new Dimension( appWidth , appHeight ) );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setLayout( new BorderLayout() );
            frame.setResizable( false );

            List<String> listButtonsName = new ArrayList<>();
            listButtonsName.add( "Connections" );
            listButtonsName.add( "Files" );
            listButtonsName.add( "Sync" );
            listButtonsName.add( "Settings" );

            MainMenu menuPanel = new MainMenu( mainMenuInitialWidth , mainMenuInitialHeight , buttonRadius , listButtonsName );
            //SyncPage syncPage = new SyncPage(mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius,dimension);

            frame.add( menuPanel , BorderLayout.WEST );
            //frame.add(syncPage, BorderLayout.CENTER);


            frame.pack();

            frame.setVisible( true );
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
