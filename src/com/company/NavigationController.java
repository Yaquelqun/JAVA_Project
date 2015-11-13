package com.company;

import JSONLibrary.JSONArray;
import JSONLibrary.JSONObject;
import com.intellij.util.containers.TreeTraversal;

import javax.swing.*;
import java.io.IOException;
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

    public void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean addItem(String text, String currentListe) {
        return client.addItem(text);
    }

    public ArrayList<ItemCourse> getRequeteData(String nomItem) {
        String requete = "https://www.mastercourses.com/api2/products/search/?q="+nomItem+"&scope=min&ip=current&mct=hieCaig6Oth2thiem7eiRiechufooWix";
        ArrayList<ItemCourse> result = new ArrayList<>();
        JSONArray reqResult = client.execRequete(requete);

        for(int i =0;i< reqResult.length();i++){
            JSONObject pouet = reqResult.getJSONObject(i);
            System.out.println(pouet.toString());
            ItemCourse repouet = new ItemCourse();
            String nom = pouet.getString("name");
            System.out.println(nom);
            repouet.setNom(nom);
            repouet.setIdItem(pouet.getInt("id"));
            repouet.setTaken(false);
            repouet.setPrix("0");
            repouet.setURL(pouet.getString("image_url"));
            result.add(repouet);
        }
        return result;
    }

    public boolean getSelectedSearchItem(ArrayList<ItemCourse> pouet) {
        ArrayList<ItemCourse> demande = new ArrayList<>();
        for (int i =0; i< pouet.size();i++){
            if(pouet.get(i).getTaken()) demande.add(pouet.get(i));
        }
        addSelectedItem(demande);
        return true;
    }

    private void addSelectedItem(ArrayList<ItemCourse> demande) {
        for (int i= 0; i<demande.size();i++){
            ItemCourse res = new ItemCourse(demande.get(i));
            JSONObject tmp = new JSONObject();
            tmp.put("idItem",res.getIdItem());
            tmp.put("nom",res.getNom());
            tmp.put("taken",false);
            tmp.put("prix",res.getPrix());
            tmp.put("url",res.getURL());
            try {
                client.curOut.writeUTF("addItem/"+navigationIHM.idCurrentList+tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<ItemCourse> getselectItem(int id) {
        JSONArray contenu = new JSONArray(client.getSelectItem(id));
        ArrayList<ItemCourse> retour = new ArrayList<>();
        for (int i = 0;i<contenu.length();i++){
            ItemCourse tmp = new ItemCourse();
            tmp.setPrix(contenu.getJSONObject(i).getString("prix"));
            tmp.setTaken(contenu.getJSONObject(i).getBoolean("taken"));
            tmp.setIdItem(contenu.getJSONObject(i).getInt("idItem"));
            tmp.setURL(contenu.getJSONObject(i).getString("url"));
            tmp.setNom(contenu.getJSONObject(i).getString("nom"));
            retour.add(tmp);
        }
        return retour;
    }
}
