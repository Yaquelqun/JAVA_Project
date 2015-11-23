package com.company;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class AjoutListe  extends JFrame implements ActionListener, FocusListener {
    private NavigationController navigationController;
    JTextField nomListe, descriptionListe, endroitListe, dateListe;
    JButton valider = new JButton();
    JButton confirmer;
    JButton annuler = new JButton();
    JPanel global = new JPanel();
    String createdListe;

    public AjoutListe(NavigationController navigationController) {
        setPreferredSize(new Dimension(300,130));
        this.navigationController = navigationController;
        setUndecorated(true);
        setLocationRelativeTo(null);
        JPanel nomListePanel = new JPanel();
        nomListePanel.setLayout(new BoxLayout(nomListePanel,BoxLayout.Y_AXIS));
        JLabel expl = new JLabel("Entrez le nom de votre liste");
        navigationController.persoLabel(expl,Client.BACKGROUND_INV_COLOR);
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
        setPreferredSize(new Dimension(300,200));
        global.removeAll();
        global.setBackground(Client.BACKGROUND_INV_COLOR);
        global.setLayout(new BoxLayout(global,BoxLayout.Y_AXIS));
        JLabel labelDescription = new JLabel("Entrez une description");
        navigationController.persoLabel(labelDescription,Client.BACKGROUND_INV_COLOR);
        descriptionListe = new JTextField(60);
        descriptionListe.setText("Description");
        descriptionListe.addFocusListener(this);
        JLabel labelDate = new JLabel("Choisissez une date");
        navigationController.persoLabel(labelDate,Client.BACKGROUND_INV_COLOR);
        dateListe = new JTextField(60);
        dateListe.setText("Date");
        dateListe.addFocusListener(this);
        JLabel labelLieux = new JLabel("Choississez une adresse");
        navigationController.persoLabel(labelLieux,Client.BACKGROUND_INV_COLOR);
        labelLieux.setAlignmentX(LEFT_ALIGNMENT);
        endroitListe = new JTextField(60);
        endroitListe.setText("Lieu");
        endroitListe.addFocusListener(this);

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


    @Override
    public void focusGained(FocusEvent e) {
        Object s = e.getSource();
        if (s == descriptionListe) {
            if(descriptionListe.getText().equals("Description")){
                descriptionListe.setText("");
            }
        }
        if (s == dateListe) {
            if (dateListe.getText().equals("Date")) {
                dateListe.setText("");
            }
        }
        if (s == endroitListe) {
            if(endroitListe.getText().equals("Lieu")){
                endroitListe.setText("");
            }
        }
        ((JTextField) s).setBorder(BorderFactory.createLineBorder(Client.BACKGROUND_COLOR,3,true));
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object s = e.getSource();
        if(((JTextField) s).getText().equals("")) {
            if (s == descriptionListe) {
                descriptionListe.setText("Description");
            } else if (s == dateListe) {
                dateListe.setText("Date");
            } else if (s == endroitListe) {
                endroitListe.setText("Lieu");
            }
        }
        ((JTextField) s).setBorder(new MatteBorder(1,1,1,1,Color.gray));
    }
}
