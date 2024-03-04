package com.example.axa180116_asg5;

import static android.graphics.RectF.intersects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import android.os.Handler;
import java.util.logging.LogRecord;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This is a custom view that handles the game view and what the user sees at the
  bottom 2/3 of the game activity screen. This view handles threads to recalculate balloon
  positions, timer decrementing, checking for the hot sensor to be activated,
   and other interactions of the user.
 */

public class MyView extends View {

    // 60 second timer
    private static final long START_TIME_IN_MILLIS = 60000;

    private TextView mTextViewTimer;
    private TextView mTextViewScore;

    private int goalColor;
    private int goalShape;
    private int poppedBalloons = 0;
    private int poppedBalloonsTimerCt = 0;

    private int score = 0;
    private int numBalloonsMissed = 0;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private File path;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private List<GameObject> balloonList;

    private boolean isEmpty = true;

    private Handler handler;
    private boolean isSensorOn;

    FragmentManager fragManager;

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context){
        handler = new Handler();
        balloonList = new ArrayList<GameObject>();
    }

    // thread computes new positions of each balloon, checks whether any 2 balloons are intersecting
    // and calls the onDraw method again
    private Runnable r = new Runnable() {
        @Override
        public void run() {

            // if timer ends, show the dialog box
            if(mTimerRunning == false){
                openScoreSaveDialog(fragManager);
                return;
            }

            // if any 10 balloons are popped, add 10 seconds to the timer
            if(poppedBalloonsTimerCt == 10){
                mCountDownTimer.cancel();
                mTimeLeftInMillis += 10000;
                startTimer();
                poppedBalloonsTimerCt = 0;
            }

            if(isSensorOn){
                hotBalloons();
            }
            else{
                resetBalloons();
            }
            computeNewPos();
            checkIntersection();
            invalidate();
        }
    };


    // Checks intersections between 2 balloons
    public void checkIntersection(){

        // compare each balloon against the rest of the balloons
        for(GameObject balloon: balloonList){
            float balloonX = balloon.getPositionX();
            float balloonY = balloon.getPositionY();
            float bal_rad = balloon.getBalloonSize();
            float buffer = 5;
            // create a rectangle around the current balloon and add a buffer for extra protection
            RectF Rect1 = new RectF(balloonX-bal_rad-buffer, balloonY-bal_rad-buffer,
                    balloonX+bal_rad+buffer, balloonY+bal_rad+buffer);


            for(GameObject balloon2: balloonList){
                float posX = balloon2.getPositionX();
                float posY = balloon2.getPositionY();
                float rad = balloon2.getBalloonSize();
                // create a rectange around the other balloon
                RectF Rect2 = new RectF(posX-rad-buffer, posY-rad-buffer, posX+rad+buffer, posY+rad+buffer);


                // if these 2 rectangles intersect at any point, set both balloons to a new speed which
                // is a max of both the speeds.
                if(Rect2.intersect(Rect1) || Rect1.intersect(Rect2)){
                    int maxspeed = Math.max(balloon.getBalloonSpeed(), balloon2.getBalloonSpeed());
                    balloon2.setBalloonSpeed(maxspeed);
                }
            }
        }
    }

    // compute new y positions by just subtracting each balloon's y position by the balloon speed
    public void computeNewPos(){
        for(GameObject balloon: balloonList){
            balloon.setPositionY(balloon.positionY - balloon.balloonSpeed);
            //System.out.println(balloon.getPositionY());
        }
    }

    // if the temperature sensor detects a temp of > 33, change balloons to darker colors
    // to make it look like they're being cooked and score is also temporarily updated, you can still
    // get points even after tapping on the shaded over correct balloon and you get points
    public void hotBalloons(){
        isSensorOn = true;
        for(GameObject balloon: balloonList) {
            switch (balloon.getBalloonColor()) {
                case -65536:
                    balloon.setBalloonColor(Color.parseColor("#980000"));
                    break;
                case -16384:
                    balloon.setBalloonColor(Color.parseColor("#984500"));
                    break;
                case -1024:
                    balloon.setBalloonColor(Color.parseColor("#a77f03"));
                    break;
                case -10027264:
                    balloon.setBalloonColor(Color.parseColor("#0c8900"));
                    break;
                case -16711681:
                    balloon.setBalloonColor(Color.parseColor("#004b83"));
                    break;
                case -65281:
                    balloon.setBalloonColor(Color.parseColor("#3f42ba"));
                    break;
                case -1:
                    balloon.setBalloonColor(Color.parseColor("#f6eee3"));
                    break;
            }
        }
        switch (goalColor) {
            case -65536:
                goalColor=(Color.parseColor("#980000"));
                break;
            case -16384:
                goalColor=(Color.parseColor("#984500"));
                break;
            case -1024:
                goalColor=(Color.parseColor("#a77f03"));
                break;
            case -10027264:
                goalColor=(Color.parseColor("#0c8900"));
                break;
            case -16711681:
                goalColor=(Color.parseColor("#004b83"));
                break;
            case -65281:
                goalColor=(Color.parseColor("#3f42ba"));
                break;
            case -1:
                goalColor=(Color.parseColor("#f6eee3"));
                break;
        }

    }

    // if temperature is back to normal, reset balloon colors
    public void resetBalloons(){
        isSensorOn = false;
        for(GameObject balloon: balloonList) {
            switch (balloon.getBalloonColor()) {
                case -6815744:
                    balloon.setBalloonColor(Color.parseColor("#FF0000"));
                    break;
                case -6798080:
                    balloon.setBalloonColor(Color.parseColor("#FFC000"));
                    break;
                case -5800189:
                    balloon.setBalloonColor(Color.parseColor("#FFFC00"));
                    break;
                case -15955712:
                    balloon.setBalloonColor(Color.parseColor("#66FF00"));
                    break;
                case -16757885:
                    balloon.setBalloonColor(Color.parseColor("#00FFFF"));
                    break;
                case -12631366:
                    balloon.setBalloonColor(Color.parseColor("#FF00FF"));
                    break;
                case -594205:
                    balloon.setBalloonColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }
        switch (goalColor) {
            case -6815744:
                goalColor=(Color.parseColor("#FF0000"));
                break;
            case -6798080:
                goalColor=(Color.parseColor("#FFC000"));
                break;
            case -5800189:
                goalColor=(Color.parseColor("#FFFC00"));
                break;
            case -15955712:
                goalColor=(Color.parseColor("#66FF00"));
                break;
            case -16757885:
                goalColor=(Color.parseColor("#00FFFF"));
                break;
            case -12631366:
                goalColor=(Color.parseColor("#FF00FF"));
                break;
            case -594205:
                goalColor=(Color.parseColor("#FFFFFF"));
                break;
        }
    }

    // initialize values that we get from the game activity
    public void initVals(TextView timer, TextView score, int[] colorAndShape, FragmentManager fm, File path){
        mTextViewTimer = timer;
        mTextViewScore = score;
        //mTextViewScore.setText(this.score);
        goalColor = colorAndShape[0];
        goalShape = colorAndShape[1];
        this.path = path;
        fragManager = fm;
    }

    // starts timer and decrements the time by 1 ms and updates the time textView
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;

                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }


    // update the textView of the timer ont he game screen page
    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewTimer.setText(timeLeftFormatted);
    }

    // additional constructors
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // When a touch by the user is detected
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //System.out.println(event.getX() + " " + event.getY());

        // get the x and y pos of the user's touch
        float clickPosX = event.getX();
        float clickPosY = event.getY();
        int action = event.getAction();

        //System.out.println("Goal Settings: " + goalShape + " " + goalShape);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                for(Iterator<GameObject> it = balloonList.iterator(); it.hasNext();){
                    GameObject curBalloon = it.next();

                    // create a rectangle around a balloon
                    float balloonX = curBalloon.getPositionX();
                    float balloonY = curBalloon.getPositionY();
                    float bal_size = curBalloon.getBalloonSize();
                    RectF Rect1 = new RectF(balloonX-bal_size, balloonY-bal_size,
                            balloonX+bal_size, balloonY+bal_size);

                    // if the rectangle contains the user's click position, that balloon is being clicked
                    if(Rect1.contains(clickPosX, clickPosY)){

                        // remove touched balloon
                        it.remove();
                        poppedBalloons++;
                        poppedBalloonsTimerCt ++;

                        // if the touched balllon is the correct balloon, then increase score, else, subtract
                        // score by 1
                        if(curBalloon.getBalloonColor() == goalColor && curBalloon.getShape() == goalShape){
                            score += 1;
                            mTextViewScore.setText(String.valueOf(score));
                        }
                        else{
                            if(score > 0){
                                score -= 1;
                            }
                            mTextViewScore.setText(String.valueOf(score));
                        }
                    }

                }
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        int sWidth = getWidth();
        int sHeight = getHeight();

        Paint borderColor = new Paint();
        borderColor.setColor(Color.WHITE);
        borderColor.setStrokeWidth(20);

        // draw the bound lines around the game screen in white
        canvas.drawLine(0,0,sWidth,0, borderColor);
        canvas.drawLine(0, 0, 0, sHeight, borderColor);
        canvas.drawLine(sWidth, 0, sWidth, sHeight, borderColor);
        canvas.drawLine(0,sHeight,sWidth,sHeight, borderColor);

        // for the game screen initially empty, draw balloons and make sure they dont intersect
        if(isEmpty){
            for(int i = 0; i<8; i++){
                Random rand = new Random();
                int val =  rand.nextInt(2);
                // randomly select a circle or square balloon
                if(val%2 == 1){
                    GameObject balloon = new CircleBalloon(sWidth, sHeight);
                    // if balloon intersects any other balloon, generate a new balloon
                    while(intersect(balloon)){
                        balloon = new CircleBalloon(sWidth, sHeight);
                    }

                    //balloon.drawBalloon(canvas);
                    balloonList.add(balloon);
                }
                else{
                    GameObject balloon = new SquareBalloon(sWidth, sHeight);
                    //System.out.println(balloon.toString());
                    // if balloon intersects any other balloon, generate a new balloon
                    while(intersect(balloon)){
                        balloon = new SquareBalloon(sWidth, sHeight);
                    }
                    // add it to a list
                    balloonList.add(balloon);
                }
                //System.out.println(balloonList.get(i).getPositionY());
            }
            // start the timer as soon as our list of balloons generated
            startTimer();
            isEmpty = false;
        }


        int numBalloons = balloonList.size();

        // as balloons cross the top line, remove them from the list
        for(Iterator<GameObject> it = balloonList.iterator(); it.hasNext();){
            GameObject balloon = it.next();
            if(balloon.getPositionY() - 20  <= 0 ||
                    balloon.getPositionY() - balloon.balloonSize - 20 <= 0 ){
                // if a correct balloon doesnt get pressed and is popped at the top, then that's a
                // missed balloon
                if(balloon.getBalloonColor() == goalColor && balloon.getShape() == goalShape){
                    numBalloonsMissed++;
                }
                it.remove();
            }

        }

        // for the balloons that popped at the top, add new balloons to replenish the game screen
        // to always have 8 balloons
        int balloonsToAdd = numBalloons - balloonList.size() + poppedBalloons;

        // once again, randomly generate new balloons and make sure they don't intersect on another
        for(int i = 0; i<balloonsToAdd; i++){
            Random rand = new Random();
            int val =  rand.nextInt(2);

            if(val%2 == 1){
                GameObject balloon = new CircleBalloon(sWidth, sHeight);
                //System.out.println(balloon.toString());
                while(intersect(balloon)){
                    balloon = new CircleBalloon(sWidth, sHeight);
                }
                //balloon.drawBalloon(canvas);
                balloonList.add(balloon);
            }
            else{
                GameObject balloon = new SquareBalloon(sWidth, sHeight);
                //System.out.println(balloon.toString());
                while(intersect(balloon)){
                    balloon = new SquareBalloon(sWidth, sHeight);
                }
                balloonList.add(balloon);
            }
        }

        poppedBalloons = 0;
        // draw all the balloons in the canvas
        for(GameObject balloon: balloonList){
            balloon.drawBalloon(canvas);
        }

        // delay by frame rate to display balloons as moving
        handler.postDelayed(r, 10);

    }

    // initially checks the balloons and ensures that there's no intersections
    public boolean intersect(GameObject curBalloon){

        float balloonX = curBalloon.getPositionX();
        float balloonY = curBalloon.getPositionY();
        float bal_rad = curBalloon.getBalloonSize()/2 + 40;
        RectF Rect1 = new RectF(balloonX-bal_rad, balloonY-bal_rad, balloonX+bal_rad, balloonY+bal_rad);

        //System.out.println(curBalloon.toString());

        for(int i = 0; i<balloonList.size(); i++){
            float posX = balloonList.get(i).getPositionX();
            float posY = balloonList.get(i).getPositionY();
            float rad = balloonList.get(i).getBalloonSize()/2;
            RectF Rect2 = new RectF(posX-rad, posY-rad, posX+rad, posY+rad);

            if(Rect2.intersect(Rect1) || Rect1.intersect(Rect2)){
                return true;
            }
        }
        return false;
    }


    // after the timer ends, shows this dialog box to the user for next steps
    public void openScoreSaveDialog(FragmentManager fm){
        int finalScore = 0;

        if(score - numBalloonsMissed > 0){
            finalScore = score - numBalloonsMissed;
        }

        // read the file and get the list of player entries to decide which dialog box to show
        FileProcessing fp = new FileProcessing();
        ArrayList<Player> playersList = new ArrayList<Player>();
        playersList = fp.readFile(path,  "file.txt");

        int add = 0;


        // if player score is 0, don't add it
        if(finalScore == 0){
            add = 1;
        }
        // if players list is empty, automatically add
        else if(playersList.isEmpty()){
            add = 2;

        }
        // if player's score is full but the last score is smaller than player's score, add score
        else if(finalScore >= playersList.get(playersList.size()-1).score && playersList.size() == 20){
            add = 2;
        }
        // if player list is less than 20, add high score
        else if(playersList.size() < 20){
            add = 2;
        }
        // dont add if none of these conditions are met
        else{
            add = 3;
        }

        // create dialog box
        SaveDialog dialog = new SaveDialog(finalScore, numBalloonsMissed, add);
        dialog.show(fm, "save dialog");
    }


}