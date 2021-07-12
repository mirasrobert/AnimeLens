package com.example.imotaku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.Toast;


public class SettingsActivity extends PreferenceActivity {


    private String nickname, userpass,user_email ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settingspreference);

        // Global Shared Pref
        SharedPreferences sp = getSharedPreferences("MYINFO", MODE_PRIVATE);

        // Settings Shared Pref
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        nickname = preferences.getString("nickname", "youremail@gmail.com");
        userpass = preferences.getString("pass", "admin");

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("email_pass", userpass);
        editor.putString("name", nickname);


        // Save Global Shared Pref
        editor.commit();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            startActivity(new Intent(this, SettingsActivity.class));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SettingsActivity.this, FullscreenActivity.class));
                }
            }, 200);

        }

        return super.onKeyDown(keyCode, event);

    }
}