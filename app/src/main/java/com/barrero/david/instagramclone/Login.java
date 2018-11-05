package com.barrero.david.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    private EditText edtLoginUsername, edtLoginPassword;
    private Button btnLogin;
    private TextView txtNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtNoAccount = findViewById(R.id.txtNoAccount);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            Toast.makeText(Login.this, "Welcome " + user.get("username"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, Welcome.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

    }
}
