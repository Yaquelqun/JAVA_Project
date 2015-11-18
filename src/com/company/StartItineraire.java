package com.company;

import JSONLibrary.JSONObject;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sandjiv on 17/11/2015.
 */
public class StartItineraire extends JEditorPane {
    NavigationController navigationController;
    private int zoomFactor = 7;
    private String ApiKey = "";
    private String roadmap = "roadmap";
    public final String viewTerrain = "terrain";
    public final String viewSatellite = "satellite";
    public final String viewHybrid = "hybrid";
    public final String viewRoadmap = "roadmap";
    double currentlat,currentlong;
    ArrayList<ItemCourse> currentList;
    Set<Integer> diffchaines = new HashSet<>();

    public StartItineraire(NavigationController navigationController, ArrayList<ItemCourse> currentList) {
        this.navigationController = navigationController;
        this.currentList = new ArrayList<>(currentList);
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) kit.createDefaultDocument();

        for (int i =0; i< currentList.size();i++){
            diffchaines.add(currentList.get(i).getChainId());
        }
        setgeoloc();
        this.setEditable(false);
        this.setContentType("text/html");
        this.setEditorKit(kit);
        this.setDocument(htmlDoc);
        try {
            setApiKey("AIzaSyCWmERAAh3Xy-3i7_9ZibdwM52wmZeeCn4");
            //setRoadmap(viewHybrid);
            setZoom(10);
            /**
             Afficher la ville de Strabourg
             */
            //showLocation("gardanne", "france", 390, 400);
            /**
             * Afficher Paris en fonction ses coordonnées GPS
             */
            showCoordinate(String.valueOf(currentlat),String.valueOf(currentlong),390, 400);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setSize(400, 420);
        frame.setLocation(200, 200);
        frame.setVisible(true);


    }

    public StartItineraire(NavigationController navigationController,double lat, double longi) {
        this.navigationController = navigationController;
        currentlat = lat;
        currentlong = longi;
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) kit.createDefaultDocument();
        this.setEditable(false);
        this.setContentType("text/html");
        this.setEditorKit(kit);
        this.setDocument(htmlDoc);
        try {
            setApiKey("AIzaSyCWmERAAh3Xy-3i7_9ZibdwM52wmZeeCn4");
            //setRoadmap(viewHybrid);
            setZoom(10);
            //showLocation("gardanne", "france", 390, 400);
            showCoordinate(String.valueOf(currentlat),String.valueOf(currentlong),300, 200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setgeoloc() {
        JSONObject totalloc = new JSONObject(navigationController.execRequeteGeoLoc());
        currentlat = totalloc.getJSONObject("data").getJSONObject("location").getDouble("latitude");
        currentlong = totalloc.getJSONObject("data").getJSONObject("location").getDouble("longitude");


    }

    /**
     * Fixer le zoom
     * @param zoom valeur de 0 à 21
     */
    public void setZoom(int zoom) {
        this.zoomFactor = zoom;
    }

    /**
     * Fixer la clé de developpeur
     * @param key APIKey fourni par Google
     */
    public void setApiKey(String key) {
        this.ApiKey = key;
    }

    /**
     * Fixer le type de vue
     * @param roadMap
     */
    public void setRoadmap(String roadMap) {
        this.roadmap = roadMap;
    }

    /**
     * Afficher la carte d'après des coordonnées GPS
     * @param latitude
     * @param longitude
     * @param width
     * @param height
     * @throws Exception erreur si la APIKey est non renseignée
     */
    public void showCoordinate(String latitude, String longitude, int width, int height) throws Exception {
        this.setMap(latitude, longitude, width, height);
    }

    /**
     * Afficher la carte d'après une ville et son pays
     * @param city
     * @param country
     * @param width
     * @param height
     * @throws Exception erreur si la APIKey est non renseignée
     */
    public void showLocation(String city, String country, int width, int height) throws Exception {
        this.setMap(city, country, width, height);
    }

    /**
     * Assembler l'url et Générer le code HTML
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws Exception
     */
    private void setMap(String x, String y, Integer width, Integer height) throws Exception {
        if (this.ApiKey.isEmpty()) {
            throw new Exception("Developper API Key not set !!!!");
        }

        String url = "http://maps.google.com/maps/api/staticmap?";
        url += "center=" + x + "," + y;
        url += "&zoom=" + this.zoomFactor;
        url += "&size=" + width.toString() + "x" + height.toString();
        url += "&scale=false";
        url += "&maptype=" + this.roadmap;
        url += "&markers=color:blue" + x + "," + y;
        url += "&sensor=false";
        url += "&key=" + this.ApiKey;
//        ArrayList<Integer> pouet = new ArrayList<Integer>(diffchaines);
//        ArrayList<String> test = new ArrayList<>();
//        test.add("113+route+nationale");
//        test.add("1+rue+René+Cailloux");
//        for (int j =0;j<test.size();j++){
//            //JSONObject Objet = new JSONObject(navigationController.execRequeteChaineLoc(pouet.get(j),currentlat,currentlong));
//            url += "&markers=size:mid";
//            url += "%7Ccolor:0xff0000";
//            url += "%7Clabel:"+j;
//            //url += "%7C"+Objet.getString("address");
//            url += "%7C"+test.get(j);
//        }
        System.out.println(url);

       String html = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
        html += "<html><head></head><body>";
        html += "<img src='" + url + "'>";
        html += "</body></html>";

        this.setText(html);
    }

}
