package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imotaku.adapter.FavoriteRecyclerAdapter;
import com.example.imotaku.adapter.SearchRecyclerAdapter;
import com.example.imotaku.database.DatabaseHelper;
import com.example.imotaku.model.FavoriteAnime;
import com.example.imotaku.model.Results;
import com.example.imotaku.utility.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    // database
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    List<FavoriteAnime> favoriteAnimeList = new ArrayList<>();

    FavoriteRecyclerAdapter.RecyclerViewClickListener listener;

    RecyclerView recyclerView;

    TextView emptyMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(FavoriteActivity.this, R.color.light_blue_600));

        setContentView(R.layout.activity_favorite);


        initWidgets();

        // Get All The List
        favoriteAnimeList = databaseHelper.getAll();

        // Show Results if have
        if (favoriteAnimeList.size() != 0) {
            emptyMsg.setVisibility(View.GONE);
            showResults(favoriteAnimeList);

        } else {
            // Otherwise show message that list is empty
            recyclerView.setVisibility(View.GONE);
            emptyMsg.setVisibility(View.VISIBLE);
        }

        bottomNavigation();


    }

    private void initWidgets() {
        recyclerView = findViewById(R.id.recyclerView);
        emptyMsg = findViewById(R.id.empty_msg);
    }

    private void showResults(List<FavoriteAnime> results) {

        setOnClickListener(results);

        // 2. Set Layout Manager

        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(favoriteAnimeList, FavoriteActivity.this, listener);

        // Set Adapter
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(List<FavoriteAnime> list) {
        // Use the click listener interface to go to each anime description by position
        listener = new FavoriteRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(FavoriteActivity.this, AnimeDescriptionActivity.class);

                //String mal_id = Integer.toString(list.get(position).getMal_id());

                intent.putExtra("mal_id", list.get(position).getId());
                // Start the activity with data passing to the next activity
                startActivity(intent);
                //Toast.makeText(FavoriteActivity.this, list.get(position).getName().toLowerCase(), Toast.LENGTH_SHORT).show();
            }
        };

    }

    private void bottomNavigation() {

        //Bottom Navigation Clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // If Bottom Navigation is Clicked
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(FavoriteActivity.this, FullscreenActivity.class));
                        finish();
                        break;

                    case R.id.genre:
                        startActivity(new Intent(FavoriteActivity.this, GenreActivity.class));
                        finish();
                        break;

                    case R.id.search:
                        startActivity(new Intent(FavoriteActivity.this, SearchActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.feedback:
                Intent intent2 = new Intent(this, FeedbackActivity.class);
                startActivity(intent2);
                return true;
            case R.id.developers:
                Intent intent3 = new Intent(this, DevelopersActivity.class);
                startActivity(intent3);
                return true;
            case R.id.logout:
                exit();
                return true;
            case R.id.info:
                Toast.makeText(this, "Developed by Robert Miras", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Logout?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences preferences = getSharedPreferences("MYINFO", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.commit();

                startActivity(new Intent(FavoriteActivity.this, LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

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