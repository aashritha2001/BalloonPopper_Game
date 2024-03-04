package com.example.axa180116_asg5;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This class inherits the GameObject abstract class which contains all the necessary
   variables needed for a game object. THis class contains an additional method called drawBalloon
   that specifically draws a circle.
 */
class CircleBalloon extends GameObject {

   public CircleBalloon(int width, int height) {
      super(width, height);
   }


   // draw a circle, set its color, and make its radius a randomly selected balloon size / 2
   @Override
   public void drawBalloon(Canvas canvas) {
      Paint color = new Paint();
      color.setColor(getBalloonColor());
      color.setStrokeWidth(2);
      canvas.drawCircle(getPositionX(), getPositionY(),
              getBalloonSize()/2, color );


   }

   // return shape, 0 ==> circle, 1 is a square
   @Override
   public int getShape() {
      return 0;
   }


}