package com.FilePage;

import com.misc.FontState;

import javax.swing.*;
import java.awt.*;

public class FilePage extends JPanel {
    private int dimension=5;

    public FilePage(int width,int height, int dimension){

        this.setPreferredSize( new Dimension(width,height) );

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
        sync.setFont(FontState.getFont(0));
        sync.setFocusable(false);
        sync.setBackground(new Color(212,186,158,255));

        JButton newButton = new JButton("New");
        newButton.setBounds(375-surplusX,310,100,50);
        newButton.setFont(FontState.getFont(0));
        newButton.setFocusable(false);
        newButton.setBackground(new Color(212,186,158,255));

        JButton noBackup = new JButton("No Backup");
        noBackup.setBounds(375-surplusX,380,100,50);
        noBackup.setFont(FontState.getFont(5);
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


    public int getDimension() {
        return dimension;
    }

    public void setFontFilePage(int dimension) {
        this.dimension = dimension;
    }
}
