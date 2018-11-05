package com.barrero.david.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseUser;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                finish();
            }
        });

    }
}
