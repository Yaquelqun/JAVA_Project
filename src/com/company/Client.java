package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static com.company.Constants.*;

public class Client extends JFrame {


    IHMClient ihm ;

    Socket sock;
    DataInputStream curIn ;
    DataOutputStream curOut ;
    boolean connected = false ;

    /**
     * start socket
     */
    Client() {
        ihm = new IHMClient(this);
        setContentPane(ihm);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation((dim.width/2)-getWidth()/2,(dim.height/2)+getHeight()/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack() ;
        setVisible(true);
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Lancement du Client");
        new Client() ;
    }

    /**
     * connect to the server
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
                infoBox("cette personne est déjà connectée","illegal Login");
                connected = false;
                sock.close();
                pageLogin();
            }
            else {
                connected = true;
                System.out.println(nickName + " est connecté");
            }

        }
        catch (IOException e) {
            infoBox("problème à la connexion","erreur");
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
            infoBox("problème à la fermeture","erreur");
            e1.printStackTrace();
        }
    }

    public void pageListeCourses(){
        CourseListesIHM MenuPage = new CourseListesIHM(this);
        setContentPane(MenuPage);
        pack();
    }

    public void pageLogin(){
        IHMClient loginPage = new IHMClient(this);
        setContentPane(loginPage);
        pack();
    }

    public void pageInscription(){
        InscriptionIHM inscriptionPage = new InscriptionIHM(this);
        setContentPane(inscriptionPage);
        pack();
    }


    public void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
