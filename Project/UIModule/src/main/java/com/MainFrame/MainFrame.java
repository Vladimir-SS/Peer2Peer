package com.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connectivity.Peer;
import com.ConnectionPage.ConPage;
import com.misc.DataController;
import com.FilePage.FilePage;
import com.FirstPage.FirstPageContentPanel;

import com.menu.*;
import com.FirstPage.PortValidator;

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

    private SyncPage syncPage;
    private ConPage connectionPage;
    private FilePage filePage;
    private FirstPageContentPanel firstPage;
    private SettingsPage settingsPage;

    //Basically,the name of the button whose page is going to appear first after we press the Connect button
    private String nameFirstButton="Files";
    private int dimension=5;


    //Maybe enter bellow variables necessary for Connectivity and ResidentApp

    private int portNumber;

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

        syncPage = new SyncPage(mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius, dimension);
        connectionPage=new ConPage( mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius ,dimension);
        filePage=new FilePage( mainMenuInitialWidth,mainMenuInitialHeight,dimension );



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
    public int getDimension(){
        return dimension;
    }

    private void whenSyncButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );

        dimension=settingsPage.getFontSize();
        syncPage.setFontSyncPage(dimension);
        syncPage=syncPage = new SyncPage(mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius, dimension);
        panelSidePageContainer.add( syncPage,"syncPage" );
        cardLayoutPages.show( panelSidePageContainer ,"syncPage" );
    }

    private void whenConnButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        dimension=settingsPage.getFontSize();
        connectionPage.setFontConPage(dimension);
        connectionPage=new ConPage( mainMenuInitialWidth, mainMenuInitialHeight, appWidth, appHeight, panelRadius ,dimension);
        panelSidePageContainer.add( connectionPage,"connPage" );
        cardLayoutPages.show( panelSidePageContainer ,"connPage" );

    }

    private void whenFileButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        dimension=settingsPage.getFontSize();
        filePage.setFontFilePage(dimension);
        filePage=new FilePage( mainMenuInitialWidth,mainMenuInitialHeight,dimension );
        panelSidePageContainer.add( filePage,"filePage" );
        cardLayoutPages.show( panelSidePageContainer ,"filePage" );

    }

    private void whenConnectButtonPressed( ActionEvent e ){

        var portField=firstPage.getPortField();




// port
        var portInformation= new PortValidator( portField.getText() );

        if(portInformation.isValid()){

            int portNumber=portInformation.getPortNumber();


            this.portNumber=-1;

            if(Peer.isAvailable( portNumber )){
                //the port is good,we save it
                this.portNumber=portNumber;

                try {
                    DataController.setPeer(new Peer(portNumber));
                } catch (IOException ex) {
                    //TODO: George: is this the right way to do it?
                    return;
                }


                //Set who the first button is according to the first page after pressing Connect on the FirstPage
                menuPanel.setLastButtonPressed( nameFirstButton );

                // FontChangingInterface
                cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
                cardLayoutPages.show( panelSidePageContainer ,"filePage" );
                dimension=settingsPage.getFontSize();
                filePage.setFontFilePage(dimension);
            }
            else{
                JOptionPane.showMessageDialog( null,"The port is already taken!","Port error",JOptionPane.ERROR_MESSAGE );
            }
        }
        else{
            JOptionPane.showMessageDialog( null,portInformation.getErrorMessage(),"Port error",JOptionPane.ERROR_MESSAGE );
        }
// end port
    }

    private void whenSettingsButtonPressed( ActionEvent e ) {
        cardLayoutWholePages.show( panelWholePageContainer,"pagePanel" );
        cardLayoutPages.show( panelSidePageContainer ,"settingsPage" );
        dimension=settingsPage.getFontSize();
    }

    private void whenDisconnectButtonPressed( ActionEvent e ) {

        cardLayoutWholePages.show( panelWholePageContainer,"firstPage" );
        dimension=settingsPage.getFontSize();

    }
}
