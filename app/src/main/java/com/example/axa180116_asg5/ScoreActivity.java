package com.example.axa180116_asg5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/*
  HW 5: Balloon Game

  Written by Aashritha Ananthula for CS6326.001, assignment 5, starting April 22, 2023.
  NetID: axa180116
  Description: This class contains the score screeb with the recycler view. This class also handles
  File I/O by calling the FileProcessing class to add players. When the add icon on the
  action bar is clicked, we go to the AddActivity of the add screen. When the user is finished
  adding a player, we get the player object back to this class and add the player to the file

 */

public class ScoreActivity extends AppCompatActivity {

    private ArrayList<Player> playersList = new ArrayList<Player>();

    private FileProcessing fp = new FileProcessing();
    private File path;
    private String fileName = "file.txt";

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // file is saved in this path
        path =  getApplicationContext().getFilesDir();
        playersList = fp.readFile(path, fileName);


        // if list of players has items
        if(!playersList.isEmpty()){

            // sort the players by score
            Collections.sort(playersList, new SORT_BY_SCORE());

        }

        // add player items to recycler view once they're sorted
        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);

        myAdapter = new PlayerAdapter(playersList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

    }

    @Override
    protected void onResume() {
        Bundle extras = getIntent().getExtras();

        // when there's a player getting passed back
        if(extras != null){

            Intent i = getIntent();
            Player playerAdded  = (Player)i.getSerializableExtra("SampleObject");

            // Add that player to the list
            playersList.add(playerAdded);

            // If there's more than one player in the list, sort by score
            if(playersList.size()>1){
                Collections.sort(playersList, new SORT_BY_SCORE());
            }

            // write the new set of players to the file
            fp.writeToFile(path, fileName, "", playersList);
        }

        super.onResume();
    }

    // used for the add button in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return true;
    }

    // When add button clicked, move to the add activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.AddNew ){
            openActivity2();
        }
        return super.onOptionsItemSelected(item);
    }


    // move to add activity screen
    public void openActivity2(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}