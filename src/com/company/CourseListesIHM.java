package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class CourseListesIHM extends JPanel implements ActionListener {

    private Client client;
    Dimension fenSize = new Dimension(300,200);
    JButton request;

    public CourseListesIHM(Client client) {
        this.client = client;
        this.client.setPreferredSize(fenSize);
        this.setPreferredSize(fenSize);
        request = new JButton("requêêêêêêêêête");
        this.add(request);
        request.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            client.curOut.writeUTF("MasterRequest");
        } catch (IOException p) {
            p.printStackTrace();
        }
    }
}