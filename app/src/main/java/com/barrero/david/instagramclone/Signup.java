package com.barrero.david.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseInstallation;

public class Signup extends AppCompatActivity {

    private EditText edtSignupEmail, edtSignupUsername, edtSignupPassword, edtConfirmPassword;
    private Button btnSignup;
    private TextView txtHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupUsername = findViewById(R.id.edtSignupUsername);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnSignup = findViewById(R.id.btnSignup);

        txtHaveAccount = findViewById(R.id.txtHaveAccount);

    }
}
