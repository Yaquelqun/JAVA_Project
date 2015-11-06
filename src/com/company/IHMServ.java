package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Sandjiv on 06/11/2015.
 */
public class IHMServ extends JPanel {

    private Serveur serv;
    JButton quitB = new JButton("Quit");
    JLabel textA = new JLabel("Wait for connection");
    JList<String> connectL = new JList<String>();
    JScrollPane scrollPane = new JScrollPane(connectL);

    IHMServ(Serveur serv) {
        super(new BorderLayout());
        this.serv = serv;
        textA.setForeground(Color.DARK_GRAY);

        connectL.setCellRenderer(new MyCellRenderer());
        add(textA, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(quitB, BorderLayout.SOUTH);

        quitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    void addMsg(String msg) {
        textA.setText(msg);
    }

    /**
     * display in the JList
     */
    public void displayConnected(ArrayList<Connected> listName) {
        String[] names = new String[listName.size()];
        int i = 0;
        for (Connected con : listName)
            names[i++] = con.name;

        connectL.setListData(names);
    }

    /**
     * repaint for displaying deconnected
     *
     * @param numClient
     */
    public void deConnected(int numClient) {
        connectL.repaint();
    }

// Display an icon and a string for each object in the list.

    class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
        final ImageIcon iconGrey = new ImageIcon(new ImageIcon("butGrey.png").getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT));
        final ImageIcon iconGreen = new ImageIcon(new ImageIcon("butGreen.png").getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT));

        //     final static ImageIcon shortIcon = new ImageIcon("short.gif");

        // This is the only method defined by ListCellRenderer.
        // We just reconfigure the JLabel each time we're called.

        public Component getListCellRendererComponent(
                JList<?> list,           // the list
                Object value,            // value to display
                int index,               // cell index
                boolean isSelected,      // is the cell selected
                boolean cellHasFocus)    // does the cell have focus
        {
            String s = value.toString();
            setText(s);
            if (serv.listConnected.get(index).connected)
                setIcon(iconGreen);
            else
                setIcon(iconGrey);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
}