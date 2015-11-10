package com.company;

import JSONLibrary.JSONArray;
import JSONLibrary.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
            checkLogin();
            return Inscrire();

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

    private void checkLogin() {
    }

    private boolean masterTest() {
        System.out.println("requete master course");
        JSONArray test = null;
        try {
            test = new JSONArray(getHTML("https://www.mastercourses.com/api2/chains/1/stores/?scope=min&mct=hieCaig6Oth2thiem7eiRiechufooWix"));
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
