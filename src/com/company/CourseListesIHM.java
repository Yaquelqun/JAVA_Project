package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Sandjiv on 07/11/2015.
 */
public class CourseListesIHM extends JPanel {

    private Client client;
    Dimension fenSize = new Dimension(300,200);


    public CourseListesIHM(Client client) {
        this.client = client;
        this.client.setPreferredSize(fenSize);
        this.setPreferredSize(fenSize);

        try {
            client.curOut.writeUTF("je donne des ordres woulalala");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
