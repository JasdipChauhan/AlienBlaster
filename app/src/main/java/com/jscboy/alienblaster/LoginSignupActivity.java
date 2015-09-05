package com.jscboy.alienblaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.*;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginSignupActivity extends ActionBarActivity {

    Button loginButton;
    Button logoutButton;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        setTitle("Sign In");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login);
        logoutButton = (Button) findViewById(R.id.logout);
    }

    public void loginMethod(View v) {
        usernametxt = username.getText().toString();
        passwordtxt = password.getText().toString();

        ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(LoginSignupActivity.this, Game.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry but you have entered the wrong username or " +
                                    "password, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signupMethod (View v) {
        Intent i = new Intent(LoginSignupActivity.this, SignupActivity.class);
        finish();
        startActivity(i);
    }

}
