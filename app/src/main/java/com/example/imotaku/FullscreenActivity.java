package com.example.imotaku;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.fragment.GenreFragment;
import com.example.imotaku.fragment.HomeFragment;
import com.example.imotaku.model.Anime;
import com.example.imotaku.model.JSONResponse;
import com.example.imotaku.model.Results;
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
    public final String base_url = "https://api.jikan.moe";
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    public List<Results> listAnimes;

    int img[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
    String titles[] = {"anime1", "anime2", "anime3", "anime4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // Java Code
        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView2 = findViewById(R.id.recyclerView2);

        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for Interface
        AnimeHTTP animeHTTP = retrofit.create(AnimeHTTP.class);

        Call<Anime> call = animeHTTP.getAnimes();

        call.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {

                // If response is success
                if (response.code() == 200) {

                    Log.i("Logs:", "onResponse: Success");

                    listAnimes = new ArrayList<>(response.body().getResults());

                    // Init RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(FullscreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    // Pass Data to RecyclerView
                    adapter = new RecyclerAdapter(listAnimes, FullscreenActivity.this);
                    // Set Adapter
                    recyclerView.setAdapter(adapter);


                    /*
                    // Init RecyclerView2
                    recyclerView2.setLayoutManager(new LinearLayoutManager(FullscreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView2.setItemAnimator(new DefaultItemAnimator());

                    // Pass Data to RecyclerView
                    adapter2 = new RecyclerAdapter(listAnimes, FullscreenActivity.this);
                    // Set Adapter
                    recyclerView2.setAdapter(adapter2);
                    */

                } else {
                    Log.i("Logs:", "onResponse: Failed");
                    Toast.makeText(FullscreenActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });


        //Bottom Navigation Clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // If Bottom Navigation is Clicked
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new HomeFragment(listAnimes));
                        break;

                    case R.id.genre:
                        replaceFragment(new GenreFragment());
                        break;

                    default: return;
                }

            }

        });

        //replaceFragment(new HomeFragment(listAnimes));

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the Activity with FRAGMENT
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

}