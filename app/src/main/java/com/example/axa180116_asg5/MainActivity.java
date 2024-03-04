package com.example.axa180116_asg5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This activity contains the home page where the user can read the game instructions,
  get a randomized shape and color, and click on the play button to start the game. Once the play button
  is clicked, we take the user to the second actual game screen
 */
public class MainActivity extends AppCompatActivity {

    private TextView instructions;
    private TextView customInstColor;
    private TextView customInstShape;
    private Button startButton;
    private int[] colorAndShape = new int[2];

    // string of color options to randomize color
    private String[] colors = {"#FF0000", "#FFC000",
            "#FFFC00", "#66FF00", "#00FFFF", "#FF00FF", "#FFFFFF"};

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instructions = findViewById(R.id.editInstruction);
        startButton = findViewById(R.id.startGameButton);
        customInstColor = findViewById(R.id.editInstruction2);
        customInstShape = findViewById(R.id.editInstruction3);


        // pick a random shape and color
        int color = pickColor();
        int shape = pickShape();

        colorAndShape[0] = color;

        // change the colors of the text view to the randomized color
        customInstColor.setTextColor(color);
        customInstShape.setTextColor(color);

        // change text string depending on color selected
        switch (color){
            case -65536:
                customInstColor.setText("RED");
                break;
            case -16384:
                customInstColor.setText("ORANGE");
                break;
            case -1024:
                customInstColor.setText("YELLOW");
                break;
            case -10027264:
                customInstColor.setText("GREEN");
                break;
            case -16711681:
                customInstColor.setText("BLUE");
                break;
            case -65281:
                customInstColor.setText("PURPLE");
                break;
            case -1:
                customInstColor.setText("WHITE");
                break;

        }

        // choose a randomized shape and change text string accordingly
        if(shape %2 == 1){
            colorAndShape[1] = 0;
            customInstShape.setText(" CIRCLES");
        }
        else{
            colorAndShape[1] = 1;
            customInstShape.setText(" SQUARES");
        }

        // when button clicked, go to game activity
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameActivity();
            }
        });
    }

    // opens game activity and passes the color and shape of the object so that we
    // can keep track of the target shape and color and score user accordingly
    public void openGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("key", colorAndShape);
        startActivity(intent);
    }

    // randomly picks color
    public int pickColor(){
        int index = rand.nextInt(7 ) ;
        int randColor = Color.parseColor(colors[index]);

        return randColor;
    }

    // randomly picks shape
    public int pickShape(){return rand.nextInt(2);
    }

}