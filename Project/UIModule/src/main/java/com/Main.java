package com;


import com.MainFrame.MainFrame;


import javax.swing.*;


public class Main {

    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            new MainFrame();
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
