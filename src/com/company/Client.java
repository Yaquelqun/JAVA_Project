package com.company;

import JSONLibrary.JSONObject;

import java.io.*;
import java.net.Socket;

import static com.company.Constants.PORT;

/**
 * Created by Loriane on 10/11/2015.
 */
public class Client {
    ControllerLoginInscription controllerLoginInscription;
    Socket sock;
    DataInputStream curIn ;
    DataOutputStream curOut ;
    String userName;
    boolean connected = false ;

    /**
     * connect to the server
     **/

    Client(ControllerLoginInscription controllerLoginInscription) {
        this.controllerLoginInscription = controllerLoginInscription;
    }

    /**
     * start socket
     */
    boolean connect(String nickName) {
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
    void disconnect(String nickName) {
        try {
            curOut.writeUTF("disconnect/"+nickName);
            connected = false ;

            sock.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            controllerLoginInscription.infoBox("problème à la fermeture","erreur");
            e1.printStackTrace();
        }
    }

    void inscription(String login, String psw){
        JSONObject nouveauinscrit = new JSONObject();
        nouveauinscrit.accumulate("login",login);
        nouveauinscrit.accumulate("psw",psw);
        try {
            curOut.writeUTF("inscription/"+nouveauinscrit.toString());
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

    void inscriptionAbort(){
        if(!sock.isClosed()) disconnect("pouet");
        controllerLoginInscription.pageLogin();
    }

    void login(String login, String psw){
        connect(login);
        JSONObject nouveaumonsieur = new JSONObject();
        nouveaumonsieur.accumulate("login",login);
        nouveaumonsieur.accumulate("psw",psw);
        System.out.println(nouveaumonsieur.toString());
        try {
            curOut.writeUTF("connexion/"+nouveaumonsieur.toString());
            System.out.println("J attends une réponse ... via"+ sock.toString());
            boolean unique = curIn.readBoolean();
            System.out.println("c'est fait !");
            if(!unique){
                System.out.println("login existe déjà !!!");
                controllerLoginInscription.infoBox("erreur login/mdp","dommage");
            }
            else{
                controllerLoginInscription.infoBox("connection réussie","félicitation");
                userName = login;
                controllerLoginInscription.nextFen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void requete(){
        try {
            curOut.writeUTF("MasterRequest/");
        } catch (IOException p) {
            p.printStackTrace();
        }
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
}
