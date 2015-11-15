package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class ObjetListe extends JPanel implements ActionListener {
    JTextArea nom;
    JTextArea budget;
    JButton detail = new JButton();
    int idButton;
    NavigationController navigationController;

    public ObjetListe (String nom, String budget,int id,NavigationController navigationController){
        this.navigationController = navigationController;

        this.setBackground(Client.BACKGROUND_INV_COLOR);
        this.setLayout(new FlowLayout());
           setPreferredSize(new Dimension(300,70));
        this.nom = new JTextArea(nom);

        idButton = id;
        this.budget = new JTextArea("Total : €");
       navigationController.persoButton("res/DetailsButton.png",detail);
        detail.addActionListener(this);
        add(this.nom);
        add(this.budget);
        add(this.detail);
        System.out.println("objet "+nom+" "+budget+" a ete créé");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == detail){
            navigationController.goToDetail(idButton);
        }
    }
}
