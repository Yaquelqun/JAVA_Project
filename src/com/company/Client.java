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


        }
        catch (IOException e) {
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
            e1.printStackTrace();
        }
    }
}
