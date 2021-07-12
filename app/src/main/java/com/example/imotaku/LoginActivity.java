package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.imotaku.utility.NetworkChangeListener;

public class LoginActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    LottieAnimationView lottieAnimationView;

    EditText emails, password;
    TextView textView;
    SharedPreferences preferences;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.light_blue_600));
        setContentView(R.layout.activity_login);

        emails = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);

        preferences = getSharedPreferences("MYINFO", MODE_PRIVATE);

        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        lottieAnimationView = findViewById(R.id.loadingLogin);

        // Prevent from going back to login page
        if (isLoggedIn) {
            //Prevent user from going back to login while authenticated
            startActivity(new Intent(LoginActivity.this, FullscreenActivity.class));
            finish();

        }

        // When Login Button clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = preferences.getString("email_name", "");
                String pass = preferences.getString("email_pass", "");

                String usernameValue = emails.getText().toString();
                String passwordValue = password.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();


                if (usernameValue.toLowerCase().equals(email) && passwordValue.toLowerCase().equals(pass) &&
                    !email.equals("") && !pass.equals("")
                ) {

                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    View b = findViewById(R.id.login_button);
                    View c = findViewById(R.id.create);
                    View l = findViewById(R.id.loadingLogin);
                    b.setVisibility(View.INVISIBLE);
                    c.setVisibility(View.INVISIBLE);
                    l.setVisibility(View.VISIBLE);
                    lottieAnimationView.animate().setDuration(1000).setStartDelay(2000);
                    new Handler(). postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, FullscreenActivity.class));
                            finish();
                        }
                    }, 4000);
                } else
                    Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

            }
        });

        textView = (TextView) findViewById(R.id.create);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // NetWork
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        // Register our broadcast receiver
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}