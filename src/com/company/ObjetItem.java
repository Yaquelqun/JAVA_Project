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

    JLabel nom;
    JLabel prix;
    JCheckBox isTaken;
    JButton infosButton = new JButton();
    JLabel imageItem;
    JLabel chosenLabel;
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
        this.nom = new JLabel(dataName);
        this.prix = new JLabel(dataPrice+"€");
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
        //add(this.prix);
        add(this.isTaken);
    }

    public void setModeSelect(){
        mode = "select";
        if(dataName.length()>10) {
            this.nom = new JLabel(dataName.substring(0, 10) + "...");
        }
        else
        {
            this.nom = new JLabel(dataName);
        }
        navigationController.persoLabel(nom,Client.BACKGROUND_INV_COLOR);
        this.nom.setToolTipText(dataName);
        this.prix = new JLabel(dataPrice+"€");
        navigationController.persoLabel(prix,Client.BACKGROUND_INV_COLOR);
        setBackground(Client.BACKGROUND_INV_COLOR);
        isTaken = new JCheckBox();
        isTaken.setBackground(Client.BACKGROUND_INV_COLOR);
        isTaken.setSelected(disable);
        if(disable == false) {
            isTaken.setIcon(new ImageIcon("res/Buttons/disableCheck.png"));
        }
        else{
            isTaken.setIcon(new ImageIcon("res/Buttons/enableCheck.png"));
        }

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
        if(disable == false) {
            isTaken.setIcon(new ImageIcon("res/Buttons/disableCheck.png"));
            add(this.isTaken);
        }
        else{
            isTaken.setIcon(new ImageIcon("res/Buttons/enableCheck.png"));
            chosenLabel = new JLabel();
            chosenLabel.setIcon(new ImageIcon("res/Buttons/disableButton.png"));
            chosenLabel.setToolTipText("apporté par "+res.getChosen());
            if(res.getChosen().equals(Client.getClient(navigationController.controllerLoginInscription).getUserName())){
                add(this.isTaken);
            }
            else{
                add(chosenLabel);
            }
        }



        add(this.nom);
        add(this.prix);
        //add(this.infosButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == isTaken){
            if(isTaken.isSelected()){
                if(mode.equals("search")){
                    ajoutItem.getRechercheItems().remove(res);
                    res.setTaken(true);
                    ajoutItem.getRechercheItems().add(res);
                }
                if(mode.equals("select")){
                    disable = true;
                    navigationController.itemUpdated(disable, idItem);
                    isTaken.setIcon(new ImageIcon("res/Buttons/EnableCheck.png"));
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
                    isTaken.setIcon(new ImageIcon("res/Buttons/DisableCheck.png"));
                }
            }
        }
        if(s == infosButton){

        }
    }
}
