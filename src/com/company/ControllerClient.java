package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static com.company.Constants.*;

public class ControllerClient extends JFrame {

    Client client;

    ControllerClient() {
        client = new Client(this);
        pageLogin();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation((dim.width/2)-getWidth()/2,(dim.height/2)+getHeight()/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack() ;
        setVisible(true);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Lancement du ControllerClient");
        new ControllerClient();
    }

    /**
     * Gestion des IHMs
     */
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
        client.connect("pouet");
        InscriptionIHM inscriptionPage = new InscriptionIHM(this);
        setContentPane(inscriptionPage);
        pack();
    }

    /**
     * Controle des appels
     * @param psw1
     * @param psw2
     * @param login
     */
    public void verifInscription(String psw1,String psw2, String login) {
        if (psw2.equals(psw1)) {
            System.out.println("les psw sont égaux");
            client.inscription(login, psw1);
        }
        else{
            infoBox("Les mots de passe sont différents", "Erreur");
        }
    }

    public void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
