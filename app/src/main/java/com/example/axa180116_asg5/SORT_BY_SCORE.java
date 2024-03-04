package com.example.axa180116_asg5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 10, 2023.
  NetID: axa180116
  Description: This class contains the compare method for the player object. This method
  first compares the players by score, if the score is equal, then we compare
  by date and the most recent date takes precedence.
 */
public class SORT_BY_SCORE implements Comparator<Player> {
    public int compare(Player a, Player b) {


        // if scores are equal
        if(a.score == b.score){
            try {

                // get the times and dates of both players
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

                LocalDateTime aTime = LocalDateTime.parse(a.getDate(), f);
                LocalDateTime bTime = LocalDateTime.parse(b.getDate(), f);
                boolean isAfter = aTime.isBefore(bTime);

                // if player a is before player b, return 1 meaning player a is before b in the
                // recyclerView
                if(isAfter){
                    return 1;
                }
                else{
                    // else, return -1 meaning player b is first in the recyclerView
                    return -1;
                }
            }
            catch(DateTimeParseException e){
                e.printStackTrace();
            }
        }

        // if scores are not equal
        return b.score - a.score;
    }
}
