package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class AjoutListe  extends JFrame implements ActionListener {
    private NavigationController navigationController;
    JTextField nomListe, descriptionListe, dateListe, endroitListe;
    JButton valider = new JButton();
    JButton confirmer;
    JButton annuler = new JButton();
    JPanel global = new JPanel();
    String createdListe;

    public AjoutListe(NavigationController navigationController) {
        setPreferredSize(new Dimension(300,200));
        this.navigationController = navigationController;
        setUndecorated(true);
        setLocationRelativeTo(null);
        JPanel nomListePanel = new JPanel();
        nomListePanel.setLayout(new BoxLayout(nomListePanel,BoxLayout.Y_AXIS));
        JLabel expl = new JLabel("entrez le nom de votre liste");
        navigationController.persoLabel(expl,Client.BACKGROUND_INV_COLOR);
        expl.setFont(new Font("Serif", Font.BOLD, 14));
        nomListePanel.setBackground(Client.BACKGROUND_INV_COLOR);
        nomListe = new JTextField(50);
        nomListePanel.add(expl);
        nomListePanel.add(nomListe);
        nomListePanel.setPreferredSize(new Dimension(200,50));
        global.setBackground(Client.BACKGROUND_INV_COLOR);
        global.add(nomListePanel);
        annuler.addActionListener(this);
        valider.addActionListener(this);
        JPanel boutons = new JPanel(new FlowLayout());
        boutons.setBackground(Client.BACKGROUND_COLOR);
        navigationController.persoButton("CancelButton.png",annuler);
        boutons.add(annuler);
        navigationController.persoButton("OKButton.png",valider);
        boutons.add(valider);
        boutons.setPreferredSize(new Dimension(300,70));
        global.add(boutons,BOTTOM_ALIGNMENT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(global);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void toMapAndDetailPanel() {
        global.removeAll();
        global.setBackground(Client.BACKGROUND_INV_COLOR);
        global.setLayout(new BoxLayout(global,BoxLayout.Y_AXIS));
        JLabel labelDescription = new JLabel("entrez une description");
        navigationController.persoLabel(labelDescription,Client.BACKGROUND_INV_COLOR);
        descriptionListe = new JTextField(60);
        descriptionListe.setText("description");
        JLabel labelDate = new JLabel("choisissez une date");
        navigationController.persoLabel(labelDate,Client.BACKGROUND_INV_COLOR);
        dateListe = new JTextField(60);
        dateListe.setText("date");
        JLabel labelLieux = new JLabel("choississez une adresse pour votre écènement");
        navigationController.persoLabel(labelLieux,Client.BACKGROUND_INV_COLOR);
        endroitListe = new JTextField(60);
        endroitListe.setText("lieu");

        JPanel boutonsPanel = new JPanel(new FlowLayout());
        boutonsPanel.setBackground(Client.BACKGROUND_INV_COLOR);
        confirmer = new JButton();
        navigationController.persoButton("OKButton.png",confirmer);
        confirmer.addActionListener(this);
        boutonsPanel.add(confirmer);
        boutonsPanel.add(annuler);

        global.add(labelDescription);
        global.add(descriptionListe);
        global.add(labelDate);
        global.add(dateListe);
        global.add(labelLieux);
        global.add(endroitListe);
        global.add(boutonsPanel);
        repaint();
        pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();

        if (s==valider){
            createdListe = nomListe.getText();
            System.out.println("on va créer la liste "+createdListe);
            toMapAndDetailPanel();
        }
        if(s== confirmer){
            if(navigationController.addListe(createdListe,descriptionListe.getText(),dateListe.getText(),endroitListe.getText())){
                navigationController.setVisible(true);
                navigationController.navigationIHM.updateNavigation();
                dispose();
            }
        }
        else if(s==annuler){
            navigationController.setVisible(true);
            dispose();
        }

    }


}
