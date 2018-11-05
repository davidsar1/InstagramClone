package com.barrero.david.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText edtLoginNameOrEmail, edtLoginPassword;
    private Button btnLogin;
    private TextView txtNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginNameOrEmail = findViewById(R.id.edtLoginNameOrEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtNoAccount = findViewById(R.id.txtNoAccount);

    }
}
