package com.SyncPage;

import com.misc.CustomTableModel;
import com.misc.DrawRoundRectangle;
import com.misc.color.ColorPalleteController;
import com.misc.color.ColorPalleteEnum;
import com.misc.color.MyButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class SyncPage extends JPanel implements ActionListener {

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");

    private static final Color tablecolor = Color.decode("#CCDDE2");

    private static int pageWidth, pageHeight;

    private final JPanel container1, container2;
    private final JPanel filesPanel;
    private final JPanel fileChoserPanel;
    private final JPanel buttonsPanel;

    private final MyButton fileChoserButton, syncButton, deleteButton, fetchButton, scanFiles;

    private JTable filesTable;

    private String fileChoserFile = new String();
    private JFormattedTextField fileChoserTextField;
    private JLabel fileChoserLabel;
    private int dimension;

    private int selectedRow= -1;

    public SyncPage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius, int dimension){
        super();

        this.dimension=dimension;
        this.setLayout(new BorderLayout());

        pageWidth = appWidth-menuWidth;
        pageHeight = appHeight;
        this.setPreferredSize(new Dimension(pageWidth,pageHeight));

        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-50)), BorderLayout.NORTH);
        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-89)), BorderLayout.SOUTH);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-50)), BorderLayout.WEST);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-50)), BorderLayout.EAST);

        int container1Height = pageHeight-100;
        int container1Width = pageWidth-100;

        container1 = new JPanel(new BorderLayout());

        ColorPalleteController controller = new ColorPalleteController();
        ColorPalleteEnum theme;
        theme = ColorPalleteEnum.LightThemeColorPalette;
        controller.setColorPallete(theme);
        // Panel for the sync options
        filesPanel = new JPanel();
        filesPanel.setLayout(null);
        filesPanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

        scanFiles = new MyButton("Scan for files");
        scanFiles.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        scanFiles.setBackground(buttoncolor);
        scanFiles.setFocusable(false);
        scanFiles.setBorder(new LineBorder(Color.BLACK));
        scanFiles.setBounds(30, 30,(int)(container1Width-container1Width/2.5)-60, 50);
        scanFiles.setPressedBackgroundColor(buttonPressedcolor);
        scanFiles.addActionListener(this);

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

        filesTable = createFilesTable(data);

        //filesTable.setBounds(50,50,600,200);


        JScrollPane tableScrollPane = new JScrollPane(filesTable);
        tableScrollPane.setBounds(30,100,(int)(container1Width-container1Width/2.5)-60,container1Height-200 );

        filesPanel.add(scanFiles);
        filesPanel.add(tableScrollPane);
        filesPanel.add( new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);


        // Container for buttons and Path Panels
        container2 = new JPanel(new BorderLayout());
        container2.setPreferredSize(new Dimension((int) (container1Width-(container1Width-container1Width/3)),container1Height));

        int container2Height = pageHeight-150;
        int container2Width = (int) (container1Width-(container1Width-container1Width/3));

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        buttonsPanel.setPreferredSize(new Dimension(container2Width,(int) (container2Height/1.5)));

        JLabel syncText = new JLabel();
        syncText.setText("Synchronize selected file");
        syncText.setFont(new Font( "Comic sans",Font.PLAIN,dimension+15 ));
        syncText.setBounds(50,20,(int) (container2Width-100),50);

        syncButton = new MyButton("Synchronize");
        syncButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        syncButton.setBackground(buttoncolor);
        syncButton.setFocusable(false);
        syncButton.setBorder(new LineBorder(Color.BLACK));
        syncButton.setBounds(50,70,(int) (container2Width-container2Width/2),50);
        syncButton.setPressedBackgroundColor(buttonPressedcolor);
        syncButton.addActionListener(this);

        buttonsPanel.add(syncText);
        buttonsPanel.add(syncButton);

        JLabel deleteText = new JLabel();
        deleteText.setText("Delete selected file");
        deleteText.setFont(new Font( "Comic sans",Font.PLAIN,dimension+15 ));
        deleteText.setBounds(50,120,(int) (container2Width-100),50);

        deleteButton = new MyButton("Delete");
        deleteButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        deleteButton.setBackground(buttoncolor);
        deleteButton.setFocusable(false);
        deleteButton.setBorder(new LineBorder(Color.BLACK));
        deleteButton.setBounds(50,170, container2Width-container2Width/2,50);
        deleteButton.setPressedBackgroundColor(buttonPressedcolor);
        deleteButton.addActionListener(this);

        buttonsPanel.add(deleteText);
        buttonsPanel.add(deleteButton);

        JLabel fetchText = new JLabel();
        fetchText.setText("Fetch selected file");
        fetchText.setFont(new Font( "Comic sans",Font.PLAIN,dimension+15 ));
        fetchText.setBounds(50,220,(int) (container2Width-100),50);


        fetchButton = new MyButton("Fetch File");
        fetchButton.setFont(new Font("Comic Sans",Font.PLAIN,dimension+15));
        fetchButton.setBackground(buttoncolor);
        fetchButton.setFocusable(false);
        fetchButton.setBorder(new LineBorder(Color.BLACK));
        fetchButton.setBounds(50, 270,container2Width-container2Width/2,50);
        fetchButton.setPressedBackgroundColor(buttonPressedcolor);
        fetchButton.addActionListener(this);

        buttonsPanel.add(fetchText);
        buttonsPanel.add(fetchButton);

        buttonsPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width,(int) (container2Height/1.5));

        //Panel for "Choose path to synchronize to"
        fileChoserPanel = new JPanel();
        fileChoserPanel.setLayout(null);
        fileChoserPanel.setPreferredSize(new Dimension(container2Width, (int) (container2Height/3.5)));

        fileChoserTextField = new JFormattedTextField();
        fileChoserTextField.setBackground(textFieldColor);
        fileChoserTextField.setFocusable(true);   /// daca se seteaza pe true -- pentru a putea fi editabil -- apare bug la butoanele din meniu cand se dau hover
                    /// de asemenea apare bug ul dupa ce se selecteaza fisier
        fileChoserTextField.setBounds(20,100,250,30);


        fileChoserButton = new MyButton("Browse directory");
        fileChoserButton.setFont(new Font( "Comic sans",Font.PLAIN,dimension+15 ));
        fileChoserButton.setBackground(buttoncolor);
        fileChoserButton.setFocusable(false);
        fileChoserButton.setBounds(20,60,250,30);
        fileChoserButton.setBorder(new LineBorder(Color.BLACK));
        fileChoserButton.setPressedBackgroundColor(buttonPressedcolor);
        fileChoserButton.addActionListener(this);

        fileChoserLabel = new JLabel();
        fileChoserLabel.setText("Choose directory to synchronize files");
        fileChoserLabel.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        fileChoserLabel.setBounds(10,20,300,25);


        fileChoserPanel.add(fileChoserTextField);
        fileChoserPanel.add(fileChoserButton);
        fileChoserPanel.add(fileChoserLabel);

        fileChoserPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width, (int) (container2Height/3.5));

        container2.add(buttonsPanel, BorderLayout.NORTH);
        container2.add(fileChoserPanel, BorderLayout.SOUTH);

        container1.add(filesPanel, BorderLayout.WEST);
        container1.add(container2, BorderLayout.EAST);

        this.add(container1, BorderLayout.CENTER);


    }


    public JTable createFilesTable(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Name");
        model.addColumn("Type");
        model.addColumn("Size");
        model.addColumn("Extension");
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
                    selectedRow = evmodel.getMinSelectionIndex();
                }
            }
        });

        return table;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChoserButton) {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setAcceptAllFileFilterUsed(false);
            if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fileChoserFile = String.valueOf(filechooser.getSelectedFile());
                fileChoserTextField.setText(fileChoserFile);
            }
        } else if (e.getSource() == syncButton) {
            if(selectedRow < 0){
                JOptionPane.showMessageDialog(null, "Please select a file from the table to synchronize", "Please select a file", JOptionPane.ERROR_MESSAGE);
            }
            else {
                //TODO SYNC files function
            }
        } else if (e.getSource() == deleteButton) {
            if(selectedRow < 0){
                JOptionPane.showMessageDialog(null, "Please select a file from the table to delete", "Please select a file", JOptionPane.ERROR_MESSAGE);
            }
            else {
                //TODO DELETE files function
            }
        } else if (e.getSource() == fetchButton) {
            if(selectedRow < 0){
                JOptionPane.showMessageDialog(null, "Please select a file from the table to fetch", "Please select a file", JOptionPane.ERROR_MESSAGE);
            }
            else {
                //TODO DELETE files function
            }
        } else if (e.getSource() == scanFiles) {
            //TODO FETCH -reload files- search for changes
        }
    }
    public int getFontSize(){
        return dimension;
    }
    public void setFontSyncPage(int dimension)
    {
        this.dimension=dimension;
    }

}



