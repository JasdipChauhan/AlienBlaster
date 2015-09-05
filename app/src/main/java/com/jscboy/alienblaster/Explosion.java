package com.jscboy.alienblaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Explosion {

    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Explosion(Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        Bitmap [] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            if(i % 6 == 0 && i > 0) row++;
            image [i] = Bitmap.createBitmap
                    (spritesheet, (i - (6*row))*width, row * height, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);
    }

    public void update() {
        //explosion should only be animated if its the first time being played
        if (!animation.playedOnce()) {
            animation.update();
        }
    }

    public void draw(Canvas canvas) {
        //explosion should only be drawn if its the first time being played
        if (!animation.playedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
    }

    public int getHeight() {
        return height;
    }

}
