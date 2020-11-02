package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rQbKR75FSwPTMv2xnIGrq0mHCVvI25e4wE6Z0ssL")
                .clientKey("nHv4SvMSw0MGmbZ0hnGj0NIKk9terhDRzS7J42TF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
