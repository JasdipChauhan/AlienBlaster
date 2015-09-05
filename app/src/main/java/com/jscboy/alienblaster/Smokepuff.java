package com.jscboy.alienblaster;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Smokepuff extends GameObject {

    public int r = 2;

    public Smokepuff(int x, int y) {
        super.x = x;
        super.y = y;
    }

    public void update() {
        y += 10;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        /*canvas.drawCircle(x - r + 2, y - r + 5, r, paint);
        canvas.drawCircle(x - r + 4, y - r + 7, r, paint);
        canvas.drawCircle(x - r + 6, y - r + 10, r, paint); */

        //canvas.drawRect(x, y, 5, 10, paint);
        canvas.drawRect(x, y, x + 5, y + 10, paint);
    }

}
