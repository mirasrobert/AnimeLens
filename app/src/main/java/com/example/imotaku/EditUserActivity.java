package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.model.content.Mask;
import com.example.imotaku.utility.NetworkChangeListener;
import com.google.android.material.textfield.TextInputEditText;

public class EditUserActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private TextInputEditText user_name, user_pass, user_email;
    private String userName, userEmail ,userPass ;

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        initWidgets();

        sp = getSharedPreferences("MYINFO", MODE_PRIVATE);

        userEmail = sp.getString("email_name", "");
        userPass = sp.getString("email_pass", "");
        userName = sp.getString("name", "");

        // Show the current info
        user_name.setText(userName.toString());

        user_email.setText(userEmail.toString());

        user_pass.setText(userPass.toString());

    }

    private void initWidgets() {
        user_name = findViewById(R.id.user_name);

        user_email = findViewById(R.id.user_email);

        user_pass = findViewById(R.id.user_pass);
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
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }
}