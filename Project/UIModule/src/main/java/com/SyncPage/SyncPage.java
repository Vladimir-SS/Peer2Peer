package com.SyncPage;

import com.misc.FontState;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static SyncFiles.SyncFiles.Sync;

public class SyncPage extends JPanel implements ActionListener {

    private static final Color panelcolor = Color.decode("#69A2B8");
    private static final Color buttoncolor = Color.decode("#DC965A");
    private static final Color buttonPressedcolor = Color.decode("#D4BA9E");
    private static final Color textFieldColor = Color.decode("#CCDDE2");

    private static int pageWidth, pageHeight;

    private final JPanel container1, container2;
    private final JPanel syncPanel;
    private final JPanel fileChoserPanel;
    private final JPanel setTimePanel;

    private final MyButton fileChoserButton, syncButton ;

    private String fileChoserFile = new String();
    private JFormattedTextField fileChoserTextField;
    private JLabel fileChoserLabel;
    private int dimension;

    public SyncPage(int menuWidth, int menuHeight, int appWidth, int appHeight, int radius, int dimension){
        super();

        this.dimension=dimension;
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
        syncText.setText("Synchronize all files");
        syncText.setFont(FontState.getFont(3));
        syncText.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        syncPanel.add(syncText);

        syncButton = new MyButton("Synchronize files");
        syncButton.setFont(FontState.getFont(3));
        syncButton.setBackground(buttoncolor);
        syncButton.setFocusable(false);
        syncButton.setBorder(new LineBorder(Color.BLACK));
        syncButton.setBounds(50,85,(int) (container1Width-container1Width/2),50);
        syncButton.setPressedBackgroundColor(buttonPressedcolor);
        syncButton.addActionListener(this);
        syncPanel.add(syncButton);

        syncPanel.add( new DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);


        // Container for Time and Path Panels
        container2 = new JPanel(new BorderLayout());
        container2.setPreferredSize(new Dimension((int) (container1Width-(container1Width-container1Width/3)),container1Height));


        int container2Height = pageHeight-150;
        int container2Width = (int) (container1Width-(container1Width-container1Width/3));

        ///Panel for time setting - select when to sync (future)0
        setTimePanel = new JPanel();
        setTimePanel.setLayout(null);
        setTimePanel.setPreferredSize(new Dimension(container2Width,(int) (container2Height/1.5)));
        //to be added here
        setTimePanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width,(int) (container2Height/1.5));

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
        fileChoserButton.setFont(FontState.getFont(1));
        fileChoserButton.setBackground(buttoncolor);
        fileChoserButton.setFocusable(false);
        fileChoserButton.setBounds(20,60,250,30);
        fileChoserButton.setBorder(new LineBorder(Color.BLACK));
        fileChoserButton.setPressedBackgroundColor(buttonPressedcolor);
        fileChoserButton.addActionListener(this);

        fileChoserLabel = new JLabel();
        fileChoserLabel.setText("Choose directory to synchronize files");
        fileChoserLabel.setFont(FontState.getFont(1));
        fileChoserLabel.setBounds(10,20,300,25);


        fileChoserPanel.add(fileChoserTextField);
        fileChoserPanel.add(fileChoserButton);
        fileChoserPanel.add(fileChoserLabel);

        fileChoserPanel.add(new DrawRoundRectangle(radius)).setBounds(0,0,container2Width, (int) (container2Height/3.5));

        container2.add(setTimePanel, BorderLayout.NORTH);
        container2.add(fileChoserPanel, BorderLayout.SOUTH);

        container1.add(syncPanel, BorderLayout.WEST);
        container1.add(container2, BorderLayout.EAST);

        this.add(container1, BorderLayout.CENTER);


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
        }

        if(e.getSource() == syncButton)
        {
            try {
                Sync();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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



