package com.jscboy.alienblaster;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {
    private Bitmap spritesheet;
    public int score;
    private boolean right;
    private boolean playing = true;
    private Animation animation = new Animation();
    private long startTime;

    public Player (Bitmap res, int w, int h, int numFrames) {
        x = GamePanel.WIDTH/2;
        y = 650;
        dx = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap [] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void setRight(boolean b) {
        right = b;
    }

    public boolean getRight() {
        return right;
    }

    public void update () {
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if (elapsed > 100) {
            score += scoreIncrement;
            startTime = System.nanoTime();
        }
        animation.update();

        if (right) {
            dx -= speedIncrement;
        } else {
            dx += speedIncrement;
        }

        x += (double) dx;
    }

    public void draw (Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getScore() {
        return score;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetScore() {
        score = 0;
    }
}
