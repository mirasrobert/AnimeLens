package com.example.imotaku;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.Rating;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.imotaku.utility.NetworkChangeListener;
import com.google.android.material.textfield.TextInputLayout;

public class FeedbackActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    public String subject, feedback;

    EditText subjectTxt, feedbackTxt;
    Button sendbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(FeedbackActivity.this, R.color.light_blue_600));
        setContentView(R.layout.activity_feedback);

        subjectTxt = (EditText) findViewById(R.id.subject_name);
        feedbackTxt = (EditText) findViewById(R.id.feedback_text);
        sendbtn = findViewById(R.id.submit);

    }
    public void createEmail (View v)
    {
        subject = subjectTxt.getText().toString();
        feedback = feedbackTxt.getText().toString();
        if (validation(subject, feedback)) {
            Toast.makeText(this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, "Composing E-mail...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String[] array = {"animelensinfo@gmail.com"};
            i.putExtra(Intent.EXTRA_EMAIL, array);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, feedback);  // wala nito kanina

            subject = subjectTxt.getText().toString();
            feedback = subjectTxt.getText().toString(); // at eto

            //Check if user has some application that can handle email
            if (i.resolveActivity(getPackageManager()) != null )
            {
                startActivity(i);
                finish();
            }
        }
    }

    public boolean validation (String subject, String feedback) {
        if (subject.equals("") || feedback.equals("")) {
            return true;
        }
        return false;
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
