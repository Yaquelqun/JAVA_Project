package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class LoginIHM extends JPanel implements ActionListener{

    private ControllerLoginInscription controllerLoginInscription;
    private JTextField nickNameF = new JTextField("Name", 10) ;
    private JTextField passWord  = new JTextField("Password", 10);
    private JCheckBox retenir;
    private JButton connectB = new JButton("Connexion");
    private JButton inscription = new JButton("Inscription");
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
            retenir = new JCheckBox("Retenir mes indentifiants",Boolean.valueOf(sharedPref[2]));
            retenir.setBackground(Color.WHITE);
            nickNameF.setText(sharedPref[0]);
            passWord.setText(sharedPref[1]);
        }
        this.controllerLoginInscription = controllerLoginInscription;

        JPanel panelNorth = new JPanel();

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelNorth.add(logo);
        panelCenter.add(nickNameF);
        panelCenter.add(passWord);
        panelCenter.add(retenir);
        JPanel panelSouth = new JPanel();

<<<<<<< HEAD
        controllerLoginInscription.persoButton("res/LoginButton.png",connectB);
=======
        panelSouth.setBackground(Color.WHITE);
        panelNorth.setBackground(Color.WHITE);
        panelCenter.setBackground(Color.WHITE);

        /*Icon loginButton = new ImageIcon("res/LoginButton.png");
        connectB.setIcon(loginButton);
        connectB.setBorderPainted(false);
        connectB.setContentAreaFilled(false);
        connectB.setFocusPainted(false);
        connectB.setOpaque(false);*/
        connectB.setBorderPainted(false);
        connectB.setBackground(Color.WHITE);
        connectB.setBorder(BorderFactory.createMatteBorder(0,0,10,5,Color.cyan));
>>>>>>> refs/remotes/origin/integration
        connectB.addActionListener(this);
        connectB.repaint();
        panelSouth.add(connectB);

<<<<<<< HEAD
        controllerLoginInscription.persoButton("res/NewButton.png",inscription);
=======
/*        Icon inscrButton = new ImageIcon("res/InscrButton.png");
        inscription.setIcon(inscrButton);
        inscription.setBorderPainted(false);
        inscription.setContentAreaFilled(false);
        inscription.setFocusPainted(false);
        inscription.setOpaque(false);
        inscription.addActionListener(this);*/
        inscription.setBorderPainted(false);
        inscription.setBackground(Color.WHITE);
>>>>>>> refs/remotes/origin/integration
        inscription.addActionListener(this);
        inscription.setBorder(BorderFactory.createMatteBorder(0,0,10,5,Color.cyan));
        inscription.repaint();
        panelSouth.add(inscription);


        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == connectB){
            String login = nickNameF.getText();
            String psw = passWord.getText();
            if(retenir.isValid()){
                controllerLoginInscription.client.keepLogin(login,psw);
            }
            controllerLoginInscription.client.login(login,psw);
        }
        if(s == inscription){
            controllerLoginInscription.pageInscription();
        }
    }
}
