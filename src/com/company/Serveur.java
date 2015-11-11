package com.company;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import JSONLibrary.*;
import static com.company.Constants.*;

/**
 * Created by Sandjiv on 06/11/2015.
 */
public class Serveur extends JFrame implements Runnable {

    ServerSocket gestSock;
    ArrayList <Connected>listConnected = new ArrayList<Connected>();
    IHMServ ihm;
    int numClient;

    Serveur(){
        super("Serveur");
        ihm = new IHMServ(this);
        setContentPane(ihm);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        try {
            gestSock = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();

    }

        public static void main(String[] args) {
            // write your code here
            System.out.println("je suis un serveur !!");
            new Serveur();
        }

    @Override
    public void run() {
        Socket sock;
        DataInputStream in;
        DataOutputStream out;
        String name;
        Connected con =null;

        try {
            sock = gestSock.accept() ;
            numClient++ ;
            new Thread(this).start(); // wait the next

            System.out.println("New ControllerLoginInscription connected");
            in = new DataInputStream(sock.getInputStream()) ;
            out = new DataOutputStream(sock.getOutputStream()) ;


            // readName
            System.out.println("j'attends de recevoir le nom via "+sock.toString());
            name = in.readUTF() ;
            System.out.println(" Done New Connected : "+name);

            // test if  connected before
            if ((con=searchList(name))!= null){
                if(con.connected){
                    //waaaaat
                    System.out.println(name+" a essayé de se connecter illégallement !!!!!");
                    out.writeBoolean(false);
                    return;
                }
                out.writeBoolean(true);
                con.connected = true ;
                con.sock = sock;
                con.out = out;
                con.in = in;
            } else {
                out.writeBoolean(true);
                con = new Connected(sock, in, out, name, true);
                listConnected.add(con) ;
                ihm.addMsg(name +" connected");
            }

            displayAllClients() ;

            boolean connected = true ;
            // attente des ordres
            ControleurOrdres interpreteur = new ControleurOrdres(this,con);

            while (connected) {
                String order = in.readUTF();
                System.out.println("ordre reçu :" +order);
                connected = interpreteur.setOrdre(order);

            }


            con.connected = false ;
            ihm.deConnected(numClient);
            displayAllClients() ;
        } catch (IOException e) {
            con.connected = false ;
            ihm.deConnected(numClient);
            displayAllClients() ;
            e.printStackTrace();
        } //attente
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * display all clients
     */
    public void displayAllClients() {
        String con ;
        System.out.println("------ connected------");
        for(Connected n:listConnected) {
            con = n.connected?"x":" " ;
            System.out.println(n.name +"("+con+")");
        }
        System.out.println("----------------------");
        ihm.displayConnected(listConnected);
    }

    /**
     * find if name already exists
     * @param name
     * @return
     */
    private Connected searchList(String name) {
        for (Connected con : listConnected)
            if(con.name.equals(name))
                return con ;

        return null ;
    }
    
}

