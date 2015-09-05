package com.jscboy.alienblaster;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Collection;

public class Borders {

    Rect rightRect;
    Rect leftRect;
    String direction;
    Paint paint;

    public Borders() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    public Rect getRightBound () {
        rightRect = new Rect(GamePanel.WIDTH - 5, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        return rightRect;
    }

    public Rect getLeftBound () {
        leftRect = new Rect(0, 0, 5, GamePanel.HEIGHT);
        return leftRect;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(getRightBound(), paint);
        canvas.drawRect(getLeftBound(), paint);
    }
}
