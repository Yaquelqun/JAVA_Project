package com.company;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static com.company.Constants.*;

public class Client extends JFrame {


    IHMClient ihm ;

    Socket sock;
    DataInputStream curIn ;
    DataOutputStream curOut ;
    boolean connected = false ;

    /**
     * start socket
     */
    Client() {
        ihm = new IHMClient(this);
        setContentPane(ihm);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack() ;
        setVisible(true);
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Lancement du Client");
        new Client() ;
    }

    /**
     * connect to the server
     */
    void connect(String nickName) {
        try {
            sock = new Socket("localhost", PORT);

            curIn = new DataInputStream(sock.getInputStream());
            curOut = new DataOutputStream(sock.getOutputStream());
            curOut.writeUTF(nickName);
            connected = true;
            pageListeCourses();

        }
        catch (IOException e) {
            infoBox("problème à la connexion","erreur");
            e.printStackTrace();
        }
    }
    /**
     *
     * @param nickName
     */
    void disconnect(String nickName) {
        try {
            curOut.writeInt(ORDER.DISCONNECT.ordinal());
            connected = false ;

            sock.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            infoBox("problème à la fermeture","erreur");
            e1.printStackTrace();
        }
    }

    public void pageListeCourses(){
        CourseListesIHM MenuPage = new CourseListesIHM(this);
        setContentPane(MenuPage);
        pack();
    }


    public void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
