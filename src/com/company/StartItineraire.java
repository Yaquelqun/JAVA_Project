package com.company;

import JSONLibrary.JSONObject;
import com.teamdev.jxbrowser.chromium.Browser;

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

    private Browser roger;
    com.teamdev.jxbrowser.chromium.swing.BrowserView jean;
    ArrayList<ItemCourse> currentList;
    Set<Integer> diffchaines = new HashSet<>();

    public StartItineraire(NavigationController navigationController, ArrayList<ItemCourse> currentList) {
        this.navigationController = navigationController;
        this.currentList = new ArrayList<>(currentList);
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) kit.createDefaultDocument();
        roger  = new Browser();
        jean  = new com.teamdev.jxbrowser.chromium.swing.BrowserView(roger);

        for (int i =0; i< currentList.size();i++){
            if(currentList.get(i).getChosen().equals(Client.getClient(navigationController.controllerLoginInscription).getUserName())) {
                diffchaines.add(currentList.get(i).getChainId());
            }
        }
        setgeoloc();
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
        frame.add(jean);
        frame.setSize(400, 420);
        frame.setVisible(true);
        repaint();


    }

    public StartItineraire(NavigationController navigationController,String location) {
        this.navigationController = navigationController;
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
            location = location.replace(" ","+");
            showLocation(location, "", 300, 200);
            //showCoordinate(String.valueOf(currentlat),String.valueOf(currentlong),300, 200);
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
        this.setStaticMap(city, country, width, height);
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

//        String url = "http://maps.google.com/maps/api/staticmap?";
//        url += "center=" + x + "," + y;
//        //url += "&zoom=" + this.zoomFactor;
//        url += "&size=" + width.toString() + "x" + height.toString();
//        url += "&scale=false";
//        url += "&maptype=" + this.roadmap;
//        url += "&markers=color:blue" + x + "," + y;
//        url += "&sensor=false";
//        url += "&key=" + this.ApiKey;
//        url += "&markers=size:mid";
//        url += "%7Ccolor:0x00ff00";
//        url += "%7Clabel:pos";
//        url += "%7C"+x+","+y;
        String url = "https://www.google.fr/maps/dir/";
///https://www.google.fr/maps/dir/879+Avenue+de+Mimet,+13120+Gardanne/920+Chemin+de+Roman,+13120+Gardanne/Aix-en-Provence/@43.4878312,5.4084481,13z
        ArrayList<Integer> pouet = new ArrayList<>(diffchaines);
        url+=x+","+y+"/";

        for (int j =0;j<pouet.size();j++){;
            JSONObject Objet = new JSONObject(navigationController.execRequeteChaineLoc(pouet.get(j),currentlat,currentlong));
//            url += "&markers=size:mid";
//            url += "%7Ccolor:0xff0000";
//            url += "%7Clabel:"+j;
            String tmp = Objet.getString("address");
            tmp = tmp.replace(" ","+");
            url+= tmp+"/";
//            url += "%7C"+tmp;
        }
        url+="@"+x+","+y+"z";
        System.out.println(url);
        roger.loadURL(url);
        add(jean);
//       String html = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
//        html += "<html><head></head><body>";
//        html += "<img src='" + url + "'>";
//        html += "</body></html>";
//
//        this.setText(html);
    }

    private void setStaticMap(String x, String y, Integer width, Integer height) throws Exception {
        if (this.ApiKey.isEmpty()) {
            throw new Exception("Developper API Key not set !!!!");
        }

        String url = "http://maps.google.com/maps/api/staticmap?";
        url += "center=" + x + "," + y;
        //url += "&zoom=" + this.zoomFactor;
        url += "&size=" + width.toString() + "x" + height.toString();
        url += "&scale=false";
        url += "&maptype=" + this.roadmap;
        url += "&markers=color:blue" + x + "," + y;
        url += "&sensor=false";
        url += "&key=" + this.ApiKey;
        url += "&markers=size:mid";
        url += "%7Ccolor:0x00ff00";
        url += "%7Clabel:pos";
        url += "%7C"+x+","+y;
        ArrayList<Integer> pouet = new ArrayList<>(diffchaines);

        for (int j =0;j<pouet.size();j++){;
            JSONObject Objet = new JSONObject(navigationController.execRequeteChaineLoc(pouet.get(j),currentlat,currentlong));
            url += "&markers=size:mid";
            url += "%7Ccolor:0xff0000";
            url += "%7Clabel:"+j;
            String tmp = Objet.getString("address");
            tmp = tmp.replace(" ","+");
            url += "%7C"+tmp;
        }
        System.out.println(url);
       String html = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
        html += "<html><head></head><body>";
        html += "<img src='" + url + "'>";
        html += "</body></html>";

        this.setText(html);
    }

}
