package com.jscboy.alienblaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends GameObject {

    private int score;
    private int speed;
    private Random generator = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Enemy(Bitmap res, int x, int y, int w, int h, int s, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;

        speed = generator.nextInt(15);

        Bitmap [] image = new Bitmap[numFrames];
        spritesheet = res;

        //'cuts' out each sprite from the sheet by changing the width at which to 'cut' the sprite,
        // and assigns each one to the image array
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed);
    }

    public void update() {
        y += speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {}
    }

    public int getWidth() {
        //offset slightly for more realistic collision detection
        return width - 10;
    }
}
