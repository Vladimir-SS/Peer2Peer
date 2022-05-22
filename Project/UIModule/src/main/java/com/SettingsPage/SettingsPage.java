package com.SettingsPage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPage extends JPanel implements ActionListener {

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
    //private final JPanel OptionPanel;

    private String userN="LaptopD406";
    private int dimension=5;
    private int theme=1;

    private com.SettingsPage.MyButton disconnect;

    private void setUserN(String text)
    {
        userN=text;
    }
    private void setDimension(int numar)
    {
        dimension=numar*5;
    }
    private void setTheme(int number)
    {
        theme=number;
    }

    public SettingsPage( int menuWidth, int menuHeight, int appWidth, int appHeight, int radius){
        super();

        this.setLayout(new BorderLayout());

        pageWidth = appWidth-menuWidth;
        pageHeight = appHeight;
        this.setPreferredSize(new Dimension(pageWidth,pageHeight));

        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-50)), BorderLayout.NORTH);
        this.add( Box.createVerticalStrut(pageHeight-(pageHeight-50)), BorderLayout.SOUTH);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-250)), BorderLayout.WEST);
        this.add( Box.createHorizontalStrut(pageWidth-(pageWidth-50)), BorderLayout.EAST);

        int container1Height = pageHeight-100;
        int container1Width = pageWidth-100;

        container1 = new JPanel(new BorderLayout());

        // Panel for the sync options
        syncPanel = new JPanel();
        syncPanel.setLayout(null);
        syncPanel.setPreferredSize(new Dimension((int) (container1Width-container1Width/2.5),container1Height));

        JLabel settingText = new JLabel();
        settingText.setText("Settings");
        settingText.setFont(new Font( "Comic sans",Font.PLAIN,28 ));
        settingText.setBounds(50,20,(int) (container1Width-container1Width/2),50);
        syncPanel.add(settingText);

        JLabel username = new JLabel();
        username.setText("Your device:");
        username.setFont(new Font( "Comic sans",Font.PLAIN, 20));
        username.setBounds(400,20,(int) (container1Width-container1Width/2),50);
        syncPanel.add(username);


        JLabel user = new JLabel();


        user.setText(userN);
        user.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        user.setBounds(410,45,(int) (container1Width-container1Width/2),50);
        syncPanel.add(user);

        JLabel profile = new JLabel();
        profile.setText("Profile settings --------------------------------------------------");
        profile.setForeground(buttonPressedcolor);
        profile.setFont(new Font( "Comic sans",Font.PLAIN,20 ));
        profile.setBounds(50,80,(int) (container1Width-container1Width/2),50);
        syncPanel.add(profile);

        JLabel changeN = new JLabel();
        changeN.setText("Rename Device: ");
        changeN.setFont(new Font( "Comic sans",Font.PLAIN,26 ));
        changeN.setBounds(50,120,(int) (container1Width-container1Width/2),50);
        syncPanel.add(changeN);

        JTextField inputUML = new JTextField("",20);
        inputUML.setText(userN);
        inputUML.setFont(new Font( "Comic sans",Font.PLAIN,18 ));
        inputUML.setForeground(buttonPressedcolor);
        inputUML.setBounds(300,130,250,30);
        inputUML.setBackground(textFieldColor);

        inputUML.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){

                userN = inputUML.getText(); //perform your operation

                if (userN.length()<=30)
                    user.setText(userN);
                else
                    JOptionPane.showMessageDialog(syncPanel.getComponent(0), "Set a name for your user smaller than 30 characters");



            }
        });
        syncPanel.add(inputUML,BorderLayout.CENTER);

        JLabel history = new JLabel();
        history.setText("Acces history: ");
        history.setFont(new Font( "Comic sans",Font.PLAIN,26 ));
        history.setBounds(50,180,(int) (container1Width-container1Width/2),50);
        syncPanel.add(history);

        com.SettingsPage.MyButton Remove1 = new com.SettingsPage.MyButton("History");
        Remove1.setFont(new Font("Comic Sans",Font.PLAIN,18));
        Remove1.setBackground(buttoncolor);
        Remove1.setFocusable(false);
        Remove1.setBorder(new LineBorder(Color.BLACK));
        Remove1.setBounds(390,190,160,30);
        Remove1.setPressedBackgroundColor(buttonPressedcolor);
        syncPanel.add(Remove1);

        JLabel design = new JLabel();
        design.setText("Design settings --------------------------------------------------");
        design.setForeground(buttonPressedcolor);
        design.setFont(new Font( "Comic sans",Font.PLAIN,20 ));
        design.setBounds(50,230,(int) (container1Width-container1Width/2),50);
        syncPanel.add(design);

        JLabel theme = new JLabel();
        theme.setText("Light theme ");
        theme.setFont(new Font( "Comic sans",Font.PLAIN,26 ));
        theme.setBounds(70,280,(int) (container1Width-container1Width/2),50);
        syncPanel.add(theme);
        com.SettingsPage.MyButton font= new com.SettingsPage.MyButton("");
        font.setFont(new Font("Comic Sans",Font.PLAIN,18));
        font.setBackground(buttoncolor);
        font.setFocusable(false);
        font.setBorder(new LineBorder(Color.BLACK));
        font.setBounds(110,330,60,30);
        font.setPressedBackgroundColor(buttonPressedcolor);
        syncPanel.add(font);


        JLabel size = new JLabel();
        size.setText("Font dimension ");
        size.setFont(new Font( "Comic sans",Font.PLAIN,26 ));
        size.setBounds(50,390,(int) (container1Width-container1Width/2),50);
        syncPanel.add(size);

        String data[][]={
                {"100%","200%","300%"}
        };
        String column[]={"Font1","Font2","Font3"};
        final JTable sizeFont=new JTable(data,column);
        sizeFont.setFont(new Font( "Courier New",Font.BOLD,20 ));
        sizeFont.setBounds(300,400,200,40);
        sizeFont.setRowHeight(50);
        sizeFont.setBackground(tablecolor1);
        sizeFont.setRowSelectionAllowed(false);

        sizeFont.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        syncPanel.add(sizeFont);

        sizeFont.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    JTable target = (JTable)e.getSource();
                    int column = target.getSelectedColumn();

                    setDimension(column);
                    settingText.setFont(new Font( "Comic sans",Font.PLAIN,dimension + 28 ));
                    username.setFont(new Font( "Comic Sans",Font.PLAIN,dimension +20 ));
                    user.setFont(new Font( "Comic sans",Font.PLAIN,dimension + 18 ));
                    profile.setFont(new Font( "Comic sans",Font.PLAIN,dimension+ 20 ));
                    changeN.setFont(new Font( "Comic sans",Font.PLAIN,dimension+23 ));
                    history.setFont(new Font( "Comic sans",Font.PLAIN,dimension + 23 ));
                    design.setFont(new Font( "Comic sans",Font.PLAIN,dimension+ 20 ));
                    theme.setFont(new Font( "Comic sans",Font.PLAIN,dimension + 26 ));
                    size.setFont(new Font( "Comic sans",Font.PLAIN,dimension + 23 ));
                }
            }
        });

        disconnect = new com.SettingsPage.MyButton("DISCONNECT");
        disconnect.setFont(new Font("Comic Sans",Font.PLAIN,20));
        disconnect.setBackground(buttoncolorgreen);
        disconnect.setFocusable(false);
        disconnect.setBorder(new LineBorder(Color.BLACK));
        disconnect.setBounds(210,500,160,40);
        disconnect.setPressedBackgroundColor(buttonPressedcolor);
        syncPanel.add(disconnect);

        /*
        disconnect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                System.exit(0);

            }
        });

         */




        syncPanel.add( new com.SettingsPage.DrawRoundRectangle(radius)).setBounds(0,0,(int) (container1Width-container1Width/2.5),container1Height-39);


        // Container for Time and Path Panels
        container2 = new JPanel(new BorderLayout());
        container2.setPreferredSize(new Dimension((int) (container1Width-(container1Width-container1Width/3)),container1Height));


        int container2Height = pageHeight-150;
        int container2Width = (int) (container1Width-(container1Width-container1Width/3));



        container1.add(syncPanel, BorderLayout.WEST);
        // container1.add(container2, BorderLayout.EAST);

        this.add(container1, BorderLayout.CENTER);


    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
    public int getFontSize(){
        return dimension;
    }
    public void setFontSize(int dimension)
    {
        this.dimension=dimension;
    }
    public JButton getDisconnectButton(){
        return disconnect;
    }

}



