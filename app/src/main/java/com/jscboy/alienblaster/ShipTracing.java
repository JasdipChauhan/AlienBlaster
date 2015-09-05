package com.jscboy.alienblaster;

import android.graphics.*;

public class ShipTracing extends GameObject {

    public int r = 2;

    public ShipTracing (int x, int y) {
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

        canvas.drawRect(x, y, x + 5, y + 10, paint);
    }

}
