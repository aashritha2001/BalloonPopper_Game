package com.example.axa180116_asg5;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This class inherits the GameObject abstract class which contains all the necessary
   variables needed for a game object. This class contains an additional method called drawBalloon
   that specifically draws a square.
 */
class SquareBalloon extends GameObject{

   public SquareBalloon(int width, int height) {
      super(width, height);
   }

   // draw a square, set its color, and make its dimensions to a randomly selected balloon size
   @Override
   public void drawBalloon(Canvas canvas) {
      RectF rect = new RectF(((float) getPositionX()), (float)getPositionY()-getBalloonSize(),
              (float)getPositionX()+getBalloonSize(), (float)getPositionY());
      Paint color = new Paint();
      color.setColor(getBalloonColor());
      color.setStrokeWidth(2);
      canvas.drawRect(rect, color);
   }

   // return shape, 1 ==> square, 0 is a circle
   public int getShape() {
      return 1;
   }


}