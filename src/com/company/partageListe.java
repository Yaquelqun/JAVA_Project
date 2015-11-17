package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 15/11/2015.
 */
public class partageListe extends JFrame implements ActionListener {
    ArrayList<ListeCourse> mesListes;
    NavigationController navigationController;
    JComboBox<String> comboListes;
    JButton Valider = new JButton("valider");
    JButton Annuler = new JButton("annuler");
    JLabel login = new JLabel("avec qui partager ?");

    public partageListe(NavigationController navigationController, ArrayList<ListeCourse> mesListes) {
        this.mesListes = mesListes;
        setLocationRelativeTo(null);
        setUndecorated(true);
        this.navigationController = navigationController;
        JPanel total = new JPanel();
        total.setBackground(Client.BACKGROUND_INV_COLOR);
        JPanel boutonPanel = new JPanel(new FlowLayout());
        boutonPanel.setBackground(Client.BACKGROUND_INV_COLOR);
        total.setLayout(new BoxLayout(total,BoxLayout.Y_AXIS));
        comboListes = new JComboBox<>();
        for (int i=0;i<mesListes.size();i++) comboListes.addItem(mesListes.get(i).getNom());
        total.add(comboListes);
        navigationController.persoLabel(this.login, Client.BACKGROUND_INV_COLOR);
        navigationController.persoButton("OKButton.png",Valider);
        navigationController.persoButton("CancelButton.png",Annuler);
        total.add(login);
        boutonPanel.add(Annuler);
        boutonPanel.add(Valider);
        total.add(boutonPanel);
        Valider.addActionListener(this);
        Annuler.addActionListener(this);
        add(total);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s==Valider) {
            System.out.println("je valide");
            int tmp = 0;
            for (int i = 0; i < mesListes.size(); i++) {
                if (mesListes.get(i).getNom().equals(comboListes.getSelectedItem())) {
                    tmp = mesListes.get(i).getIdListe();
                }
            }
            if (!navigationController.addUserToList(login.getText(), tmp)) {
                navigationController.infoBox("il y a eu un probleme avec l'ajout", "erreur");
            }
            navigationController.setVisible(true);
            dispose();
        }
        if (s==Annuler){
            navigationController.setVisible(true);
            dispose();
        }
    }
}
