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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUsername, edtLoginPassword;
    private Button btnLogin;
    private TextView txtNoAccount;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        //Allows the Enter key to perform the same action as login button
        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLogin);
                }
                return false;
            }
        });

        btnLogin = findViewById(R.id.btnLogin);

        txtNoAccount = findViewById(R.id.txtNoAccount);

        btnLogin.setOnClickListener(this);
        txtNoAccount.setOnClickListener(this);

        progressBarLogin = findViewById(R.id.progressBarLogin);

        //Checks to see If a User is logged in on their phone and brings them to this screen if so
        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMedia();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLogin:

                ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {

                            progressBarLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Welcome " + user.get("username"), Toast.LENGTH_SHORT).show();

                            transitionToSocialMedia();

                        } else {
                            Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        }

                        progressBarLogin.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.txtNoAccount:

                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    //Closes keyboard when main screen is tapped
    public void rootLayoutTapped(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void transitionToSocialMedia () {
        Intent intent = new Intent(Login.this, SocialMedia.class);
        startActivity(intent);
        finish();
    }

}
