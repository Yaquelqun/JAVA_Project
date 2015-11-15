package com.company;

import javax.swing.*;
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
    JTextField login = new JTextField("avec qui partager ?");

    public partageListe(NavigationController navigationController, ArrayList<ListeCourse> mesListes) {
        this.mesListes = mesListes;
        this.navigationController = navigationController;
        JPanel total = new JPanel();
        total.setLayout(new BoxLayout(total,BoxLayout.Y_AXIS));
        comboListes = new JComboBox<>();
        for (int i=0;i<mesListes.size();i++) comboListes.addItem(mesListes.get(i).getNom());
        total.add(comboListes);
        total.add(login);
        total.add(Valider);
        Valider.addActionListener(this);
        add(total);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("je valide");
        int tmp = 0;
        for( int i=0;i<mesListes.size();i++){
            if(mesListes.get(i).getNom().equals(comboListes.getSelectedItem())){
                tmp = mesListes.get(i).getIdListe();
            }
        }
        if(!navigationController.addUserToList(login.getText(),tmp)){
            navigationController.infoBox("il y a eu un probleme avec l'ajout","erreur");
        }
        navigationController.setVisible(true);
        dispose();

    }
}
