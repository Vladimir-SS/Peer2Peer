package com.ConnectionPage;

import com.misc.FontState;
import com.misc.CustomTableModel;
import com.misc.DataController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConPage extends JPanel implements ActionListener {

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");

    private static final Color buttoncolorgreen = Color.decode("#C3F73A");

    private static final Color tablecolor1 = Color.decode("#D4BA9E");
    private static final Color tablecolor2 = Color.decode("#CCDDE2");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");

    public int dimension;

    private JTable connectedDevicesTable;
    private final JTable foundConnectionsTable;


    private void scanButtonHandler(){
        new Thread(()-> {
            DefaultTableModel model = (DefaultTableModel) foundConnectionsTable.getModel();
            model.setRowCount(0);
            model.addRow(new String[]{"Finding Devices...", ""});
            DataController.findDevices();

            var data = DataController.getLastFoundDevices();

            model.setRowCount(0);
            if(data.size() == 0)
                model.addRow(new String[]{"No devices were found.", ""});
            else
                data.forEach(model::addRow);
        }).start();
    }

    public JTable createDeviceTable(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Device");
        model.addColumn("IP");
        data.forEach(model::addRow);

        final JTable table = new JTable(model);
        table.setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        return table;
    }

    public void syncButtonHandler() {
        int index = foundConnectionsTable.getSelectedRow();
        if(index == -1)
            return;
        
        DataController.connectDevice(index);
        DefaultTableModel model = (DefaultTableModel) connectedDevicesTable.getModel();

        model.setRowCount(0);
        DataController.getConnectedDevices().forEach(model::addRow);
    }

    public void removeButtonHandler() {
        int index = connectedDevicesTable.getSelectedRow();
        if(index == -1)
            return;

        DataController.disconnectDevice(index);
        DefaultTableModel model = (DefaultTableModel) connectedDevicesTable.getModel();

        model.setRowCount(0);
        DataController.getConnectedDevices().forEach(model::addRow);
    }


    public ConPage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius, int dimension){
        super();
        this.dimension=dimension;
        this.setLayout(new BorderLayout());


        int pageWidth = appWidth - menuWidth;
        this.setPreferredSize(new Dimension(pageWidth, appHeight));

        this.add( Box.createVerticalStrut(appHeight -(appHeight -50)), BorderLayout.NORTH);
        this.add( Box.createVerticalStrut(appHeight -(appHeight -50)), BorderLayout.SOUTH);
        this.add( Box.createHorizontalStrut(pageWidth -(pageWidth -50)), BorderLayout.WEST);
        this.add( Box.createHorizontalStrut(pageWidth -(pageWidth -50)), BorderLayout.EAST);

        int container1Height = appHeight -100;
        int container1Width = pageWidth -100;

        JPanel container1 = new JPanel(new BorderLayout());

        // Panel for the sync options
        JPanel syncPanel = new JPanel();
        syncPanel.setLayout(null);
        syncPanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

        JLabel syncText = new JLabel();
        syncText.setText("Connections");
        syncText.setFont(FontState.getFont(2));
        syncText.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        syncPanel.add(syncText);

        JLabel syncdev = new JLabel();
        syncdev.setText("Synchronised devices");
        syncdev.setFont(FontState.getFont(1));
        syncdev.setBounds(50,60,(int) (container1Width-container1Width/2),50);
        syncPanel.add(syncdev);


        connectedDevicesTable = createDeviceTable(DataController.getConnectedDevices());
        connectedDevicesTable.setBounds(50,100,500,200);
        connectedDevicesTable.setBackground(tablecolor1);
        syncPanel.add(connectedDevicesTable);

        JLabel newcon = new JLabel();
        newcon.setText("New Connections");
        newcon.setFont(FontState.getFont(1));
        newcon.setBounds(50,310,(int) (container1Width-container1Width/2),50);
        syncPanel.add(newcon);

        MyButton scanButton = new MyButton("Scan");
        scanButton.setFont(FontState.getFont(1));
        scanButton.setBackground(tablecolor1);
        scanButton.setFocusable(false);
        scanButton.setBorder(new LineBorder(Color.BLACK));
        scanButton.setBounds(350,320,200,30);
        scanButton.setPressedBackgroundColor(buttonPressedcolor);
        syncPanel.add(scanButton);

        foundConnectionsTable = createDeviceTable(DataController.getLastFoundDevices());
        foundConnectionsTable.setBounds(50,355,500,150);
        foundConnectionsTable.setBackground(tablecolor2);
        syncPanel.add(foundConnectionsTable);
        syncPanel.add( new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);

        scanButton.addActionListener(event -> scanButtonHandler());

        // Container for Time and Path Panels
        JPanel container2 = new JPanel(new BorderLayout());
        container2.setPreferredSize(new Dimension(container1Width-(container1Width-container1Width/3),container1Height));


        int container2Height = appHeight -150;
        int container2Width = container1Width-(container1Width-container1Width/3);

        //
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(null);
        optionPanel.setPreferredSize(new Dimension(container2Width,(int) (container1Height/1.07)));


        //to be added here

        JLabel optionstxt = new JLabel();
        optionstxt.setText("Options");
        optionstxt.setFont(FontState.getFont(2));
        optionstxt.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        optionPanel.add(optionstxt);

        JLabel removetxt1 = new JLabel();
        removetxt1.setText("Remove selected device");
        removetxt1.setFont(FontState.getFont(1));
        removetxt1.setBounds(30,60,(int) (container1Width-container1Width/2),50);
        optionPanel.add(removetxt1);

        MyButton removeButton = new MyButton("Remove");
        removeButton.setFont(FontState.getFont(1));
        removeButton.setBackground(buttoncolor);
        removeButton.setFocusable(false);
        removeButton.setBorder(new LineBorder(Color.BLACK));
        removeButton.setBounds(50,115,160,30);
        removeButton.setPressedBackgroundColor(buttonPressedcolor);
        optionPanel.add(removeButton);

        removeButton.addActionListener(event -> removeButtonHandler());

//        JLabel removetxt2 = new JLabel();
//        removetxt2.setText("Remove selected device");
//        removetxt2.setFont(new Font( "Comic sans",Font.PLAIN,dimension+16 ));
//        removetxt2.setBounds(30,145,(int) (container1Width-container1Width/2),50);
//        OptionPanel.add(removetxt2);
//
//        MyButton Remove2 = new MyButton("Remove");
//        Remove2.setFont(new Font("Comic Sans",Font.PLAIN,dimension+18));
//        Remove2.setBackground(buttoncolorgreen);
//        Remove2.setFocusable(false);
//        Remove2.setBorder(new LineBorder(Color.BLACK));
//        Remove2.setBounds(50,190,160,30);
//        Remove2.setPressedBackgroundColor(buttonPressedcolor);
//        OptionPanel.add(Remove2);

        JLabel syncNewDevTXT = new JLabel();
        syncNewDevTXT.setText("Synchronize selected device");
        syncNewDevTXT.setFont(FontState.getFont(1));
        syncNewDevTXT.setBounds(30,310,(int) (container1Width-container1Width/2),50);
        optionPanel.add(syncNewDevTXT);

        MyButton syncNewDev = new MyButton("Sync");
        syncNewDev.setFont(FontState.getFont(1));
        syncNewDev.setBackground(buttoncolor);
        syncNewDev.setFocusable(false);
        syncNewDev.setBorder(new LineBorder(Color.BLACK));
        syncNewDev.setBounds(50,355,160,30);
        syncNewDev.setPressedBackgroundColor(buttonPressedcolor);
        syncNewDev.addActionListener(event -> syncButtonHandler());
        optionPanel.add(syncNewDev);

//        JLabel propSelDevTXT = new JLabel();
//        propSelDevTXT.setText("Remove selected device");
//        propSelDevTXT.setFont(new Font( "Comic sans",Font.PLAIN,dimension+16 ));
//        propSelDevTXT.setBounds(30,395,(int) (container1Width-container1Width/2),50);
//        OptionPanel.add(propSelDevTXT);

//        MyButton propSelDev = new MyButton("Proprieties");
//        propSelDev.setFont(new Font("Comic Sans",Font.PLAIN,dimension+18));
//        propSelDev.setBackground(buttoncolor);
//        propSelDev.setFocusable(false);
//        propSelDev.setBorder(new LineBorder(Color.BLACK));
//        propSelDev.setBounds(50,440,160,30);
//        propSelDev.setPressedBackgroundColor(buttonPressedcolor);
//        OptionPanel.add(propSelDev);

        optionPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width,(int) (container1Height/1.07));



        //Panel for "Choose path to synchronize to"


        container2.add(optionPanel, BorderLayout.NORTH);
        container1.add(syncPanel, BorderLayout.WEST);
        container1.add(container2, BorderLayout.EAST);
        this.add(container1, BorderLayout.CENTER);


    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
    public int getDimension() {
        return dimension;
    }

    public void setFontConPage(int dimension) {
        this.dimension = dimension;

    }

}



