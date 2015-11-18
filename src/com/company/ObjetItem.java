package com.company;

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
    JButton infosButton = new JButton();
    JLabel imageItem;
    AjoutItem ajoutItem;
    ItemCourse res;
    boolean taken, disable;
    String dataName, dataPrice, mode,urlItem;
    int idItem;
    NavigationController navigationController;

    //rechercheItems.get(i).getNom(),rechercheItems.get(i).getPrix(),rechercheItems.get(i).getIdItem(), rechercheItems.get(i).getTaken(),rechercheItems.get(i).getURL(),


    public ObjetItem(ItemCourse res, NavigationController navigationController) {
        this.res =res;
        this.urlItem = res.getURL();
        this.navigationController = navigationController;
        dataName = res.getNom();
        dataPrice = res.getPrix();
        this.idItem = res.getIdItem();
        this.taken = res.getTaken();
        this.disable = res.getDisable();
        this.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(275,70));
        System.out.println("objet "+dataName+" a ete créé");
    }

    public void setModeSearch(AjoutItem ajoutItem){
        this.ajoutItem = ajoutItem;
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

    public void setModeSelect(){
        mode = "select";
        this.nom = new JTextArea(dataName.substring(0,10)+"...");
        this.nom.setToolTipText(dataName);
        this.prix = new JTextArea(dataPrice+"€");
        isTaken = new JCheckBox();
        isTaken.addActionListener(this);
        navigationController.persoButton("DetailsButton.png",infosButton);
        infosButton.addActionListener(this);
        //urlPath = address of your picture on internet
        try {
            URL url = new URL(urlItem);
            BufferedImage c;

            c = ImageIO.read(url);
            imageItem = new JLabel(new ImageIcon(new ImageIcon(c).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
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
                    ajoutItem.getRechercheItems().remove(res);
                    res.setTaken(true);
                    ajoutItem.getRechercheItems().add(res);
                }
                if(mode.equals("select")){
                    disable = true;
                    navigationController.itemUpdated(disable, idItem);
                }
            }
            else{
                if(mode.equals("search")){
                    ajoutItem.getRechercheItems().remove(res);
                    res.setTaken(false);
                    ajoutItem.getRechercheItems().add(res);
                }
                if(mode.equals("select")){
                    disable = false;
                    navigationController.itemUpdated(disable, idItem);
                }
            }
        }
        if(s == infosButton){

        }
    }
}
