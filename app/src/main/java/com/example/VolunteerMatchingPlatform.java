package com.example;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class VolunteerMatchingPlatform extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
