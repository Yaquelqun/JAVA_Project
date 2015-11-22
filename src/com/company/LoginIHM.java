package com.company;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class LoginIHM extends JPanel implements ActionListener,FocusListener{

    private ControllerLoginInscription controllerLoginInscription;
    private JTextField nickNameF;
    private JPasswordField passWord  = new JPasswordField("Password");
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
        super.setPreferredSize(new Dimension(300,200));
        setPreferredSize(new Dimension(300,200));
        nickNameF = new JTextField(20) ;
        nickNameF.setText("Name");

        String[] sharedPref = controllerLoginInscription.client.getSharedPageLogin();
        System.out.println(Boolean.valueOf(sharedPref[2]));
        retenir = new JCheckBox("Retenir mes identifiants");
        retenir.setBackground(Client.BACKGROUND_INV_COLOR);
        controllerLoginInscription.persoCheckBox(retenir);
        retenir.setSelected(Boolean.valueOf(sharedPref[2]));
        nickNameF.setBorder(BorderFactory.createLineBorder(Client.BACKGROUND_COLOR,3,true));
        nickNameF.addActionListener(this);
        nickNameF.addFocusListener(this);
        passWord.addActionListener(this);
        passWord.addFocusListener(this);
        if(Boolean.valueOf(sharedPref[2])){
            nickNameF.setText(sharedPref[0]);
            //nickNameF.setPreferredSize(new Dimension(10,10));
            passWord.setText(sharedPref[1]);
        }
        this.controllerLoginInscription = controllerLoginInscription;

        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel();
        JPanel panelSouth = new JPanel();


        panelNorth.add(logo);

        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelCenter.add(nickNameF);
        panelCenter.add(passWord);
        controllerLoginInscription.persoLabel(retenir, Client.BACKGROUND_INV_COLOR);
        panelCenter.add(retenir);

        controllerLoginInscription.persoButton("LoginButton.png",connectB);
        panelSouth.setBackground(Client.BACKGROUND_INV_COLOR);
        panelNorth.setBackground(Client.BACKGROUND_INV_COLOR);
        panelCenter.setBackground(Client.BACKGROUND_INV_COLOR);
        connectB.addActionListener(this);
        panelSouth.add(connectB);

        controllerLoginInscription.persoButton("SignInButton.png",inscription);
        inscription.addActionListener(this);
        panelSouth.add(inscription);


        add(panelNorth, BorderLayout.NORTH);
        panelCenter.setPreferredSize(new Dimension(100,150));
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
        ((JTextField) s).setBorder(BorderFactory.createLineBorder(Client.BACKGROUND_COLOR,3,true));
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
        ((JTextField) s).setBorder(new MatteBorder(1,1,1,1,Color.gray));
    }
}
