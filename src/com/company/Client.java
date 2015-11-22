package com.company;

import JSONLibrary.JSONArray;
import JSONLibrary.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import static com.company.Constants.PORT;

/**
 * Created by Loriane on 10/11/2015.
 */
final public class Client {
    public static final Color BACKGROUND_COLOR = new Color(253,175,112), BACKGROUND_INV_COLOR = new Color(68, 154, 151);
    private static Client client;
    private ControllerLoginInscription controllerLoginInscription;
    private Socket sock;
    private DataInputStream curIn ;
    private DataOutputStream curOut ;
    private String userName;
    private ListeCourse listeCourse;
    private boolean connected = false ;

    public static Client getClient (ControllerLoginInscription controllerLoginInscription) { // récup de la référence unique
        if (client == null){
            client = new Client(controllerLoginInscription) ;
        }
        return client ;
    }
    /**
     * connect to the server
     **/

    private Client(ControllerLoginInscription controllerLoginInscription) {
        this.controllerLoginInscription = controllerLoginInscription;
    }

    /**
     * start socket
     */
    public boolean connect(String nickName) {
        try {
            System.out.println(nickName+" tente de se connecter");
            sock = new Socket("localhost", PORT);

            curIn = new DataInputStream(sock.getInputStream());
            curOut = new DataOutputStream(sock.getOutputStream());
            System.out.println("j'écris mon nom via "+sock.toString());
            curOut.writeUTF(nickName);
            System.out.println("done, j'attends un ordre via "+sock.toString());
            if(!curIn.readBoolean()){
                //TODO ya un problème là, faudrait renvoyer un false sinon le programme continue comme si de rien était
                controllerLoginInscription.infoBox("cette personne est déjà connectée","illegal Login");
                connected = false;
                sock.close();
                return false;
            }
            else {
                connected = true;
                System.out.println(nickName + " est connecté");
                return true;
            }

        }
        catch (IOException e) {
            controllerLoginInscription.infoBox("problème à la connexion","erreur");
            e.printStackTrace();
        }
        return false;
    }


    /**
     *
     * @param nickName
     */
    public void disconnect(String nickName) {
        try {
            curOut.writeUTF("disconnect/1/"+nickName);
            connected = false ;

            sock.close();

        } catch (IOException e1) {
            controllerLoginInscription.infoBox("problème à la fermeture","erreur");
            e1.printStackTrace();
        }
    }

    public void inscription(String login, String psw){
        JSONObject nouveauinscrit = new JSONObject();
        nouveauinscrit.accumulate("login",login);
        nouveauinscrit.accumulate("psw",psw);
        try {
            curOut.writeUTF("inscription/1/"+nouveauinscrit.toString());
            System.out.println("J attends une réponse ... via"+ sock.toString());
            boolean unique = curIn.readBoolean();
            System.out.println("c'est fait !");
            if(!unique){
                System.out.println("login existe déjà !!!");
                controllerLoginInscription.infoBox("ce login est déjà pris","dommage");
            }
            else{
                System.out.println("je déconnecte pouet");
                controllerLoginInscription.infoBox("inscription réussie","félicitation");
                disconnect("pouet");
                controllerLoginInscription.pageLogin();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void inscriptionAbort(){
        if(!sock.isClosed()) disconnect("pouet");
        controllerLoginInscription.pageLogin();
    }

    public void login(String login, String psw){
        connect(login);
        JSONObject nouveaumonsieur = new JSONObject();
        nouveaumonsieur.accumulate("login",login);
        nouveaumonsieur.accumulate("psw",psw);
        System.out.println(nouveaumonsieur.toString());
        try {
            curOut.writeUTF("connexion/1/"+nouveaumonsieur.toString());
            System.out.println("J attends une réponse ... via"+ sock.toString());
            boolean unique = curIn.readBoolean();
            System.out.println("c'est fait !");
            if(!unique){
                System.out.println("login existe déjà !!!");
                controllerLoginInscription.infoBox("erreur login/mdp","dommage");
            }
            else{
                System.out.println("on passe sur login");
                userName = login;
                controllerLoginInscription.nextFen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void keepLogin(String login, String psw) {
        FileWriter fw;
        JSONObject Jason;
        try {
            Jason = new JSONObject();
                Jason.put("login",login);
                Jason.put("psw",psw);
                Jason.put("retenir","true");
                fw = new FileWriter("sharedPref.json",false);
                BufferedWriter output = new BufferedWriter(fw);
            output.write(Jason.toString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getSharedPageLogin(){
        String[] shared = new String[3];
        FileReader fr;
        JSONObject Jason;

        try {
            fr = new FileReader("sharedPref.json");

        BufferedReader input = new BufferedReader(fr);
        Jason = new JSONObject(input.readLine());
        shared[0] = Jason.get("login").toString();
        shared[1] = Jason.get("psw").toString();
        shared[2] = Jason.get("retenir").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shared;
    }


    public boolean addListe(String text) {
        boolean retour = false;
        String requete = "ajoutListe/0/"+text;
        System.out.println("j'ajoute une liste");
        try {
            curOut.writeUTF(requete);
            System.out.println("done, j'attend des retours éventuels");
            retour = curIn.readBoolean();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retour;
    }

    public String getListes(){

        System.out.println("je récupère mes listes");
        try {
            curOut.writeUTF("getGlobalListe/1/");

            String pouet = curIn.readUTF();
            System.out.println("cote client j'ai recu : "+pouet);
            return pouet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return"WTFFFFFFFFF";

    }


    public JSONArray execRequete(String requete) {
        try {
            return new JSONArray(getHTML(requete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String execStringRequete(String requete) {
        try {
            return getHTML(requete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public String getSelectItem(int p0) {

        try {
            curOut.writeUTF("getListe/0/"+String.valueOf(p0));
            return curIn.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "WTFFFFFF";
    }

    public boolean sendRequest(String requete) {
        try{
            curOut.writeUTF(requete);
            return curIn.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void suppressLogin() {
        FileWriter fw;
        JSONObject Jason;
        try {
            Jason = new JSONObject();
            Jason.put("login","");
            Jason.put("psw","");
            Jason.put("retenir","false");
            fw = new FileWriter("sharedPref.json",false);
            BufferedWriter output = new BufferedWriter(fw);
            output.write(Jason.toString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ListeCourse getListeCourse() {
        return listeCourse;
    }

    public void setListeCourse(ListeCourse listeCourse) {
        this.listeCourse = listeCourse;
    }

    public DataOutputStream getCurOut() {
        return curOut;
    }

    public void setCurOut(DataOutputStream curOut) {
        this.curOut = curOut;
    }

    public DataInputStream getCurIn() {
        return curIn;
    }

    public void setCurIn(DataInputStream curIn) {
        this.curIn = curIn;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public ControllerLoginInscription getControllerLoginInscription() {
        return controllerLoginInscription;
    }

    public void setControllerLoginInscription(ControllerLoginInscription controllerLoginInscription) {
        this.controllerLoginInscription = controllerLoginInscription;
    }
}
