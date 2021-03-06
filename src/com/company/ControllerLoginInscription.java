package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ControllerLoginInscription extends JFrame {

    Client client;

    ControllerLoginInscription() {
        client = new Client(this);

        pageLogin();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation((dim.width/2)-getWidth()/2,(dim.height/2)+getHeight()/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack() ;
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Lancement du ControllerLoginInscription");
        new ControllerLoginInscription();
    }

    /**
     * Gestion des IHMs
     */

    public void pageLogin(){
        LoginIHM loginPage = new LoginIHM(this);
        setContentPane(loginPage);
        pack();
    }

    public void pageInscription(){
        if(client.connect("pouet")) {
            InscriptionIHM inscriptionPage = new InscriptionIHM(this);
            setContentPane(inscriptionPage);
            pack();
        }
        else{
            infoBox("serveur d'inscription occupé, veuillez réessayer plus tard","désolé");
        }
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

    public void persoLabel(Component component, Color backgroundColor){
        component.setBackground(backgroundColor);
        Font myFont = new Font("Serif", Font.BOLD, 18);
        component.setFont(myFont);
        component.setForeground(Color.WHITE);
    }
     public void persoButton(String URLImage, JButton bouton){
        Icon icoButton = new ImageIcon("res/Buttons/"+URLImage);
        bouton.setIcon(icoButton);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFocusPainted(false);
        bouton.setOpaque(false);
    }

    public void persoCheckBox(JCheckBox jCheckBox){
        jCheckBox.setDisabledIcon(new ImageIcon("/res/Buttons/DisableCheck.png"));
        jCheckBox.setSelectedIcon(new ImageIcon("/res/Buttons/EnableCheck.png"));
    }

    public void nextFen() {
        NavigationController cousteau = new NavigationController(client);
        this.dispose();
    }
}
