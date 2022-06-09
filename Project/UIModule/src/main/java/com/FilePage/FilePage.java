package com.FilePage;

import com.misc.CustomTableModel;
import com.misc.DataController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FilePage extends JPanel {
    private int dimension=5;

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");
    private static final Color tablecolor = Color.decode("#CCDDE2");

    private static int pageWidth, pageHeight;

    private JPanel container1, container2;

    private JTable connectedDevicesTable;
    private JTable foundConnectionsTable;



    public FilePage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius, int dimension){
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
        JPanel filePagePanel = new JPanel();
        filePagePanel.setLayout(null);
        filePagePanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

        List<String[]> data = IntStream.range(0, 20).map(new[]);


        filesTable = createFilesTable(data);

        JScrollPane tableScrollPane = new JScrollPane(filesTable);
        tableScrollPane.setBounds(30,100,(int)(container1Width-container1Width/2.5)-60,container1Height-200 );

        filePagePanel.add(tableScrollPane);
        filePagePanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);
        //JLabel syncText = new JLabel();
        //syncText.setText("Connections");
        //syncText.setFont(new Font( "Comic sans",Font.PLAIN,dimension+28 ));
        //syncText.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        //syncPanel.add(syncText);

        //JLabel syncdev = new JLabel();
        //syncdev.setText("Synchronised devices");
        //syncdev.setFont(new Font( "Comic sans",Font.PLAIN,dimension+18 ));
        //syncdev.setBounds(50,60,(int) (container1Width-container1Width/2),50);
        //syncPanel.add(syncdev);


        connectedDevicesTable = createDeviceTable(DataController.getConnectedDevices());
        connectedDevicesTable.setBounds(50,100,500,200);
        connectedDevicesTable.setBackground(tablecolor1);
        syncPanel.add(connectedDevicesTable);

        //JLabel newcon = new JLabel();
        //newcon.setText("New Connections");
        //newcon.setFont(new Font( "Comic sans",Font.BOLD,dimension+20 ));
        //newcon.setBounds(50,310,(int) (container1Width-container1Width/2),50);
        //syncPanel.add(newcon);

        //MyButton scanButton = new MyButton("Scan");
        //scanButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+ 18));
        //scanButton.setBackground(tablecolor1);
        //scanButton.setFocusable(false);
        //scanButton.setBorder(new LineBorder(Color.BLACK));
        //scanButton.setBounds(350,320,200,30);
        //scanButton.setPressedBackgroundColor(buttonPressedcolor);
        //syncPanel.add(scanButton);

        //foundConnectionsTable = createDeviceTable(DataController.getLastFoundDevices());
        //foundConnectionsTable.setBounds(50,355,500,150);
        //foundConnectionsTable.setBackground(tablecolor2);
        //syncPanel.add(foundConnectionsTable);
        //syncPanel.add( new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);

        //scanButton.addActionListener(event -> scanButtonHandler());

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
        optionstxt.setFont(new Font( "Comic sans",Font.PLAIN,dimension+28 ));
        optionstxt.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        optionPanel.add(optionstxt);

        JLabel removetxt1 = new JLabel();
        removetxt1.setText("Remove selected device");
        removetxt1.setFont(new Font( "Comic sans",Font.PLAIN,dimension+16 ));
        removetxt1.setBounds(30,60,(int) (container1Width-container1Width/2),50);
        optionPanel.add(removetxt1);

        MyButton removeButton = new MyButton("Remove");
        removeButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+18));
        removeButton.setBackground(buttoncolor);
        removeButton.setFocusable(false);
        removeButton.setBorder(new LineBorder(Color.BLACK));
        removeButton.setBounds(50,115,160,30);
        removeButton.setPressedBackgroundColor(buttonPressedcolor);
        optionPanel.add(removeButton);

        removeButton.addActionListener(event -> removeButtonHandler());

        JLabel syncNewDevTXT = new JLabel();
        syncNewDevTXT.setText("Synchronize selected device");
        syncNewDevTXT.setFont(new Font( "Comic sans",Font.PLAIN,dimension+16 ));
        syncNewDevTXT.setBounds(30,310,(int) (container1Width-container1Width/2),50);
        optionPanel.add(syncNewDevTXT);

        MyButton syncNewDev = new MyButton("Sync");
        syncNewDev.setFont(new Font("Comic Sans",Font.PLAIN,dimension+18));
        syncNewDev.setBackground(buttoncolor);
        syncNewDev.setFocusable(false);
        syncNewDev.setBorder(new LineBorder(Color.BLACK));
        syncNewDev.setBounds(50,355,160,30);
        syncNewDev.setPressedBackgroundColor(buttonPressedcolor);
        syncNewDev.addActionListener(event -> syncButtonHandler());
        optionPanel.add(syncNewDev);

        optionPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width,(int) (container1Height/1.07));



        //Panel for "Choose path to synchronize to"


        container2.add(optionPanel, BorderLayout.NORTH);
        container1.add(syncPanel, BorderLayout.WEST);
        container1.add(container2, BorderLayout.EAST);
        this.add(container1, BorderLayout.CENTER);//////////////////////////////////////////////////////////////////////////////////////

        //Make the x coordinate smaller so that it fits better in the menu
        int surplusX=200;



        /*
        JButton connections = new JButton("Connections");
        connections.setBounds(25,205,150,50);
        connections.setFocusable(false);
        connections.setBackground(new Color(204,221,226,255));

        JButton Files = new JButton("Files");
        Files.setBounds(25,275,150,50);
        Files.setFocusable(false);
        Files.setBackground(new Color(204,221,226,255));

        JButton Synchronisation = new JButton("Synchronisation");
        Synchronisation.setBounds(25,345,150,50);
        Synchronisation.setFocusable(false);
        Synchronisation.setBackground(new Color(204,221,226,255));

        JButton Settings = new JButton("Settings");
        Settings.setBounds(25,415,150,50);
        Settings.setFocusable(false);
        Settings.setBackground(new Color(204,221,226,255));

         */

        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon("pistrui.png");
        label.setIcon(image);
        label.setBounds(0,0,1280,720);

        /*
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(105,162,184,255));
        menuPanel.setBounds(0,0,200,720);

         */

        JPanel filePanel = new JPanel();
        filePanel.setBackground(new Color(204,221,226,255));
        filePanel.setBounds(350-surplusX,90,150,500);
        filePanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));

        JPanel pathPanel = new JPanel();
        pathPanel.setBackground(new Color(204,221,226,255));
        pathPanel.setBounds(350-surplusX,90,750,100);
        pathPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));

        JLabel pathLabel = new JLabel("Path:");
        pathLabel.setFont(new Font("Verdana",1,dimension+20));
        pathPanel.add(pathLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(105,162,184,255));
        mainPanel.setBounds(350-surplusX,90,750,500);
        mainPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));

        JButton sync = new JButton("Sync");
        sync.setBounds(375-surplusX,240,100,50);
        sync.setFont(new Font("Comic Sans",Font.PLAIN,dimension+14));
        sync.setFocusable(false);
        sync.setBackground(new Color(212,186,158,255));

        JButton newButton = new JButton("New");
        newButton.setBounds(375-surplusX,310,100,50);
        newButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+14));
        newButton.setFocusable(false);
        newButton.setBackground(new Color(212,186,158,255));

        JButton noBackup = new JButton("No Backup");
        noBackup.setBounds(375-surplusX,380,100,50);
        noBackup.setFont(new Font("Comic Sans",Font.PLAIN,dimension+6));
        noBackup.setFocusable(false);
        noBackup.setBackground(new Color(212,186,158,255));

        //Add elements
        this.setLayout( null );
        this.add(sync);
        this.add(newButton);
        this.add(noBackup);
        this.add(filePanel);
        this.add(pathPanel);
        this.add(mainPanel);
        this.add(label);
    }
    //functia scanButtonHandler trb modificata
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
        model.addColumn("Name/Path");
        model.addColumn("Time");
        model.addColumn("Origin Device/Last Fetch");
        model.addColumn("Type");
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


    public int getDimension() {
        return dimension;
    }

    public void setFontFilePage(int dimension) {
        this.dimension = dimension;
    }
}
