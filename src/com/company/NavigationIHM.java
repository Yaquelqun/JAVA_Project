package com.company;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class NavigationIHM extends JPanel {
    NavigationController navigationController;
    Dimension fenSize = new Dimension(300,600);
    JPanel header;
    JPanel navigation;
    JLabel imageHeader;
    JLabel textHeader;
    JMenu parametre;

    public NavigationIHM(NavigationController navigationController) {
        this.navigationController = navigationController;
        super.setPreferredSize(fenSize);
        this.setSize(fenSize);
        setHeader();
    }

    private void setHeader() {
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(300,50));
        JPanel panelWest = new JPanel(new FlowLayout());
        JPanel panelEast = new JPanel();

        imageHeader = new JLabel(new ImageIcon((new ImageIcon("butGreen.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT))));
        textHeader = new JLabel(navigationController.client.userName);
        panelWest.add(imageHeader);
        panelWest.add(textHeader);
        header.add(panelWest,BorderLayout.WEST);

        parametre = new JMenu("sonpère");
        panelEast.add(parametre);
        header.add(panelEast,BorderLayout.EAST);
        add(header);
    }
}
