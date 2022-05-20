package com.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.ConnectionPage.ConPage;
import com.FilePage.FilePage;
import com.FirstPage.FirstPageContentPanel;
import com.FirstPage.PortValidator;
import com.Menu.*;
import com.SettingsPage.SettingsPage;
import com.SyncPage.*;

public class MainFrame {

    private static final int appWidth =1280;
    private static final int appHeight =720;

    private static final int mainMenuInitialWidth=200;
    private static final int mainMenuInitialHeight=720;
    private static final int buttonRadius=20;
    private static final int panelRadius = 50;

    private final JFrame mainFrame;

    private final CardLayout cardLayoutPages;
    private final CardLayout cardLayoutWholePages;

    //The panel which will contain the page associated with a button
    private final JPanel panelSidePageContainer;
    private final JPanel panelWholePageContainer;
    private final JPanel pagePanel;

    private final MainMenu menuPanel;

    private final JPanel syncPage;
    private final JPanel connectionPage;
    private final JPanel filePage;
    private final FirstPageContentPanel firstPage;
    private final SettingsPage settingsPage;

    //Basically,the name of the button whose page is going to appear first after we press the Connect button
    private final String nameFirstButton="Files";


    public MainFrame(){

        pagePanel =new JPanel();
        pagePanel.setLayout( new BorderLayout() );

        mainFrame=new JFrame("MyP2P");

        mainFrame.setPreferredSize( new Dimension( appWidth , appHeight ) );
        mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        mainFrame.setLayout( new FlowLayout() );
        mainFrame.setResizable( false );

        List<String> listButtonsName = new ArrayList<>();
        listButtonsName.add( "Connections" );
        listButtonsName.add( "Files" );
        listButtonsName.add( "Sync" );
        listButtonsName.add( "Settings" );

        menuPanel = new MainMenu( mainMenuInitialWidth , mainMenuInitialHeight , buttonRadius , listButtonsName );

        //Make the cardLayoutWholePages
        cardLayoutWholePages=new CardLayout();

        //Add the whole page container
        panelWholePageContainer=new JPanel();

        panelWholePageContainer.setLayout( cardLayoutWholePages );

        //Make the cardLayoutPages
        cardLayoutPages=new CardLayout();

        //Adding the pages in the card container and giving them a proper id(name)
        panelSidePageContainer =new JPanel();
        panelSidePageContainer.setLayout( cardLayoutPages );

        syncPage = new SyncPage(mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius);
        connectionPage=new ConPage( mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius );
        filePage=new FilePage( mainMenuInitialWidth,mainMenuInitialHeight );

        firstPage=new FirstPageContentPanel( appWidth,appHeight,"Welcome to MyP2P" );

        settingsPage=new SettingsPage( mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius );

        panelSidePageContainer.add( syncPage,"syncPage" );
        panelSidePageContainer.add( connectionPage,"connPage" );
        panelSidePageContainer.add( filePage,"filePage" );
        panelSidePageContainer.add( settingsPage,"settingsPage" );

        //Set the listeners for the buttons to switch the shown page as needed

        menuPanel.getButtonWithName( "Sync" ).addActionListener( this::whenSyncButtonPressed );
        menuPanel.getButtonWithName( "Connections" ).addActionListener( this::whenConnButtonPressed );
        menuPanel.getButtonWithName( "Files" ).addActionListener( this::whenFileButtonPressed );
        menuPanel.getButtonWithName( "Settings" ).addActionListener( this::whenSettingsButtonPressed );

        //Set the listeners for the connect button and disconnect button

        firstPage.getConnectButton().addActionListener( this::whenConnectButtonPressed );

        settingsPage.getDisconnectButton().addActionListener( this::whenDisconnectButtonPressed );



        //Don't forget to add panelPages to the wholePage panel

        panelWholePageContainer.add( firstPage,"firstPage" );
        panelWholePageContainer.add( pagePanel ,"pagePanel" );

        cardLayoutWholePages.show( panelWholePageContainer,"firstPage" );

        mainFrame.add( panelWholePageContainer );


        //Add the menu and page to the pagePanel

        pagePanel.add( menuPanel,BorderLayout.WEST );
        pagePanel.add( panelSidePageContainer,BorderLayout.CENTER );

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void whenSyncButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        cardLayoutPages.show( panelSidePageContainer ,"syncPage" );
    }

    private void whenConnButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        cardLayoutPages.show( panelSidePageContainer ,"connPage" );
    }

    private void whenFileButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        cardLayoutPages.show( panelSidePageContainer ,"filePage" );
    }

    private void whenConnectButtonPressed( ActionEvent e ) {

        var portField=firstPage.getPortField();

        var portInformation= new PortValidator( portField.getText() );

        if(portInformation.isValid()){
            //TODO
            //add required functionalities

            //Set who the first button is according to the first page after pressing Connect on the FirstPage
            menuPanel.setLastButtonPressed( nameFirstButton );

            cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
            cardLayoutPages.show( panelSidePageContainer ,"filePage" );
        }
        else{
            JOptionPane.showMessageDialog( null,portInformation.getErrorMessage(),"Port error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void whenSettingsButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        cardLayoutPages.show( panelSidePageContainer ,"settingsPage" );
    }

    private void whenDisconnectButtonPressed( ActionEvent e ) {

        cardLayoutWholePages.show( panelWholePageContainer,"firstPage" );
    }
}
