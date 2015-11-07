package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Sandjiv on 06/11/2015.
 */
public class Connected {

    String name ;
    Socket sock ;
    DataInputStream in ;
    DataOutputStream out ;
    boolean connected ;

    Connected(Socket sock, DataInputStream in, DataOutputStream out, String name, boolean connected){
        this.sock = sock ;
        this.in = in ;
        this.out = out ;
        this.name = name ;
        this.connected = connected ;
    }
}
