package com.company;
import JSONLibrary.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Sandjiv on 09/11/2015.
 */
public class InscriptionIHM extends JPanel implements ActionListener{

    ControllerClient controllerClient;
    Dimension fenSize = new Dimension(300,120);//TODO : chercher comment faire ça élegamment
    JTextField login;
    JTextField psw;
    JTextField psw2;
    JButton Confirm;
    JButton Cancel;
    JPanel panelChamps;
    JPanel panelBoutons;

    public InscriptionIHM(final ControllerClient controllerClient) {
        //super(new BorderLayout());
        this.controllerClient = controllerClient;
        super.setPreferredSize(fenSize);
        setPreferredSize(fenSize);

        //TODO : trouver comment  mettre le texte en overlay (CMOCHE)
        login = new JTextField(20);
        login.setText("votre Login");
        psw = new JTextField(20);
        psw.setText("votre mot de passe");
        psw2 = new JTextField(20);
        psw2.setText("votre mot de passe");

        Confirm = new JButton("Confirmer");
        Confirm.addActionListener(this);
        Cancel = new JButton("Annuler");
        Cancel.addActionListener(this);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == Confirm){
            System.out.println("click sur confirmer");
            controllerClient.verifInscription(psw.getText(),psw2.getText(),login.getText());
        }
        if(s == Cancel){
            controllerClient.client.inscriptionAbort();
        }
    }
}
