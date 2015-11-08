package com.company;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import static com.company.Constants.*;
import libs.*;

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

            System.out.println("New Client connected");
            in = new DataInputStream(sock.getInputStream()) ;
            out = new DataOutputStream(sock.getOutputStream()) ;


            // readName
            name = in.readUTF() ;
            System.out.println("New Connected : "+name);

            // test if  connected before
            if ((con=searchList(name))!= null){
                con.connected = true ;
            } else {
                con = new Connected(sock, in, out, name, true);
                listConnected.add(con) ;
                ihm.addMsg(name +" connected");
            }

            displayAllClients() ;

            boolean connected = true ;
            // attente des ordres
            while (connected) {
                String order = in.readUTF();
                System.out.println("ordre reçu :" +order);
                if(order.equals("disconnect")){
                    con.connected = false ;
                    ihm.deConnected(numClient);
                    displayAllClients() ;
                    connected=false ;
                }
                if(order.equals("MasterRequest")){
                    System.out.println(getHTML("https://www.mastercourses.com/api2/chains/1/stores/?scope=min&mct=hieCaig6Oth2thiem7eiRiechufooWix"));
                    org.json.JSONObject jsonObj = new libs.JS("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
                }
            }
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
    private void displayAllClients() {
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

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}

