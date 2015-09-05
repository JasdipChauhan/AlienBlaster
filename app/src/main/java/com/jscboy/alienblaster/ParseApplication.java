package com.jscboy.alienblaster;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "uwLhBrjDHQwiAd3pLFQO6UWUJ5uvDWFDpkQ7bkza",
                "gMVC5AQJeAZ4iNGPWibM3VXJOLm8ISmojMTleO9V");

        ParseUser.enableAutomaticUser();
        ParseACL defaultAcl = new ParseACL();

        defaultAcl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultAcl, true);
    }
}
