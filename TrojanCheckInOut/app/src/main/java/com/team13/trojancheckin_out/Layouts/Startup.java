package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Database.AccountManipulator;

public class Startup extends AppCompatActivity {

    private Button Register;
    private Button Login;
    public AccountManipulator accountManipulator = new AccountManipulator();
    public Register register = new Register(accountManipulator);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        Register = (Button)findViewById(R.id.register);
        Login = (Button)findViewById(R.id.login);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Register.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Login.class);
                startActivity(intent);
            }
        });
    }

}