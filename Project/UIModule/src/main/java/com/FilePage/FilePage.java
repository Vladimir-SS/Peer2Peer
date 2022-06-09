package com.FilePage;

import com.SyncPage.SyncPage;
import com.menu.MainMenu;
import com.misc.DrawRoundRectangle;
import com.misc.color.MyButton;
import com.misc.CustomTableModel;
import com.misc.DataController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FilePage extends JPanel {
    private  MainMenu menuPanelFile;
    private int dimension=5;

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");
    private static final Color tablecolor = Color.decode("#CCDDE2");

    private static int pageWidth, pageHeight;

    private JPanel container, panelForSplit1, panelSyncedFiles, leftButtonsPannel, filesContainer, rightTablesPanel, panelNewFiles, panelNoBackupFiles;

    private JTable connectedDevicesTable;
    private JTable foundConnectionsTable;

    private JTable newFilesTable, noBackupFilesTable, syncedFilesTable;



    public FilePage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius, int dimension){
        super();
        this.dimension=dimension;
        this.setLayout(new BorderLayout());

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});
        data.add(new String[]{"Fisier1", "Fisier", "25MB"});



        int pageWidth = appWidth - menuWidth;
        this.setPreferredSize(new Dimension(pageWidth, appHeight));

        this.add( Box.createVerticalStrut(appHeight -(appHeight-50)), BorderLayout.NORTH);
        this.add( Box.createVerticalStrut(appHeight -(appHeight-89)), BorderLayout.SOUTH);
        this.add( Box.createHorizontalStrut(pageWidth -(pageWidth-50)), BorderLayout.WEST);
        this.add( Box.createHorizontalStrut(pageWidth -(pageWidth-50)), BorderLayout.EAST);
        //this.setBackground(Color.red);

        container = new JPanel(new BorderLayout());
       // container.setBackground(Color.blue);

        leftButtonsPannel=new JPanel(new CardLayout());
        leftButtonsPannel.setBackground( Color.decode( "#69A2B8" ) );

        container.setPreferredSize(new Dimension(400, 800));

        panelForSplit1 = new JPanel(new BorderLayout());
      //  panelForSplit1.setPreferredSize(new Dimension(100, 100));

        List<String> listButtonsNameFiles = new ArrayList<>();
        listButtonsNameFiles.add( "Synchronized" );
        listButtonsNameFiles.add( "New files" );
        listButtonsNameFiles.add( "No Backup" );

        menuPanelFile = new MainMenu( 230 , 580 , 30 , listButtonsNameFiles );
        panelForSplit1.add(menuPanelFile, BorderLayout.CENTER);

        syncedFilesTable = createSyncedFilesTable(data);
        newFilesTable = createNewFilesTable(data);
        noBackupFilesTable = createNoBackupFilesTable(data);

        
        panelSyncedFiles = new JPanel(new BorderLayout());
        JPanel buttonPannel= new JPanel(null);
        buttonPannel.setBackground(panelcolor);

        JPanel buttonPannel2= new JPanel(null);
        buttonPannel2.setBackground(panelcolor);

        JPanel buttonPannel3= new JPanel(null);
        buttonPannel3.setBackground(panelcolor);

        MyButton syncButton = new MyButton("Example button if needed");
        syncButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        syncButton.setBackground(buttoncolor);
        syncButton.setFocusable(false);
        syncButton.setBorder(new LineBorder(Color.BLACK));
        syncButton.setBounds(2,2, 200,50);
        syncButton.setPressedBackgroundColor(buttonPressedcolor);
        //syncButton.addActionListener((ActionListener) this);

        MyButton syncButton2 = new MyButton("Example button if needed");
        syncButton2.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        syncButton2.setBackground(buttoncolor);
        syncButton2.setFocusable(false);
        syncButton2.setBorder(new LineBorder(Color.BLACK));
        syncButton2.setBounds(2,2, 200,50);
        syncButton2.setPressedBackgroundColor(buttonPressedcolor);
        //syncButton2.addActionListener((ActionListener) this);

        MyButton syncButton3 = new MyButton("Example button if needed");
        syncButton3.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        syncButton3.setBackground(buttoncolor);
        syncButton3.setFocusable(false);
        syncButton3.setBorder(new LineBorder(Color.BLACK));
        syncButton3.setBounds(2,2, 200,50);
        syncButton3.setPressedBackgroundColor(buttonPressedcolor);
        //syncButton3.addActionListener((ActionListener) this);

        buttonPannel.add(syncButton);
        buttonPannel2.add(syncButton2);
        buttonPannel3.add(syncButton3);

        panelSyncedFiles.add(buttonPannel, BorderLayout.CENTER);
        JScrollPane tableScrollPane1 = new JScrollPane(syncedFilesTable);
        panelSyncedFiles.add(tableScrollPane1, BorderLayout.SOUTH);

        panelNewFiles = new JPanel(new BorderLayout());
        panelNewFiles.add(buttonPannel2, BorderLayout.CENTER);
        JScrollPane tableScrollPane2 = new JScrollPane(newFilesTable);
        panelNewFiles.add(tableScrollPane2, BorderLayout.SOUTH);

        panelNoBackupFiles = new JPanel(new BorderLayout());
        panelNoBackupFiles.add(buttonPannel3, BorderLayout.CENTER);
        JScrollPane tableScrollPane3 = new JScrollPane(noBackupFilesTable);
        panelNoBackupFiles.add(tableScrollPane3, BorderLayout.SOUTH);

        JPanel startingPanel = new JPanel(null);
        startingPanel.setBackground(panelcolor);

        rightTablesPanel = new JPanel(new CardLayout());

        rightTablesPanel.add(startingPanel);
        rightTablesPanel.add(panelSyncedFiles, "sync");
        rightTablesPanel.add(panelNewFiles, "new");
        rightTablesPanel.add(panelNoBackupFiles, "nobackup");

        menuPanelFile.getButtonWithName("Synchronized").addActionListener(this::whenSyncButtonPressed);
        menuPanelFile.getButtonWithName("New files").addActionListener(this::whenNewFilesButtonPressed);
        menuPanelFile.getButtonWithName("No Backup").addActionListener(this::whenNoBackupButtonPressed);

        container.add(panelForSplit1, BorderLayout.WEST);
        container.add(rightTablesPanel, BorderLayout.CENTER);


        this.add(container).setBounds( 65, 60, 950, 550);
        this.add(new DrawRoundRectangle(radius), BorderLayout.CENTER);

        /*
        // Panel for the sync options
        JPanel filePagePanel = new JPanel();
        filePagePanel.setLayout(null);
        filePagePanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

       // List<String[]> data = IntStream.range(0, 20).map(new[]);



        this.add(container1, BorderLayout.CENTER);//////////////////////////////////////////////////////////////////////////////////////

        //Make the x coordinate smaller so that it fits better in the menu
        int surplusX=200;


        JPanel filePanel = new JPanel();
        filePanel.setBackground(tablecolor);
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
        mainPanel.setBackground(panelcolor);
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
       // this.add(label);

         */
    }
    private void whenSyncButtonPressed( ActionEvent e ) {
        //rightTablesPanel.add(panelSyncedFiles);
        CardLayout cl = (CardLayout)(rightTablesPanel.getLayout());
        cl.show(rightTablesPanel, "sync");
    }
    private void whenNewFilesButtonPressed( ActionEvent e ) {
        //rightTablesPanel.add(panelNewFiles);
        CardLayout cl = (CardLayout)(rightTablesPanel.getLayout());
        cl.show(rightTablesPanel, "new");
    }
    private void whenNoBackupButtonPressed( ActionEvent e ) {
       // rightTablesPanel.add(panelNoBackupFiles);
        CardLayout cl = (CardLayout)(rightTablesPanel.getLayout());
        cl.show(rightTablesPanel, "nobackup");

    }

    public JTable createSyncedFilesTable(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Name");
        model.addColumn("Time");
        model.addColumn("Origin Device");
        model.addColumn("Type");
        data.forEach(model::addRow);

        final JTable table = new JTable(model);
        table.setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.getTableHeader().setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.setBackground(tablecolor);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(25);


        ListSelectionModel evmodel = table.getSelectionModel();
        evmodel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!evmodel.isSelectionEmpty()){
                   // selectedRow = evmodel.getMinSelectionIndex();
                }
            }
        });

        return table;
    }

    public JTable createNewFilesTable(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Name");
        model.addColumn("Size");
        model.addColumn("Origin Device");
        model.addColumn("Type");
        data.forEach(model::addRow);

        final JTable table = new JTable(model);
        table.setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.getTableHeader().setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.setBackground(tablecolor);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(25);


        ListSelectionModel evmodel = table.getSelectionModel();
        evmodel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!evmodel.isSelectionEmpty()){
                   // selectedRow = evmodel.getMinSelectionIndex();
                }
            }
        });

        return table;
    }

    public JTable createNoBackupFilesTable(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Name");
        model.addColumn("SizeDif");
        model.addColumn("Origin Device");
        model.addColumn("Type");
        data.forEach(model::addRow);

        final JTable table = new JTable(model);
        table.setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.getTableHeader().setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.setBackground(tablecolor);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(25);


        ListSelectionModel evmodel = table.getSelectionModel();
        evmodel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!evmodel.isSelectionEmpty()){
                //    selectedRow = evmodel.getMinSelectionIndex();
                }
            }
        });

        return table;
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
