package com.company;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Sandjiv on 09/11/2015.
 */
public class InscriptionIHM extends JPanel implements ActionListener, FocusListener {

    ControllerLoginInscription controllerLoginInscription;
    Dimension fenSize = new Dimension(300,120);//TODO : chercher comment faire ça élegamment
    JTextField login;
    JTextField psw;
    JTextField psw2;
    JButton Confirm;
    JButton Cancel;
    JPanel panelChamps;
    JPanel panelBoutons;

    public InscriptionIHM(final ControllerLoginInscription controllerLoginInscription) {
        //super(new BorderLayout());
        this.controllerLoginInscription = controllerLoginInscription;
        super.setPreferredSize(fenSize);
        setPreferredSize(fenSize);
        setBackground(controllerLoginInscription.client.BACKGROUND_INV_COLOR);

        login = new JTextField(20);
        login.setText("votre Login");
        psw = new JTextField(20);
        psw.setText("votre mot de passe");
        psw2 = new JTextField(20);
        psw2.setText("votre mot de passe");

        login.addActionListener(this);
        login.addFocusListener(this);
        psw.addActionListener(this);
        psw.addFocusListener(this);
        psw2.addActionListener(this);
        psw2.addFocusListener(this);

        Confirm = new JButton("Confirmer");
        Confirm.addActionListener(this);
        Cancel = new JButton("Annuler");
        Cancel.addActionListener(this);

        panelChamps = new JPanel();
        panelChamps.setBackground(controllerLoginInscription.client.BACKGROUND_INV_COLOR);
        panelChamps.setLayout(new BoxLayout(panelChamps,BoxLayout.Y_AXIS));
        panelChamps.add(login);
        panelChamps.add(psw);
        panelChamps.add(psw2);
        panelBoutons = new JPanel(new FlowLayout());
        panelBoutons.setBackground(controllerLoginInscription.client.BACKGROUND_INV_COLOR);
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
            controllerLoginInscription.verifInscription(psw.getText(),psw2.getText(),login.getText());
        }
        if(s == Cancel){
            controllerLoginInscription.client.inscriptionAbort();
        }
        if (s.getClass().equals(login.getClass())){
            System.out.println("mouallez");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object s = e.getSource();
        ((JTextField) s).setText("");
        ((JTextField) s).setBorder(BorderFactory.createLineBorder(controllerLoginInscription.client.BACKGROUND_COLOR,3,true));

    }

    @Override
    public void focusLost(FocusEvent e) {
        Object s = e.getSource();
        if(((JTextField) s).getText().equals("")) {
            if (s == login) {
                login.setText("Votre Login");
                login.setBorder(new MatteBorder(1,1,1,1,Color.gray));
            } else if (s == psw) {
                psw.setText("Votre Mot de passe");
                psw.setBorder(new MatteBorder(1,1,1,1,Color.gray));
            } else if (s == psw2) {
                psw2.setText("Votre mot de passe");
                psw2.setBorder(new MatteBorder(1,1,1,1,Color.gray));
            }
        }
    }
}
