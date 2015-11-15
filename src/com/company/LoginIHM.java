package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class LoginIHM extends JPanel implements ActionListener,FocusListener{

    private ControllerLoginInscription controllerLoginInscription;
    private JTextField nickNameF = new JTextField("Name", 10) ;
    private JPasswordField passWord  = new JPasswordField("Password", 10);
    private JCheckBox retenir;
    private JButton connectB = new JButton();
    private JButton inscription = new JButton();
    private JLabel logo = new JLabel(new ImageIcon(new ImageIcon("logo_fox.jpg").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
    /**
     *
     * @param controllerLoginInscription
     */
    LoginIHM(final ControllerLoginInscription controllerLoginInscription) {
        super(new BorderLayout());
        String[] sharedPref = controllerLoginInscription.client.getSharedPageLogin();
        System.out.println(Boolean.valueOf(sharedPref[2]));
        if(Boolean.valueOf(sharedPref[2])){
            retenir = new JCheckBox("Retenir mes identifiants",Boolean.valueOf(sharedPref[2]));
            retenir.setBackground(Client.BACKGROUND_COLOR);
            controllerLoginInscription.persoCheckBox(retenir);
            nickNameF.setText(sharedPref[0]);
            nickNameF.addActionListener(this);
            nickNameF.addFocusListener(this);
            passWord.setText(sharedPref[1]);
            passWord.addActionListener(this);
            passWord.addFocusListener(this);
        }
        this.controllerLoginInscription = controllerLoginInscription;

        JPanel panelNorth = new JPanel();

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelNorth.add(logo);
        panelCenter.add(nickNameF);
        panelCenter.add(passWord);
        controllerLoginInscription.persoLabel(retenir, Client.BACKGROUND_COLOR);
        panelCenter.add(retenir);
        JPanel panelSouth = new JPanel();

        controllerLoginInscription.persoButton("LoginButton.png",connectB);
        panelSouth.setBackground(Color.WHITE);
        panelNorth.setBackground(Color.WHITE);
        panelCenter.setBackground(controllerLoginInscription.client.BACKGROUND_COLOR);
        connectB.addActionListener(this);
        panelSouth.add(connectB);

        controllerLoginInscription.persoButton("SignInButton.png",inscription);
        inscription.addActionListener(this);
        panelSouth.add(inscription);


        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == connectB || s == nickNameF || s == passWord){
            String login = nickNameF.getText();
            String psw = String.copyValueOf(passWord.getPassword());
            if(retenir.isValid()){
                controllerLoginInscription.client.keepLogin(login,psw);
            }
            else{
                controllerLoginInscription.client.suppressLogin();
            }
            controllerLoginInscription.client.login(login,psw);
        }
        if(s == inscription){
            controllerLoginInscription.pageInscription();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object s = e.getSource();
        if(s==nickNameF){
            if(nickNameF.getText().equals("Name")){
                nickNameF.setText("");
            }
        }
        if(s==passWord){
            if(String.copyValueOf(passWord.getPassword()).equals("Password")){
                passWord.setText("");
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        Object s = e.getSource();
        if(s == nickNameF){
            if(nickNameF.getText().equals("")){
                nickNameF.setText("Name");
            }
        }
        if(s == passWord){
            if(String.copyValueOf(passWord.getPassword()).equals("")){
                passWord.setText("Password");
            }
        }
    }
}
