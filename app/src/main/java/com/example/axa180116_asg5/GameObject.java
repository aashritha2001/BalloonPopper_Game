package com.example.axa180116_asg5;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Arrays;
import java.util.Random;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This abstract class contains the game object properties whether it's a circle or square.
  It contains generic properties such as color, speed, position, size, etc. The Circle and Square classes
  inherit this abstract class and must describe a drawBalloon method that draws their respective shape
  balloons.

 */
public abstract class GameObject {

    protected float positionX;
    protected float positionY;

    protected int balloonColor;
    protected int balloonSpeed;

    public int getBalloonSize() {
        return balloonSize;
    }

    public void setBalloonSize(int balloonSize) {
        this.balloonSize = balloonSize;
    }

    protected int balloonSize;
    protected int screenWidth;
    protected int screenHeight;
    Random rand = new Random();

    // Randomly select colors from this string
    protected String[] colors = {"#FF0000", "#FFC000",
    "#FFFC00", "#66FF00", "#00FFFF", "#FF00FF", "#FFFFFF"};



    public GameObject(int sWidth, int sHeight){
        this.screenHeight = sHeight;
        this.screenWidth = sWidth;
        randomlyAssignVals();

    }

    @Override
    public String toString() {
        return "GameObject{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", balloonColor=" + balloonColor +
                ", balloonSpeed=" + balloonSpeed +
                ", balloonSize=" + balloonSize +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", rand=" + rand +
                ", colors=" + Arrays.toString(colors) +
                '}';
    }


    public int getBalloonSpeed() {
        return balloonSpeed;
    }

    public void setBalloonSpeed(int balloonSpeed) {
        this.balloonSpeed = balloonSpeed;
    }

    // randomly assigns color, shape, speed, size, and positions of the balloon initially
    public void randomlyAssignVals(){

        int index = rand.nextInt(7 ) ;
        int randColor = Color.parseColor(colors[index]);

        balloonColor = randColor;

        balloonSpeed = rand.nextInt(6) + 2;
        balloonSize = rand.nextInt(70-45) + 45;
        positionX = rand.nextInt(screenWidth - 2*balloonSize - 50)+ 50;
        positionY = screenHeight - 50;
    }

    // getter and setter methods

    public abstract void drawBalloon(Canvas canvas);
    public abstract int getShape();

    public float getPositionX() {
        return positionX;
    }

    public int getBalloonColor() {
        return balloonColor;
    }

    public void setBalloonColor(int balloonColor) {
        this.balloonColor = balloonColor;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }



}