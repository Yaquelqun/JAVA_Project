package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class IHMClient extends JPanel{

    private ControllerClient controllerClient;
    private JTextField nickNameF = new JTextField("Name", 10) ;
    private JTextField PassWord  = new JTextField("Password", 10);
    private JButton connectB = new JButton("Connect");
    private JButton inscription = new JButton("Inscription");
    private JLabel logo = new JLabel(new ImageIcon("logo_fox.jpg"));
    /**
     *
     * @param controllerClient
     */
    IHMClient(final ControllerClient controllerClient) {
        super(new BorderLayout());
        this.controllerClient = controllerClient;
        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelNorth.add(logo);
        panelCenter.add(nickNameF);
        panelCenter.add(PassWord);
        JPanel panelSouth = new JPanel() ;
        panelSouth.add(connectB);
        panelSouth.add(inscription);
        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
        connectB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!controllerClient.connected) {
                    controllerClient.connect(nickNameF.getText()) ;
                    controllerClient.pageListeCourses();
                } else {
                    controllerClient.disconnect(nickNameF.getText()) ;
                    connectB.setText("Connect");
                }
                controllerClient.pack();
            }
        });

        inscription.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                controllerClient.pageInscription();
            }
        });
    }

}
