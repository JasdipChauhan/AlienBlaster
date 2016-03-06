package com.jscboy.alienblaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.*;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.facebook.ads.*;


public class Welcome extends Activity {

    private Button logout;
    private Button exit;
    private Button playGame;
    private String playerScoreStr = "0";
    private int playerScoreNum = 0;
    private TextView playersScoreTV;
    private TextView playersHighScoreTV;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ParseUser currentUser = ParseUser.getCurrentUser();
        String strUser = currentUser.getUsername();

        TextView beforeTxtUser = (TextView) findViewById(R.id.beforeTxtUser);
        TextView afterTxtUser = (TextView) findViewById(R.id.afterTxtUser);
        beforeTxtUser.setText("↪You are logged in as ");
        afterTxtUser.setText(strUser);

        logout = (Button) findViewById(R.id.logout);
        exit = (Button) findViewById(R.id.exitButton);
        playGame = (Button) findViewById(R.id.playButton);
        playersScoreTV = (TextView) findViewById(R.id.playersScoreTV);
        playersHighScoreTV = (TextView) findViewById(R.id.playersHighScoreTV);

        //this is for the banner ad on the welcome screen at the bottom
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("38408A0DBD8A775B").build();
        mAdView.loadAd(adRequest);

        //this is for the interstitial ad

        /*interAD = new InterstitialAd(this);
        interAD.setAdUnitId("ca-app-pub-5012706603951387/2638362958");
        AdRequest adRequestINT = new AdRequest.Builder().addTestDevice("38408A0DBD8A775B").build();

        interAD.loadAd(adRequestINT);
        interAD.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                displayInterstitial();
            }
        }); */
        /////////////////////////

        Bundle playersData = getIntent().getExtras();
        if (playersData != null) {
            playerScoreNum = playersData.getInt("playerScore");
            playerScoreStr = Integer.toString(playerScoreNum);
            playersScoreTV.setTextColor(Color.RED);
            playersScoreTV.setText("Most Recent Score: " + playerScoreNum);

            if (playerScoreNum > loadScore(getApplicationContext())) {
                saveScore(getApplicationContext(), playerScoreNum);
            }

            playersHighScoreTV.setTextColor(Color.CYAN);
            playersHighScoreTV.setText("High Score: " + loadScore(getApplicationContext()));
        }

    }

    public void saveScore(Context context, int score){
        SharedPreferences sharedPreferences = context.getSharedPreferences
                ("com.jscboy.alienblaster", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SCORE", score);
        editor.commit();
    }

    public int loadScore(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences
                ("com.jscboy.alienblaster", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("SCORE", 0);
    }


    public void logoutMethod(View view) {
        finish();
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                Intent i = new Intent(Welcome.this, LoginSignupActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

    public void exitMethod(View view) {
        System.exit(0);
    }

    public void playgameMethod(View view) {
            setTitle("They're coming for you!");
            setContentView(new GamePanel(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
