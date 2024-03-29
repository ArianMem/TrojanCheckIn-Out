package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Database.BuildingManipulator;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;
import static com.team13.trojancheckin_out.Database.AccountManipulator.resetFromStart;


public class Startup extends AppCompatActivity {

    private Button Register;
    private Button Login;
    public static BuildingManipulator buildingManipulator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        buildingManipulator = new BuildingManipulator();

        currentUser = null;
        resetFromStart = true;


        Register = (Button)findViewById(R.id.register);
        Login = (Button)findViewById(R.id.login);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Register.class);
                resetFromStart = false;
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startup.this, Login.class);
                resetFromStart = false;
                startActivity(intent);
            }
        });
    }

}