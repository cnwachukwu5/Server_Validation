package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Threads implements Runnable{

    private final Socket cacheNode;
    private List<String> listFromClient = new ArrayList<>();

    public Threads(Socket cacheNode){

        this.cacheNode = cacheNode;
    }

    @Override
    public void run() {
        try {
            FeedBackMechanism feedBackMechanism = new FeedBackMechanism();
            PrintWriter output = new PrintWriter(cacheNode.getOutputStream(), true);
            BufferedReader fromCacheNodes = new BufferedReader(new InputStreamReader(cacheNode.getInputStream()));//Read from input

            System.out.println("Connected to server");

            while(true){
                String server_status = feedBackMechanism.currentNodeStatus();

                int status_duration = feedBackMechanism.duration_of_Status();

                System.out.println("Server status: " + server_status);
                System.out.println("Duration: " + status_duration);
                boolean end = false;


                long start_Duration_Monitor = System.currentTimeMillis();
                long end_Duration_Monitor = 0;

                while ((end_Duration_Monitor - start_Duration_Monitor) <= status_duration) {
                    output.println(server_status);
                    String from_IoTNode = fromCacheNodes.readLine();

                    System.out.println("Received: " + from_IoTNode);

                    if(from_IoTNode != null){
                        if(from_IoTNode.equals("EODATA")){
                            end = true;
                            break;
                        }else if(from_IoTNode.equals("ODE")){
                           //Ignore data
                        }else{
                            listFromClient.add(from_IoTNode);
                        }

                    }

                    end_Duration_Monitor = System.currentTimeMillis();
                }
                if (end)
                    break;
                //Thread.sleep(status_duration);
            }

            System.out.println("Total received: " + listFromClient.size());
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
