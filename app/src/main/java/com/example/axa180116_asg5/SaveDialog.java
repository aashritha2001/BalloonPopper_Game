package com.example.axa180116_asg5;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.color.utilities.Score;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This class contains the save dialog that a user can click after the game
  is over.
 */
public class SaveDialog extends AppCompatDialogFragment {

    int score;
    int numBalloonsMissed;
    int doWeAdd;


    // constructor
    public SaveDialog(int score, int numBalloonsMissed, int addNum) {
        this.score = score;
        this.numBalloonsMissed = numBalloonsMissed;
        this.doWeAdd = addNum;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // If the score is 0, don't let the user save it, let them either view the score board or
        // play again
        if(doWeAdd == 1){
            builder.setTitle("Game Over").setMessage("Your Score: " + score +  "\nMissed Balloon Count: "+ numBalloonsMissed +
                    "\nYour score does not make the highscore board. 0s cannot be added").setPositiveButton
                    ("Play again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("score", score);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton("See high scores", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), ScoreActivity.class);
                    startActivity(intent);
                }
            });
        }

        // If user's score makes it to high score, then they must save their score,
        // take them to the add score screen
        else if(doWeAdd == 2){
            builder.setTitle("Game Over").setMessage("Your Score: " + score +  "\nMissed Balloon Count: "+ numBalloonsMissed +
                    "\nYour score made the highscore board! Save you score!").setPositiveButton("Ok.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), AddActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                }
            });
        }

        // if user's score isn't greater than the lowest score in the score board, don't
        // add their score to the board, give them an option to play again or view high scores
        else if(doWeAdd == 3){
            builder.setTitle("Game Over").setMessage("Your Score: " + score +  "\nMissed Balloon Count: "+ numBalloonsMissed +
                    "\nYour score did not make the highscore board").setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), AddActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("See high scores", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), ScoreActivity.class);
                    startActivity(intent);
                }
            });
        }

        return builder.create();
    }
}