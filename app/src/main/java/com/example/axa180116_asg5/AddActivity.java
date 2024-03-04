package com.example.axa180116_asg5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: In this activity the user can enter their scores and add to the recycler view in the
  score screen. The player name, score, and date fields are validated as the user types their input
  and the submit button gets disabled and activated depending on whether the fields are valid. When
  the submit button is clicked, I created a player object containing the fields and they are sent to
  the main screen to be added to the file. This class does not do any file operations.
 */

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText editTextPlayerName;
    private TextInputEditText editScore;
    private MaskedEditText editDate;
    private Button buttonConfirm;

    private String currentTime;

    TextInputLayout errorInputName, errorInputScore, errorInputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // id matching and initialization
        editTextPlayerName = findViewById(R.id.edit_text_playername);
        editScore = findViewById(R.id.edit_text_score);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        editDate = findViewById(R.id.edit_date);
        errorInputName = findViewById(R.id.textInputLayout);
        errorInputScore = findViewById(R.id.textInputLayout2);
        errorInputDate = findViewById(R.id.textInputLayout3);



        // keep track of the current time and auto-populate it as soon
        // as the app is started
        currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
        editDate.setText(currentTime);

        // as text is added into each field, we are validating each input
        editTextPlayerName.addTextChangedListener(nameTextWatcher);
        editScore.addTextChangedListener(scoreTextWatcher);
        editDate.addTextChangedListener(dateTextWatcher);

        buttonConfirm.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        int score = 0;
        if(extras != null){
            score = extras.getInt("score");
            editScore.setText(String.valueOf(score));
            editScore.setEnabled(false);
            editScore.setKeyListener(null);
        }

    }

    public void onClick(View v){
        String nameInput = editTextPlayerName.getText().toString().trim();
        String scoreInput = editScore.getText().toString().trim();
        String dateInput = editDate.getText().toString().trim();

        // When submit button is clicked, we create a player object with the user input
        int score = Integer.parseInt(scoreInput);
        Player player = new Player(nameInput,
                score , dateInput);

        // passing the player object to main activity page with the recycler view
        Intent i = new Intent(this, ScoreActivity.class);
        i.putExtra("SampleObject", player);

        // return to the main activity page
        startActivity(i);
    }


    // validate the name input, just checks to make sure that it's not empty
    private boolean validateName(String nameInput){
        if(nameInput.isEmpty()){
            errorInputName.setError("Player name cannot be empty");
            return false;
        }
        else{
            errorInputName.setError("");
            return true;
        }

    }

    // validate score
    private boolean validateScore(String scoreText){
        try
        {
            // If text is empty
            if(scoreText.isEmpty()){
                //editScore.setError("Score cannot be empty");
                errorInputScore.setError("Score cannot be empty");
                return false;
            }
            // If text is a 0
            else if(scoreText.equals("0")){
                errorInputScore.setError("Score must be a positive integer");
                return false;
            }
            else{

                // parse string score to int
                int scoreInput = Integer.parseInt(scoreText);
                // if text is a negative integer (not possible since the
                // field type is restricted to numbers only)
                if(scoreInput <= 0){
                    errorInputScore.setError("Score must be a positive integer");
                    return false;
                }
                // if score is valid, reset error and return true
                else{
                    errorInputScore.setError("");
                    return true;
                }
            }

        }
        catch (NumberFormatException e)
        {
            errorInputScore.setError("Score must be a number");
            e.printStackTrace();
            return false;
        }
    }

    // validate the date and time
    private boolean validateDate(String date){

        // reset current time so that its updated for when we need to check if the input date/time is
        // in the past
        currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        // regex of the time and date in the format : 'MM/dd/yyyy HH:mm'
        String regex = "^(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02/(0[1-9]|[1-2][0-9]))|" +
                "((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))/[0-9]{4}([0-1]?[0-9]|2[0-3]):[0-5][0-9]";

        // ignore space in user input
        String regexDate = date.replaceAll("\\s+","");

        // check that date isn't empty
        if(date.isEmpty()){
            errorInputDate.setError("Date cannot be empty");
            return false;
        }
        else if(!regexDate.matches(regex)){
            //make sure input matches the format of the regex
            errorInputDate.setError("Date must be valid and in this format: " +
                    "MM/DD/YYYY HH:MM");
            return false;
        }
        else{

            try {

                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

                //String currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
                String inputTime = date;
                LocalDateTime current = LocalDateTime.parse(currentTime, f);
                LocalDateTime input = LocalDateTime.parse(inputTime, f);
                boolean isAfter = current.isBefore(input);

                // check to see if the input date is before the current date
                if (isAfter) {
                    errorInputDate.setError("Date cannot be in the future");
                    return false;
                }
                else if(current.isEqual(input)){
                    // If equal, set the error equal to empty string and return true
                    errorInputDate.setError("");
                    return true;
                }
                else {
                    errorInputDate.setError("");
                    return true;
                }
            }
            catch(DateTimeParseException e){
                e.printStackTrace();
                errorInputDate.setError("");
                return false;
            }

        }
    }


    // Used to observe changes in the score text field and gray-out button and validate it
    private TextWatcher scoreTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String dateInput = editDate.getText().toString().trim();
            String scoreText = editScore.getText().toString().trim();
            String nameInput = editTextPlayerName.getText().toString().trim();

            if(validateDate(dateInput) && validateName(nameInput) && validateScore(scoreText)){
                buttonConfirm.setEnabled(true);
            }
            else{
                buttonConfirm.setEnabled(false);
            }

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

    // Used to observe changes in the player name text field and gray-out button and validate it
    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
            String dateInput = editDate.getText().toString().trim();
            String scoreText = editScore.getText().toString().trim();
            String nameInput = editTextPlayerName.getText().toString().trim();

            if(validateDate(dateInput) && validateName(nameInput) && validateScore(scoreText)){
                buttonConfirm.setEnabled(true);
            }
            else{
                buttonConfirm.setEnabled(false);
            }

        }
    };

    // Used to observe changes in the date text field and gray-out button and validate it
    private TextWatcher dateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String dateInput = editDate.getText().toString().trim();
            String scoreText = editScore.getText().toString().trim();
            String nameInput = editTextPlayerName.getText().toString().trim();

            if(validateDate(dateInput) && validateName(nameInput) && validateScore(scoreText)){
                buttonConfirm.setEnabled(true);
            }
            else{
                buttonConfirm.setEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

}