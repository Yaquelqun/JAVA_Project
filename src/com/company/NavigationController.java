package com.company;

import JSONLibrary.JSONArray;
import JSONLibrary.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationController extends JFrame {
   ControllerLoginInscription controllerLoginInscription;
    NavigationIHM navigationIHM;

    public NavigationController(ControllerLoginInscription controllerLoginInscription) {
    this.controllerLoginInscription  = controllerLoginInscription;
    navigationIHM = new NavigationIHM(this);
        this.setContentPane(navigationIHM);
        this.pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public boolean addListe(String text, String descriptionListeText, String dateListeText, String endroitListeText) {
        JSONObject envoi = new JSONObject();
        envoi.put("nomListe",text);
        envoi.put("description",descriptionListeText);
        envoi.put("date",dateListeText);
        envoi.put("endroit",endroitListeText);
        return Client.getClient(controllerLoginInscription).addListe(envoi.toString());
    }

    public ArrayList<ListeCourse> getListe(){
        ArrayList<ListeCourse> retour = new ArrayList<ListeCourse>();
        JSONArray listes = new JSONArray(Client.getClient(controllerLoginInscription).getListes());
        for(int i=0;i<listes.length();i++){
            ListeCourse objet = new ListeCourse();
            objet.setNom(listes.getJSONObject(i).getString("nomListe"));
            objet.setBudget(listes.getJSONObject(i).getString("BudgetListe"));
            objet.setDate(listes.getJSONObject(i).getString("date"));
            objet.setIdListe(listes.getJSONObject(i).getInt("id"));
            objet.setLieu(listes.getJSONObject(i).getString("endroit"));
            objet.setDescription(listes.getJSONObject(i).getString("description"));
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

    public void persoLabel(JLabel jLabel, Color backgroundColor){
        jLabel.setBackground(backgroundColor);
    Font myFont = new Font("Arial", Font.BOLD, 18);
        jLabel.setFont(myFont);
        jLabel.setForeground(Color.WHITE);
    }

    public void persoButton(String URLImage, JButton bouton){
        Icon icoButton = new ImageIcon("res/Buttons/"+URLImage);
        bouton.setIcon(icoButton);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFocusPainted(false);
        bouton.setOpaque(false);
    }

    public ArrayList<ItemCourse> getRequeteData(String nomItem) {
        nomItem = nomItem.replace(" ","%20");
        String requete = "https://www.mastercourses.com/api2/products/search/?q="+nomItem+"&scope=min&mct=hieCaig6Oth2thiem7eiRiechufooWix";
        ArrayList<ItemCourse> result = new ArrayList<>();
        JSONArray reqResult = Client.getClient(controllerLoginInscription).execRequete(requete);
        int max = 0;
        if(reqResult.length()>10) max =10;
        else max = reqResult.length();
        for(int i =0;i< max;i++){
            JSONObject pouet = reqResult.getJSONObject(i);
            ItemCourse repouet = new ItemCourse();
            String nom = pouet.getString("name");
            System.out.println(nom);
            repouet.setNom(nom);
            repouet.setChainId(pouet.getInt("chain_id"));
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
            if(pouet.get(i).getTaken()){
                demande.add(pouet.get(i));
                System.out.println("le produit "+pouet.get(i).getNom()+"a été ajouté de la chaine"+pouet.get(i).getChainId());
                System.out.println("le produit "+pouet.get(i).getNom()+"a été ajouté de la chaine"+demande.get(demande.size()-1).getChainId());
            }
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
            tmp.put("disable",false);
            tmp.put("prix",res.getPrix());
            tmp.put("chosen_by",res.getChosen());
            tmp.put("disable", res.getDisable());
            tmp.put("chain_id",res.getChainId());
            if(res.getURL().equals(null)){
                tmp.put("url","http://www.vernon-encheres.fr/_images/banniere_404.jpg");
            }
            else{
                tmp.put("url",res.getURL());
            }
            try {
                String requete = "addItem/"+navigationIHM.idCurrentList+"/"+tmp.toString();
                System.out.println("j'écris : "+requete);
                Client.getClient(controllerLoginInscription).getCurOut().writeUTF(requete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<ItemCourse> getselectItem(int id) {
        JSONArray contenu = new JSONArray(Client.getClient(controllerLoginInscription).getSelectItem(id));
        ArrayList<ItemCourse> retour = new ArrayList<>();
        for (int i = 0;i<contenu.length();i++){
            ItemCourse tmp = new ItemCourse();
            tmp.setPrix(contenu.getJSONObject(i).getString("prix"));
            tmp.setTaken(contenu.getJSONObject(i).getBoolean("taken"));
            tmp.setIdItem(contenu.getJSONObject(i).getInt("idItem"));
            tmp.setDisable(contenu.getJSONObject(i).getBoolean("disable"));
            tmp.setURL(contenu.getJSONObject(i).getString("url"));
            tmp.setChosen(contenu.getJSONObject(i).getString("chosen_by"));
            tmp.setNom(contenu.getJSONObject(i).getString("nom"));
            tmp.setChainId(contenu.getJSONObject(i).getInt("chain_id"));
            retour.add(tmp);
        }
        return retour;
    }

    public boolean addUserToList(String text, int selectedItemId) {
        String requete = "addUsertoList/"+text+"/"+selectedItemId;
        return Client.getClient(controllerLoginInscription).sendRequest(requete);
    }


    public boolean itemUpdated(boolean disable, int selectedItemId) {
        String requete;
        if (disable) {
            requete = "disableItem/" + navigationIHM.idCurrentList + "/" + selectedItemId;
            return Client.getClient(controllerLoginInscription).sendRequest(requete);
        } else {
            requete = "enableItem/" + navigationIHM.idCurrentList + "/" + selectedItemId;
            return Client.getClient(controllerLoginInscription).sendRequest(requete);
        }
    }

    public String execRequeteGeoLoc() {
        String requete = "https://context.skyhookwireless.com/accelerator/ip?version=2.0&prettyPrint=true&key=eJwVwckNACAIALC3w5CAgMcTBZYy7m5sqRB-MkTLqeaLc0wQRgJsJoDOG_rU7RZhSXkfERwLSw&user=eval&timestamp=1362089701";
        return (Client.getClient(controllerLoginInscription).execStringRequete(requete));

    }

    public String execRequeteChaineLoc(Integer integer,double lat,double longi) {
        String requete = "https://www.mastercourses.com/api2/chains/"+integer+"/stores/locator/?lat="+lat+"&lon="+longi+"&scope=min&mct=hieCaig6Oth2thiem7eiRiechufooWix";
        JSONArray resul =Client.getClient(controllerLoginInscription).execRequete(requete);
        return resul.getJSONObject(0).toString();
    }
}
