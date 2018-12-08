package com.company;

import java.util.Random;

public class FeedBackMechanism {
    private String[] server_Status = null;
    private int counter = 0;

    public FeedBackMechanism() {
        this.server_Status = new String[] {"NOT_CONGESTED", "MODERATELY_CONGESTED", "CONGESTED"};
    }

    public String currentNodeStatus(){
            return server_Status[getIndex()];
    }

    private int getIndex(){
        Random rand = new Random();

        return rand.nextInt(3);
    }

    public int duration_of_Status(){
        Random rand = new Random();

        //Using (max-min+1) + 1 -- between 100 - 10000 milliseconds
        return rand.nextInt(10000-100+1) + 1;
    }
}
