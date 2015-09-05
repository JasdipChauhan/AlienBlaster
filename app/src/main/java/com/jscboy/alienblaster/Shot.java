package com.jscboy.alienblaster;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shot extends GameObject {

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update () {
        y -= 10;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(x, y, x + 5, y + 10, paint);
    }

}
