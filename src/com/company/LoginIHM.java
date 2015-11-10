package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class LoginIHM extends JPanel implements ActionListener{

    private ControllerClient controllerClient;
    private JTextField nickNameF = new JTextField("Name", 10) ;
    private JTextField passWord  = new JTextField("Password", 10);
    private JButton connectB = new JButton("Connect");
    private JButton inscription = new JButton("Inscription");
    private JLabel logo = new JLabel(new ImageIcon("logo_fox.jpg"));
    /**
     *
     * @param controllerClient
     */
    LoginIHM(final ControllerClient controllerClient) {
        super(new BorderLayout());
        this.controllerClient = controllerClient;
        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelNorth.add(logo);
        panelCenter.add(nickNameF);
        panelCenter.add(passWord);
        JPanel panelSouth = new JPanel() ;
        connectB.addActionListener(this);
        panelSouth.add(connectB);
        inscription.addActionListener(this);
        panelSouth.add(inscription);
        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if(s == connectB){
            controllerClient.client.login(nickNameF.getText(), passWord.getText());
        }
        if(s == inscription){
            controllerClient.pageInscription();
        }
    }
}
