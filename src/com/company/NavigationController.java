package com.company;

import javax.swing.*;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationController extends JFrame {
    Client client;
    NavigationIHM navigationIHM;
    public NavigationController(Client client) {
    this.client  = client;
    navigationIHM = new NavigationIHM(this);
        this.setContentPane(navigationIHM);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

    }
}
