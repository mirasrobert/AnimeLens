package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.database.DatabaseHelper;
import com.example.imotaku.fragment.HomeFragment;
import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;
import com.example.imotaku.model.TopAnime;
import com.example.imotaku.utility.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    // Init Variables
    public final String BASE_URL = "https://api.jikan.moe";
    RecyclerView recyclerView, popularRecycler;
    RecyclerAdapter adapter, popularAdapter;
    public List<Results> listAnimes, popularAnimes, thisYearAnimes = new ArrayList<>();

    Call<Anime> call2, call3;
    Call<TopAnime> call;

    private RecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(FullscreenActivity.this, R.color.light_blue_600));

        // Change Action bar color
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_primary)));
        setContentView(R.layout.activity_fullscreen);

        // Java Code
        isLoggedIn();

        // Create table if NOT exist
        DatabaseHelper db = new DatabaseHelper(this);
        db.createTable();

        recyclerView = findViewById(R.id.recyclerView);
        popularRecycler = findViewById(R.id.popularRecycler);


        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for Interface
        AnimeHTTP animeHTTP = retrofit.create(AnimeHTTP.class);

        // Browse anime
        call = animeHTTP.getTopAnimes();

        // Popular Animes
        call2 = animeHTTP.getPopularAnimes();

        // Airing animes
        call3 = animeHTTP.getThisYearAnimes();

        getAndLoadDataFromAPI();

        initBottomNavigation();

    }

    private void initBottomNavigation() {
        //Bottom Navigation Clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // If Bottom Navigation is Clicked
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new HomeFragment(listAnimes, popularAnimes, thisYearAnimes));
                        break;

                    case R.id.genre:
                        startActivity(new Intent(FullscreenActivity.this, GenreActivity.class));
                        finish();
                        break;
                    case R.id.search:
                        startActivity(new Intent(FullscreenActivity.this, SearchActivity.class));
                        finish();
                        break;
                    case R.id.favorite:
                        startActivity(new Intent(FullscreenActivity.this, FavoriteActivity.class));
                        finish();
                        break;
                    default:
                        replaceFragment(new HomeFragment(listAnimes, popularAnimes, thisYearAnimes));
                        ;
                }

                return false;
            }
        });
    }

    // Replace the frame layout with FRAGMENT
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the Activity with FRAGMENT
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    private void getAndLoadDataFromAPI() {
        call.enqueue(new Callback<TopAnime>() {
            @Override
            public void onResponse(Call<TopAnime> call, Response<TopAnime> response) {

                // If response is success
                if (response.code() == 200) {

                    Log.i("Logs:", "onResponse: Success");

                    listAnimes = new ArrayList<>(response.body().getTop());

                    // Call enqueue for popular anime
                    call2.enqueue(new Callback<Anime>() {
                        @Override
                        public void onResponse(Call<Anime> call, Response<Anime> response) {
                            // If Response is success
                            if (response.code() == 200) {

                                Log.i("Logs:", "onResponse: Success");

                                popularAnimes = new ArrayList<>(response.body().getResults());

                                call3.enqueue(new Callback<Anime>() {
                                    @Override
                                    public void onResponse(Call<Anime> call, Response<Anime> response) {
                                        if (response.code() == 200) {
                                            Log.i("Logs:", "onResponse: Success");

                                            thisYearAnimes = new ArrayList<>(response.body().getResults());

                                            if (listAnimes.size() != 0 && popularAnimes.size() != 0 && thisYearAnimes.size() != 0) {
                                                replaceFragment(new HomeFragment(listAnimes, popularAnimes, thisYearAnimes));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Anime> call, Throwable t) {

                                    }
                                });


                            } else {
                                Log.i("Logs:", "onResponse: Failed");
                                Toast.makeText(FullscreenActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Anime> call, Throwable t) {

                        }
                    });

                } else {
                    Log.i("Logs:", "onResponse: Failed");
                    Toast.makeText(FullscreenActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopAnime> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Actionbar Buttons
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
                finish();
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
                return true;
            case R.id.logo:
                Intent intent4 = new Intent(this, EditUserActivity.class);
                startActivity(intent4);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

                startActivity(new Intent(FullscreenActivity.this, LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onKeyDown(keyCode, event);

    }

    private void isLoggedIn() {


        SharedPreferences preferences = getSharedPreferences("MYINFO", MODE_PRIVATE);

        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            //Prevent user from going back to login while authenticated
            startActivity(new Intent(FullscreenActivity.this, LoginActivity.class));
        }



    }
}