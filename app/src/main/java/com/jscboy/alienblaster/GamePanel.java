package com.jscboy.alienblaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;
    private MainThread thread;
    private Background bg;
    public static final int MOVESPEED = 15;
    private long tracingStartTime;
    private Player player;
    private ArrayList<ShipTracing> tracings;
    private ArrayList<Enemy> enemies;
    private ArrayList<Shot> laserShots;
    private long enemyStartTime;
    private long enemyElapsed;
    private Random generator = new Random();
    private Borders borders;
    MediaPlayer laserSound;


    private float scaleFactorX;
    private float scaleFactorY;

    public GamePanel(Context context) {

        super(context);
        this.context = context;

        //adding the panel to handle events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));

        player = new Player(BitmapFactory.decodeResource(getResources(),
                R.drawable.spaceship_sprite), 39, 44, 3);
        tracings = new ArrayList<>();
        enemies = new ArrayList<>();
        laserShots = new ArrayList<>();

        tracingStartTime = System.nanoTime();
        enemyStartTime = System.nanoTime();

        borders = new Borders();
        laserSound = MediaPlayer.create(getContext(), R.raw.lasers);

        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            if (!player.getPlaying()) {
                player.setPlaying(true);
            }
            if (player.getRight()) {
                player.setRight(false);
                addLaserShot();
                playLaserSound();
            } else {
                player.setRight(true);
                addLaserShot();
                playLaserSound();
            }
        }

        return super.onTouchEvent(event);
    }

    public void playLaserSound () {
        laserSound.start();
    }

    public void addLaserShot() {
        laserShots.add(new Shot(player.getX() + 17, player.getY() - 20));
    }

    public void update() {
        if (player.getPlaying()) {
            bg.update();
            player.update();

            //add enemies on an appropriate schedule
            if (player.getScore() % 1000 == 0) {
                newEnemy();
            }

            //check if the enemies collide with the player, and check if enemy is off the screen
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
                if (collision(enemies.get(i), player)) {
                    enemies.remove(i);
                    player.setPlaying(false);
                    break;
                }
                if (enemies.get(i).getY() > 1080) {
                    enemies.remove(i);
                    newEnemy();
                    break;
                }
                for (int j = 0; j < laserShots.size(); j++) {
                    laserShots.get(j).update();
                    if (collision(laserShots.get(j), enemies.get(i))) {
                        enemies.remove(i);
                        newEnemy();
                        laserShots.remove(j);
                        player.score += 1000;
                    }
                }
            }

            if (collision(player, borders)) {
                player.setPlaying(false);
            }

            //add laser tracings to the spaceship
            long elapsed = (System.nanoTime() - tracingStartTime)/1000000;
            if (elapsed > 120) {
                tracings.add(new ShipTracing(player.getX() + 17, player.getY() + 20));
                tracingStartTime = System.nanoTime();
            }

            for (int i = 0; i < tracings.size(); i++) {
                tracings.get(i).update();
                if (tracings.get(i).getY() > 1080) {
                    tracings.remove(i);
                }
            }

            for (int i = 0; i < laserShots.size(); i++) {
                laserShots.get(i).update();
                if (laserShots.get(i).getY() < 0) {
                    laserShots.remove(i);
                }
            }

        } else {
            Intent i = new Intent(GamePanel.this.getContext(), Welcome.class); //switched from game
            i.putExtra("playerScore", player.getScore());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(i);
        }
    }

    public boolean collision(GameObject a, GameObject b) {
        return Rect.intersects(a.getRectangle(), b.getRectangle());
    }

    public boolean collision(GameObject a, Borders b) {
        return (Rect.intersects(a.getRectangle(), b.getLeftBound())) ||
                (Rect.intersects(a.getRectangle(), b.getRightBound()));
    }

    public void draw (Canvas canvas) {

        //calculate the scale factors that will match the user's phone
        scaleFactorX = getWidth()/(WIDTH * 1.f);
        scaleFactorY = getHeight()/(HEIGHT * 1.f);

        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            borders.draw(canvas);

            for (ShipTracing lt: tracings) {
                lt.draw(canvas);
            }

            for (Enemy enemy: enemies) {
                enemy.draw(canvas);
            }

            for (Shot ls: laserShots) {
                ls.draw(canvas);
            }
            //we must return back to saved state to prevent from continously scaling the canvas
            canvas.restoreToCount(savedState);
        }

        drawText(canvas);
    }

    public void drawText(Canvas canvas) {
        Paint paint = new Paint();
        Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), "Litebulb 8-bit.ttf");
        paint.setColor(Color.YELLOW);
        paint.setTextSize(100);
        paint.setTypeface(tf);
        canvas.drawText("Score: " + player.getScore(), (WIDTH - 125) * scaleFactorX, 100, paint);
    }

    public void newEnemy() {
        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),
                R.drawable.enemy), generator.nextInt(WIDTH - 84), -100, 84, 74,
                player.getScore(), 4));
    }
}