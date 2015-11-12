package com.company;

import JSONLibrary.JSONArray;
import JSONLibrary.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

/**
 * Created by Sandjiv on 09/11/2015.
 */
public class ControleurOrdres {

    String typeOrdre;
    String parametreOrdre;
    Serveur refServ;
    int indexClient =0;
    Connected con = null;
    public ControleurOrdres(final Serveur serveur,Connected connec) {
        refServ = serveur;
        while(!refServ.listConnected.get(indexClient).equals(connec))
        {
            indexClient++;
        }
        System.out.println("on a "+refServ.listConnected.size()+" personnes connectées");
        this.con = connec;
    }

    public boolean setOrdre(String order) {
        typeOrdre = new String(order.substring(0,order.lastIndexOf('/')));
        parametreOrdre = new String(order.substring(order.lastIndexOf('/')+1,order.length()));
        if (typeOrdre.equals("disconnect")){
            return disconnect();
        }
        if(typeOrdre.equals("MasterRequest")){
            return masterTest();
        }
        if(typeOrdre.equals("inscription")){
            return Inscrire();
        }
        if(typeOrdre.equals("connexion")){
            return checkLoginPassword();
        }
        if(typeOrdre.equals("ajoutListe")){
            return ajoutListe();
        }
        if(typeOrdre.equals("getGlobalListe")){
            return globalListe();
        }

        return true;
    }

    private boolean globalListe() {
        try {
            FileReader fr = new FileReader("liste.json");
            BufferedReader input = new BufferedReader(fr);
            JSONArray tableauListe = new JSONArray(input.readLine());
            JSONArray retour = new JSONArray();
            for(int i =0;i<tableauListe.length();i++)
            {
                String pouet = new String(tableauListe.getJSONObject(i).getJSONArray("logins").toString());
                System.out.println(pouet);
                JSONArray tableauxnoms = new JSONArray(pouet);
                for(int j =0;j<tableauxnoms.length();j++){
                    if(tableauxnoms.getJSONObject(j).get("noms").equals(con.name)){

                        retour.put(tableauListe.getJSONObject(i));
                    }
                }
            }
            System.out.println("j'ai la liste des courses, je l'écris");
            con.out.writeUTF(retour.toString());
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return(true);
    }

    private boolean ajoutListe() {
        try{
            FileReader fr = new FileReader("liste.json");
            BufferedReader input = new BufferedReader(fr);
            JSONArray tableauListe = new JSONArray(input.readLine());
            JSONObject liste = new JSONObject();
            liste.put("nomListe",parametreOrdre);
            liste.put("BudgetListe","");
            liste.put("dateListe", "maintenant");
            liste.put("logins",new JSONArray("[{\"noms\":\""+con.name+"\"}]"));
            liste.put("id",tableauListe.length());
            tableauListe.put(liste);
            FileWriter fw = new FileWriter("liste.json",false);
            BufferedWriter output = new BufferedWriter(fw);
            output.write(tableauListe.toString());
            output.close();
            con.out.writeBoolean(true);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                con.out.writeBoolean(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return true;
    }

    private boolean Inscrire() {
        System.out.println("quelqun s'inscrit");
        String jsonInscrit = parametreOrdre;
        JSONObject inscrit = new JSONObject(jsonInscrit);
        System.out.println("son login est :"+inscrit.getString("login")+ " et son pass est : "+inscrit.getString("psw"));
        try
        {
            FileReader fr = new FileReader("inscrit.json");
            BufferedReader input = new BufferedReader(fr);
            JSONArray inscrits = new JSONArray(input.readLine());
            System.out.println("ya tant de personnes : "+inscrits.length());
            for(int i =0;i<inscrits.length();i++){
                if(inscrit.getString("login").equals( ((JSONObject) inscrits.get(i)).getString("login"))){
                    System.out.println("ce login est déjà pris, j'écris false via "+con.sock.toString());

                    con.out.writeBoolean(false);
                    System.out.println("c'est fait !");
                    return(true);

                }
            }
            System.out.println("wouhou login disponible, j'écris true ");
            con.out.writeBoolean(true);
            System.out.println("c'est fait !");
            FileWriter fw = new FileWriter("inscrit.json", false);
            BufferedWriter output = new BufferedWriter(fw);
            inscrits.put(inscrit);
            output.write(inscrits.toString());

            output.flush();

            output.close();
        }
        catch(IOException ioe){
            System.out.print("Erreur : ");
            ioe.printStackTrace();
        }
        return true;
    }

    private boolean checkLoginPassword() {
        FileReader fr;
        JSONObject inscrit = new JSONObject(parametreOrdre);
        try {
            fr = new FileReader("inscrit.json");
        BufferedReader input = new BufferedReader(fr);
        JSONArray inscrits = new JSONArray(input.readLine());
        System.out.println(inscrit.get("login")+"veut se connecter via "+inscrit.get("psw"));
        for(int i =0;i<inscrits.length();i++){
            if(inscrit.getString("login").equals( ((JSONObject) inscrits.get(i)).getString("login"))){
                System.out.println("ce login existe");
                if(inscrit.getString("psw").equals( ((JSONObject) inscrits.get(i)).getString("psw"))){
                    System.out.println("ce psw existe, j'écris true via "+con.sock.toString());
                    con.out.writeBoolean(true);
                    return true;
                }

            }
        }
            System.out.println("cet utilisateur n'existe pas, j'écris false via "+con.sock.toString());
            con.out.writeBoolean(false);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean masterTest() {
        System.out.println("requete master course");
        JSONArray test = null;
        try {
            test = new JSONArray(getHTML("https://www.mastercourses.com/api2/products/search/?q=coca&lat=40&lon=5&mct=hieCaig6Oth2thiem7eiRiechufooWix"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(test.get(2));
        return true;
    }

    private boolean disconnect() {
        if(con.name.equals("pouet"))
        {
            refServ.listConnected.remove(con);
        }
        con.connected = false ;
        refServ.ihm.deConnected(indexClient);
        refServ.displayAllClients() ;
        return false;
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


}
