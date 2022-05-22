package com.ConnectionPage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConPage extends JPanel implements ActionListener {

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");

    private static final Color buttoncolorgreen = Color.decode("#C3F73A");

    private static final Color tablecolor1 = Color.decode("#D4BA9E");
    private static final Color tablecolor2 = Color.decode("#CCDDE2");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");

    private static int pageWidth, pageHeight;

    private final JPanel container1, container2;
    private final JPanel syncPanel;
    private final JPanel OptionPanel;




    public ConPage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius){
        super();

        this.setLayout(new BorderLayout());

        pageWidth = appWidth-menuWidth;
        pageHeight = appHeight;
        this.setPreferredSize(new Dimension(pageWidth,pageHeight));

        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-50)), BorderLayout.NORTH);
        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-50)), BorderLayout.SOUTH);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-50)), BorderLayout.WEST);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-50)), BorderLayout.EAST);

        int container1Height = pageHeight-100;
        int container1Width = pageWidth-100;

        container1 = new JPanel(new BorderLayout());

        // Panel for the sync options
        syncPanel = new JPanel();
        syncPanel.setLayout(null);
        syncPanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

        JLabel syncText = new JLabel();
        syncText.setText("Connections");
        syncText.setFont(new Font( "Comic sans",Font.PLAIN,28 ));
        syncText.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        syncPanel.add(syncText);

        JLabel syncdev = new JLabel();
        syncdev.setText("Synchronised devices");
        syncdev.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        syncdev.setBounds(50,60,(int) (container1Width-container1Width/2),50);
        syncPanel.add(syncdev);

        String data[][]={
                {"Catalin","Laptop","420.420.420.420"},
                {"Catalin","Laptop","420.420.420.420"},
                {"Catalin","Laptop","420.420.420.420"},
                {"Catalin","Laptop","420.420.420.420"},
                {"Catalin","Laptop","420.420.420.420"}
        };
        String column[]={"Name","TYPE","IP"};
        final JTable syncheddevtab=new JTable(data,column);
        syncheddevtab.setFont(new Font( "Courier New",Font.BOLD,15 ));
        syncheddevtab.setBounds(50,100,500,200);
        syncheddevtab.setRowHeight(25);
        syncheddevtab.setBackground(tablecolor1);
        syncheddevtab.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        syncPanel.add(syncheddevtab);

        JLabel newcon = new JLabel();
        newcon.setText("New Connections");
        newcon.setFont(new Font( "Comic sans",Font.BOLD,20 ));
        newcon.setBounds(50,310,(int) (container1Width-container1Width/2),50);
        syncPanel.add(newcon);

        MyButton syncButton = new MyButton("Scan");
        syncButton.setFont(new Font("Comic Sans",Font.PLAIN,18));
        syncButton.setBackground(tablecolor1);
        syncButton.setFocusable(false);
        syncButton.setBorder(new LineBorder(Color.BLACK));
        syncButton.setBounds(350,320,200,30);
        syncButton.setPressedBackgroundColor(buttonPressedcolor);
        syncPanel.add(syncButton);

        String data1[][]={
                {"Device1","420.420.420.420"},
                {"Device2","420.420.420.420"},
        };
        String column1[]={"Device","IP"};
        final JTable newDevices=new JTable(data1, column1);
        newDevices.setFont(new Font( "Courier New",Font.BOLD,15 ));
        newDevices.setBounds(50,355,500,150);
        newDevices.setRowHeight(25);
        newDevices.setBackground(tablecolor2);
        newDevices.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        syncPanel.add(newDevices);

        syncPanel.add( new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);


        // Container for Time and Path Panels
        container2 = new JPanel(new BorderLayout());
        container2.setPreferredSize(new Dimension((int) (container1Width-(container1Width-container1Width/3)),container1Height));


        int container2Height = pageHeight-150;
        int container2Width = (int) (container1Width-(container1Width-container1Width/3));

        //
        OptionPanel = new JPanel();
        OptionPanel.setLayout(null);
        OptionPanel.setPreferredSize(new Dimension(container2Width,(int) (container1Height/1.07)));


        //to be added here

        JLabel optionstxt = new JLabel();
        optionstxt.setText("Options");
        optionstxt.setFont(new Font( "Comic sans",Font.PLAIN,28 ));
        optionstxt.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        OptionPanel.add(optionstxt);

        JLabel removetxt1 = new JLabel();
        removetxt1.setText("Remove selected device");
        removetxt1.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        removetxt1.setBounds(50,60,(int) (container1Width-container1Width/2),50);
        OptionPanel.add(removetxt1);

        MyButton Remove1 = new MyButton("Remove");
        Remove1.setFont(new Font("Comic Sans",Font.PLAIN,18));
        Remove1.setBackground(buttoncolor);
        Remove1.setFocusable(false);
        Remove1.setBorder(new LineBorder(Color.BLACK));
        Remove1.setBounds(50,115,160,30);
        Remove1.setPressedBackgroundColor(buttonPressedcolor);
        OptionPanel.add(Remove1);

        JLabel removetxt2 = new JLabel();
        removetxt2.setText("Remove selected device");
        removetxt2.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        removetxt2.setBounds(50,145,(int) (container1Width-container1Width/2),50);
        OptionPanel.add(removetxt2);

        MyButton Remove2 = new MyButton("Remove");
        Remove2.setFont(new Font("Comic Sans",Font.PLAIN,18));
        Remove2.setBackground(buttoncolorgreen);
        Remove2.setFocusable(false);
        Remove2.setBorder(new LineBorder(Color.BLACK));
        Remove2.setBounds(50,190,160,30);
        Remove2.setPressedBackgroundColor(buttonPressedcolor);
        OptionPanel.add(Remove2);

        JLabel syncNewDevTXT = new JLabel();
        syncNewDevTXT.setText("Remove selected device");
        syncNewDevTXT.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        syncNewDevTXT.setBounds(50,310,(int) (container1Width-container1Width/2),50);
        OptionPanel.add(syncNewDevTXT);

        MyButton syncNewDev = new MyButton("Sync");
        syncNewDev.setFont(new Font("Comic Sans",Font.PLAIN,18));
        syncNewDev.setBackground(buttoncolor);
        syncNewDev.setFocusable(false);
        syncNewDev.setBorder(new LineBorder(Color.BLACK));
        syncNewDev.setBounds(50,355,160,30);
        syncNewDev.setPressedBackgroundColor(buttonPressedcolor);
        OptionPanel.add(syncNewDev);

        JLabel propSelDevTXT = new JLabel();
        propSelDevTXT.setText("Remove selected device");
        propSelDevTXT.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        propSelDevTXT.setBounds(50,395,(int) (container1Width-container1Width/2),50);
        OptionPanel.add(propSelDevTXT);

        MyButton propSelDev = new MyButton("Proprieties");
        propSelDev.setFont(new Font("Comic Sans",Font.PLAIN,18));
        propSelDev.setBackground(buttoncolor);
        propSelDev.setFocusable(false);
        propSelDev.setBorder(new LineBorder(Color.BLACK));
        propSelDev.setBounds(50,440,160,30);
        propSelDev.setPressedBackgroundColor(buttonPressedcolor);
        OptionPanel.add(propSelDev);

        OptionPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width,(int) (container1Height/1.07));



        //Panel for "Choose path to synchronize to"


        container2.add(OptionPanel, BorderLayout.NORTH);


        container1.add(syncPanel, BorderLayout.WEST);
        container1.add(container2, BorderLayout.EAST);

        this.add(container1, BorderLayout.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

}



