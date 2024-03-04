package com.example.axa180116_asg5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 10, 2023.
  NetID: axa180116
  Description: This class allows us to read from file and write to file. This class contains the
  separated File I/O and only the MainActivity class calls methods from this class
 */
public class FileProcessing {

    // reads lines in the file and converts them into an array of Player objects
    public ArrayList<Player> readFile(File path, String fileName){


        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int)readFrom.length()];

        BufferedReader reader;

        try {

            // If file doesn't already exist, create a new file
            if(!readFrom.exists()){
                readFrom.createNewFile();
            }

            // reading file from path
            reader = new BufferedReader(new FileReader(path+"/"+fileName));
            // read line
            String line = reader.readLine();

            ArrayList<Player> playerList = new ArrayList<Player>();

            // while there is player information
            while(line != null){

                // split by tab and extract information
                String[] input = line.split("\t");

                // create a new Player object
                int score = Integer.parseInt(input[1]);
                Player newplayer = new Player(input[0], score, input[2]);

                // add to list and read next line
                playerList.add(newplayer);
                line = reader.readLine();
            }
            reader.close();
            return playerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // write an array of Player objects to the file
    public void writeToFile(File path, String fileName, String content, ArrayList<Player> list){
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));

            // writing list of Player objects to file
            for(int i = 0; i<20; i++){

                writer.write((list.get(i).toString()+"\n").getBytes());

            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


