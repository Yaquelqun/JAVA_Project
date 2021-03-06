package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class AjoutItem  extends JFrame implements ActionListener {
    private NavigationController navigationController;
    JTextField nomItem;

    JButton chercher = new JButton();
    JButton valider = new JButton();
    JButton annuler = new JButton();
    JPanel global = new JPanel();
    JPanel recherche;
    JScrollPane scrollFrame;


    ArrayList<ItemCourse> rechercheItems = new ArrayList<>();

    public AjoutItem(NavigationController navigationController) {
        this.navigationController = navigationController;
        setPreferredSize(new Dimension(300,600));
        setUndecorated(true);
        setLocationRelativeTo(null);
        JPanel nomListePanel = new JPanel(new FlowLayout());
        nomListePanel.setPreferredSize(new Dimension(250,70));
        nomItem = new JTextField("Nom du produit cherché");
        navigationController.persoButton("SearchButton.png",chercher);
        chercher.addActionListener(this);
        nomListePanel.add(nomItem);
        nomListePanel.add(chercher);

        recherche = new JPanel();
        recherche.setLayout(new BoxLayout(recherche,BoxLayout.Y_AXIS));
        scrollFrame = new JScrollPane(recherche);
        JPanel boutons = new JPanel();
        boutons.setPreferredSize(new Dimension(300,70));
        annuler.addActionListener(this);
        valider.addActionListener(this);
        navigationController.persoButton("CancelButton.png",annuler);
        navigationController.persoButton("OKButton.png",valider);
        boutons.add(annuler);
        boutons.add(valider);

        global.add(nomListePanel);
        global.add(scrollFrame);
        global.add(boutons);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(global);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public ArrayList<ItemCourse> getRechercheItems() {
        return rechercheItems;
    }

    public void setRechercheItems(ArrayList<ItemCourse> rechercheItems) {
        this.rechercheItems = rechercheItems;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();

        if(s==chercher){
           rechercheItems = navigationController.getRequeteData(nomItem.getText());
            int max = 0;
            if(rechercheItems.size()>10) max =10;
            else max = rechercheItems.size();
            for(int i =0;i<max;i++){
                ObjetItem pouet = new ObjetItem(rechercheItems.get(i),navigationController);
                pouet.setModeSearch(this);
                recherche.add(pouet);
                System.out.println("et implémenté");
            }

            recherche.setAutoscrolls(true);
            scrollFrame.setPreferredSize(new Dimension( 300,400));
            scrollFrame.repaint();
            repaint();
            pack();
        }

        if (s==valider){
               if(navigationController.getSelectedSearchItem(rechercheItems)) {
                   navigationController.setVisible(true);
                   navigationController.navigationIHM.updatedetailedPanel();
                   dispose();
               }
        }
        else if(s==annuler){
            navigationController.setVisible(true);
            dispose();
        }
    }
}
