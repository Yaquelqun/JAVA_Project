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
    Dimension fenSize = new Dimension(300,140);//TODO : chercher comment faire ça élegamment
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
        setBackground(Client.BACKGROUND_INV_COLOR);

        login = new JTextField(20);
        login.setText("Votre Login");
        psw = new JTextField(20);
        psw.setText("Votre mot de passe");
        psw2 = new JTextField(20);
        psw2.setText("Votre mot de passe");

        login.addFocusListener(this);
        psw.addFocusListener(this);
        psw2.addFocusListener(this);

        Confirm = new JButton();
        Confirm.addActionListener(this);
        controllerLoginInscription.persoButton("OKButton.png",Confirm);
        Cancel = new JButton();
        controllerLoginInscription.persoButton("CancelButton.png",Cancel);
        Cancel.addActionListener(this);

        panelChamps = new JPanel();
        panelChamps.setBackground(Client.BACKGROUND_INV_COLOR);
        panelChamps.setLayout(new BoxLayout(panelChamps,BoxLayout.Y_AXIS));
        panelChamps.add(login);
        panelChamps.add(psw);
        panelChamps.add(psw2);
        panelBoutons = new JPanel(new FlowLayout());
        panelBoutons.setPreferredSize(new Dimension(300,70));
        panelBoutons.setBackground(Client.BACKGROUND_INV_COLOR);
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
            Client.getClient().inscriptionAbort();
            controllerLoginInscription.pageLogin();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object s = e.getSource();
        if (s == login) {
            if(login.getText().equals("Votre Login")){
                login.setText("");
            }
        }
        if (s == psw) {
            if (psw.getText().equals("Votre mot de passe")) {
                psw.setText("");
            }
        }
        if (s == psw2) {
            if(psw2.getText().equals("Votre mot de passe")){
                psw2.setText("");
            }
        }
        ((JTextField) s).setBorder(BorderFactory.createLineBorder(Client.BACKGROUND_COLOR,3,true));

    }

    @Override
    public void focusLost(FocusEvent e) {
        Object s = e.getSource();
        if(((JTextField) s).getText().equals("")) {
            if (s == login) {
                login.setText("Votre Login");
            } else if (s == psw) {
                psw.setText("Votre mot de passe");
            } else if (s == psw2) {
                psw2.setText("Votre mot de passe");
            }
        }
        ((JTextField) s).setBorder(new MatteBorder(1,1,1,1,Color.gray));
    }
}
