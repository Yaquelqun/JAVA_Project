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
    JButton valider = new JButton();
    JButton annuler = new JButton();
    JPanel global = new JPanel();

    public AjoutListe(NavigationController navigationController) {
        setPreferredSize(new Dimension(300,200));
        this.navigationController = navigationController;
        setUndecorated(true);
        setLocationRelativeTo(null);
        JPanel nomListePanel = new JPanel();
        nomListePanel.setLayout(new BoxLayout(nomListePanel,BoxLayout.Y_AXIS));
        JLabel expl = new JLabel("entrez le nom de votre liste");
        nomListe = new JTextField(50);
        nomListePanel.add(expl);
        nomListePanel.add(nomListe);
        nomListePanel.setPreferredSize(new Dimension(200,50));
        global.add(nomListePanel);
        annuler.addActionListener(this);
        valider.addActionListener(this);
        JPanel boutons = new JPanel(new FlowLayout());
        navigationController.persoButton("CancelButton.png",annuler);
        boutons.add(annuler);
        navigationController.persoButton("OKButton.png",valider);
        boutons.add(valider);
        boutons.setPreferredSize(new Dimension(300,70));
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
