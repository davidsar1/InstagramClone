package com.barrero.david.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseInstallation;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
