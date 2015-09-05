package com.jscboy.alienblaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends ActionBarActivity {

    String usernametxt;
    String passwordtxt;
    String emailtxt;
    EditText password;
    EditText username;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
    }

    public void signupMethod (View v) {
        usernametxt = username.getText().toString();
        passwordtxt = password.getText().toString();
        emailtxt = email.getText().toString();

        if (usernametxt.equals("") || passwordtxt.equals("")) {
            Toast.makeText(getApplicationContext(), "You did not enter a username, or a" +
                    " password. Please make sure you have filled them out please.",
                    Toast.LENGTH_SHORT).show();
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(usernametxt);
            user.setPassword(passwordtxt);
            if (!emailtxt.equals("")) {
                user.setEmail(emailtxt);
            }
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(),
                                "Successfully Signed Up! Try logging in now",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignupActivity.this, LoginSignupActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error while signing up!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void returnMethod(View view) {
        finish();
        Intent i = new Intent(this, LoginSignupActivity.class);
        startActivity(i);
    }
}
