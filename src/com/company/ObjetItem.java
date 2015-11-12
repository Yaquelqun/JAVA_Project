package com.company;

import com.intellij.util.ui.CheckBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class ObjetItem extends JPanel implements ActionListener{

    JTextArea nom;
    JTextArea prix;
    JCheckBox isTaken;
    JButton infosButton = new JButton("Details");
    JLabel imageItem;

    boolean taken;
    String dataName, dataPrice, mode,urlItem;
    int idButton;
    NavigationController navigationController;

    public ObjetItem(String nom, String prix, int idItem, Boolean taken, String urlItem, NavigationController navigationController) {
        this.urlItem = urlItem;
        this.navigationController = navigationController;
        dataName = nom;
        dataPrice = prix;
        idButton = idItem;
        this.taken = taken;
        this.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300,50));



        System.out.println("objet "+nom+" "+prix+" a ete créé");
    }

    public void setModeSearch(){
        mode = "search";
        this.nom = new JTextArea(dataName);
        this.prix = new JTextArea(dataPrice+"€");
        isTaken = new JCheckBox();
        isTaken.addActionListener(this);

        //urlPath = address of your picture on internet
        try {
        URL url = new URL(urlItem);
        BufferedImage c;

            c = ImageIO.read(url);
            if(c!=null) {
                imageItem = new JLabel(new ImageIcon(new ImageIcon(c).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                add(this.imageItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(this.nom);
        add(this.prix);
        add(this.isTaken);
    }

    public void setSelectSearch(){
        mode = "select";
        this.nom = new JTextArea(dataName);
        this.prix = new JTextArea(dataPrice+"€");
        isTaken = new JCheckBox();
        isTaken.addActionListener(this);
        infosButton.addActionListener(this);
        //urlPath = address of your picture on internet
        try {
            URL url = new URL(urlItem);
            BufferedImage c;

            c = ImageIO.read(url);
            imageItem = new JLabel(new ImageIcon(new ImageIcon(c).getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(this.imageItem);
        add(this.isTaken);
        add(this.nom);
        add(this.prix);
        add(this.infosButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == isTaken){
            if(isTaken.isValid()){
                if(mode.equals("search")){

                }
                if(mode.equals("select")){

                }
            }
            else{
                if(mode.equals("search")){

                }
                if(mode.equals("select")){

                }
            }
        }
        if(s == infosButton){

        }
    }
}
