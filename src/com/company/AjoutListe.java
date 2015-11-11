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
    JTextField nomListe;
    JButton valider = new JButton("valider");
    JButton annuler = new JButton("annuler");
    JPanel global = new JPanel();

    public AjoutListe(NavigationController navigationController) {
        setPreferredSize(new Dimension(200,150));
        this.navigationController = navigationController;
        JPanel nomListePanel = new JPanel();
        nomListe = new JTextField();
        nomListe.setText("entrez le nom de votre liste");
        nomListePanel.add(nomListe);
        nomListePanel.setPreferredSize(new Dimension(200,30));
        global.add(nomListePanel);
        annuler.addActionListener(this);
        valider.addActionListener(this);
        JPanel boutons = new JPanel(new FlowLayout());
        boutons.add(annuler);
        boutons.add(valider);
        boutons.setPreferredSize(new Dimension(200,50));
        global.add(boutons);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(global);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();

        if (s==valider){
            if(navigationController.addListe(nomListe.getText())){
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
