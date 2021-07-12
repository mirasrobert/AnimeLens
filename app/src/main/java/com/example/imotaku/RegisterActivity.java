package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imotaku.utility.NetworkChangeListener;

public class RegisterActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    EditText userName, email, pass;
    SharedPreferences preferences;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterActivity.this, R.color.light_blue_600));
        setContentView(R.layout.activity_registration);

        //Optional
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.username);
        email =  findViewById(R.id.email_name);
        pass = findViewById(R.id.email_pass);
        btnRegister = findViewById(R.id.create_button);

        preferences = getSharedPreferences("MYINFO", MODE_PRIVATE);

        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn",false);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUser = userName.getText().toString();
                String newPass = pass.getText().toString();
                String newEmail = email.getText().toString();

                if (!newUser.equals("") && !newPass.equals("") && !newEmail.equals("")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name",newUser.toLowerCase());
                    editor.putString("email_pass",newPass.toLowerCase());
                    editor.putString("email_name",newEmail.toLowerCase());
                    editor.putBoolean("isLoggedIn",false);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(login);
                    finish();


                }
                else {
                    Toast.makeText(RegisterActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        // Register our broadcast receiver
        registerReceiver(networkChangeListener, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}