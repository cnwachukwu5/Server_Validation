package com.company;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;

    private static void init(){
        int port = 56123;
        try{
            serverSocket = new ServerSocket(port);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
	    init();
        Socket iot_Node = null;
        while(true){
            try{
                //Listen for connection
                System.out.println("Waiting for connection from IOTNode");
                iot_Node = serverSocket.accept();
                new Thread(new Threads(iot_Node)).start();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
