package com.jscboy.alienblaster;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class Game extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set the screen to be full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Intent intent = new Intent(Game.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();

        } else {
            ParseUser currentUser = ParseUser.getCurrentUser();

            if(currentUser != null) {
                Intent intent = new Intent(Game.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        }
    }
}

