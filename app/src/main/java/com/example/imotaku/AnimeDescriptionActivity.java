package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnimeDescriptionActivity extends AppCompatActivity {

    TextView animeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_description);

         animeName = findViewById(R.id.nameTextView);

         String title = "No title";

         // Get the extras that we passed on the intent
         Bundle extras = getIntent().getExtras();

         if(extras != null) {
            title = extras.getString("title");
         }

         animeName.setText(title);
    }
}