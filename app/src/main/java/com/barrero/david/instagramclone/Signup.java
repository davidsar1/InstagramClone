package com.barrero.david.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity {

    private EditText edtSignupEmail, edtSignupUsername, edtSignupPassword, edtConfirmPassword;
    private Button btnSignup;
    private TextView txtHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.signup_title);
        setContentView(R.layout.activity_signup);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupUsername = findViewById(R.id.edtSignupUsername);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnSignup = findViewById(R.id.btnSignup);

        txtHaveAccount = findViewById(R.id.txtHaveAccount);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setEmail(edtSignupEmail.getText().toString());
                appUser.setUsername(edtSignupUsername.getText().toString());
                appUser.setPassword(edtSignupPassword.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null && edtSignupPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                            Toast.makeText(Signup.this, "welcome " + appUser.get("username"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Signup.this, Welcome.class);
                            startActivity(intent);

                        } else if (e != null && edtSignupPassword == edtConfirmPassword) {
                            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else if (e == null && edtSignupPassword != edtConfirmPassword) {
                            Toast.makeText(Signup.this, "The Passwords did not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

    }
}
