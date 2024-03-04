package com.example.axa180116_asg5;

import java.io.Serializable;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 10, 2023.
  NetID: axa180116
  Description: The player class contains the object used to store each player' name, score, and date.
  The player class implements the serializable class to allow passing this object from one acitivity
  to another.
 */

public class Player implements Serializable{

    String name;
    int score;
    String date;

    // constructor
    Player(String name, int score, String date){
        this.name = name;
        this.score = score;
        this.date = date;
    }

    // getter methods
    public String getPlayerName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    // toString method
    @Override
    public String toString() {
        return (name +'\t' + score + '\t' + date);
    }

}


