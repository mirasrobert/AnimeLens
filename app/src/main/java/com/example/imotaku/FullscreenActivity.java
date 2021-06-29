package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.fragment.GenreFragment;
import com.example.imotaku.fragment.HomeFragment;
import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;
import com.example.imotaku.model.TopAnime;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenActivity extends AppCompatActivity {

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
        getWindow().setStatusBarColor(ContextCompat.getColor(FullscreenActivity.this, R.color.bg_primary));

        // Change Action bar color
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_primary)));
        setContentView(R.layout.activity_fullscreen);

        // Java Code
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

        //Bottom Navigation Clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // If Bottom Navigation is Clicked
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                         replaceFragment(new HomeFragment(listAnimes, popularAnimes, thisYearAnimes));
                        break;

                    case R.id.genre:
                        startActivity(new Intent(FullscreenActivity.this, GenreActivity.class));
                        break;

                    default: replaceFragment(new HomeFragment(listAnimes, popularAnimes, thisYearAnimes));;
                }

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

                                            if(listAnimes.size() != 0 && popularAnimes.size() != 0 && thisYearAnimes.size() != 0) {
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

}