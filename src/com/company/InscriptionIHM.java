package com.company;

import JSONLibrary.JSONObject;
import com.intellij.codeInsight.template.postfix.templates.SoutPostfixTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.company.Constants.PORT;

/**
 * Created by Sandjiv on 09/11/2015.
 */
public class InscriptionIHM extends JPanel {

    Dimension fenSize = new Dimension(300,120);//TODO : chercher comment faire ça élegamment
    JTextField login;
    JTextField psw;
    JTextField psw2;
    JButton Confirm;
    JButton Cancel;
    JPanel panelChamps;
    JPanel panelBoutons;

    public InscriptionIHM(final Client client) {
        //super(new BorderLayout());
        super.setPreferredSize(fenSize);
        client.connect("pouet");
        setPreferredSize(fenSize);

        //TODO : trouver comment  mettre le texte en overlay (CMOCHE)
        login = new JTextField(20);
        login.setText("votre Login");
        psw = new JTextField(20);
        psw.setText("votre mot de passe");
        psw2 = new JTextField(20);
        psw2.setText("votre mot de passe");

        Confirm = new JButton("Confirmer");
        Confirm.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                System.out.println("click sur confirmer");
                if(psw2.getText().equals(psw.getText())){
                    System.out.println("les psw sont égaux");
                    JSONObject nouveauinscrit = new JSONObject();
                    nouveauinscrit.accumulate("login",login.getText());
                    nouveauinscrit.accumulate("psw",psw.getText());
                    try {

                        client.curOut.writeUTF("inscription/"+nouveauinscrit.toString());
                        System.out.println("J attends une réponse ... via"+client.sock.toString());
                        boolean unique = client.curIn.readBoolean();
                        System.out.println("c'est fait !");
                        if(!unique){
                            System.out.println("login existe déjà !!!");
                            client.infoBox("ce login est déjà pris","dommage");
                        }
                        else{
                            System.out.println("je déconnecte pouet");
                            client.infoBox("inscription réussie","félicitation");
                            client.disconnect("pouet");
                            client.pageLogin();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        Cancel = new JButton("Annuler");
        Cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!client.sock.isClosed()) client.disconnect("pouet");
                client.pageLogin();
            }
        });
        panelChamps = new JPanel();
        panelChamps.setLayout(new BoxLayout(panelChamps,BoxLayout.Y_AXIS));
        panelChamps.add(login);
        panelChamps.add(psw);
        panelChamps.add(psw2);
        panelBoutons = new JPanel(new FlowLayout());
        panelBoutons.add(Confirm);
        panelBoutons.add(Cancel);
        add(panelChamps);
        add(panelBoutons);
    }
}
