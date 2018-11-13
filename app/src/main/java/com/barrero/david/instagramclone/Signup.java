package com.barrero.david.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignupEmail, edtSignupUsername, edtSignupPassword;
    private Button btnSignup;
    private TextView txtHaveAccount;
    private ProgressBar progressBarSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupUsername = findViewById(R.id.edtSignupUsername);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);

        //Allows the Enter key to perform the same function as the signup button
        edtSignupPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignup);
                }
                return false;
            }
        });

        btnSignup = findViewById(R.id.btnSignup);

        txtHaveAccount = findViewById(R.id.txtHaveAccount);

        progressBarSignup = findViewById(R.id.progressBarSignup);

        btnSignup.setOnClickListener(this);
        txtHaveAccount.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMedia();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignup:

                //Informs User that all fields are required
                if (edtSignupEmail.getText().toString().equals("") || edtSignupUsername.getText().toString().equals("") ||
                        edtSignupPassword.getText().toString().equals("")) {
                    Toast.makeText(Signup.this, "All Fields are Required", Toast.LENGTH_LONG).show();

                } else {

                    //Writes the User signup info to database
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignupEmail.getText().toString());
                    appUser.setUsername(edtSignupUsername.getText().toString());
                    appUser.setPassword(edtSignupPassword.getText().toString());

                    progressBarSignup.setVisibility(ProgressBar.VISIBLE);

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(Signup.this, "welcome " + appUser.get("username"), Toast.LENGTH_SHORT).show();

                                transitionToSocialMedia();

                            } else if (e != null) {
                                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progressBarSignup.setVisibility(ProgressBar.INVISIBLE);

                        }
                    });
                }
                break;

            case  R.id.txtHaveAccount:

                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    //Closes the keyboard when the main screen is tapped
    public void rootLayoutTapped(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void transitionToSocialMedia () {
        Intent intent = new Intent(Signup.this, SocialMedia.class);
        startActivity(intent);
        finish();
    }

}
