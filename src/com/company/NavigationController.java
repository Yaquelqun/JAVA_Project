package com.company;

import JSONLibrary.JSONArray;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationController extends JFrame {
    Client client;
    NavigationIHM navigationIHM;

    public NavigationController(Client client) {
    this.client  = client;
    navigationIHM = new NavigationIHM(this);
        this.setContentPane(navigationIHM);
        this.pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public boolean addListe(String text) {
        return client.addListe(text);
    }

    public ArrayList<ListeCourse> getListe(){
        ArrayList<ListeCourse> retour = new ArrayList<ListeCourse>();
        JSONArray listes = new JSONArray(client.getListes());
        for(int i=0;i<listes.length();i++){
            ListeCourse objet = new ListeCourse();
            objet.setNom(listes.getJSONObject(i).getString("nomListe"));
            objet.setBudget(listes.getJSONObject(i).getString("BudgetListe"));
            objet.setDate(listes.getJSONObject(i).getString("dateListe"));
            objet.setIdListe(listes.getJSONObject(i).getInt("id"));
            retour.add(objet);
        }
        return retour;
    }


    public void goToDetail(int idButton) {
        navigationIHM.updateHeader(idButton);
        navigationIHM.removeNavigation();
        navigationIHM.addDetailedPanel(idButton);
        navigationIHM.repaint();
    }
}
