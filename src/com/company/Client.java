package com.company;

import JSONLibrary.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.company.Constants.PORT;

/**
 * Created by Loriane on 10/11/2015.
 */
public class Client {
    ControllerClient controllerClient;
    Socket sock;
    DataInputStream curIn ;
    DataOutputStream curOut ;
    boolean connected = false ;

    /**
     * connect to the server
     **/

    Client(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
    }

    /**
     * start socket
     */
    void connect(String nickName) {
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
                controllerClient.infoBox("cette personne est déjà connectée","illegal Login");
                connected = false;
                sock.close();
                controllerClient.pageLogin();
            }
            else {
                connected = true;
                System.out.println(nickName + " est connecté");
            }

        }
        catch (IOException e) {
            controllerClient.infoBox("problème à la connexion","erreur");
            e.printStackTrace();
        }
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
            controllerClient.infoBox("problème à la fermeture","erreur");
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
                controllerClient.infoBox("ce login est déjà pris","dommage");
            }
            else{
                System.out.println("je déconnecte pouet");
                controllerClient.infoBox("inscription réussie","félicitation");
                disconnect("pouet");
                controllerClient.pageLogin();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    void inscriptionAbort(){
        if(!sock.isClosed()) disconnect("pouet");
        controllerClient.pageLogin();
    }
}
