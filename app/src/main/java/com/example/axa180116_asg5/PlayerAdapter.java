package com.example.axa180116_asg5;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 10, 2023.
  NetID: axa180116
  Description: The PlayerAdapter class is a helper class for the recyclerView in the Main Activity.
  This class recieves an arrayList of players and converts that into individual cards to populate the
  table in the main activity.
 */

class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final int limit = 20;
    private ArrayList<Player> myPlayersList;
    public static class PlayerViewHolder extends RecyclerView.ViewHolder{

        public TextView playerNameView;
        public TextView scoreView;
        public TextView dateView;

        public TextView ranKView;

        public CardView cardView;

        // set each TextView and CardView by ID
        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            ranKView = itemView.findViewById(R.id.rankVal);
            playerNameView = itemView.findViewById(R.id.nameVal);
            scoreView = itemView.findViewById(R.id.scoreVal);
            dateView = itemView.findViewById(R.id.dateVal);
        }
    }

    // set Players list
    public PlayerAdapter(ArrayList<Player> players){
        myPlayersList = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        PlayerViewHolder pvh = new PlayerViewHolder(v);
        return pvh;
    }

    // set Player objects to the CardView
    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        Player currentPlayer = myPlayersList.get(position);

        // If position is even, make it a different color for readability
        if((position+1)%2 == 0){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#DDEBF7"));
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.ranKView.setText(Integer.toString(position+1));
        holder.playerNameView.setText(currentPlayer.getPlayerName());
        holder.scoreView.setText(String.valueOf(currentPlayer.getScore()));
        holder.dateView.setText(currentPlayer.getDate());


    }

    // returns count of Players list, limit to 20 entries in the
    // recyclerView
    @Override
    public int getItemCount() {
        if(myPlayersList.size() > limit){
            return limit;
        }
        else{
            return myPlayersList.size();
        }
    }
}