package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.adapter.FragmentAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;

import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;
import com.example.imotaku.utility.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GenreActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter fragmentAdapter;

    // Init Variables
    public final String BASE_URL = "https://api.jikan.moe";

    public List<Results> listAnimes, ovaG, movieG, tvPG, ovaPG, moviePG, ovaRatedR17List, tvRatedR17List, MovieRatedR17List, tvRatedPlusList, ovaRatedPlusList, movieRatedPlusList = new ArrayList<>();
    Call<Anime> call, tvRatedG, MovieRatedG, ovaRatedPG13, tvRatedPG13, MovieRatedPG13, ovaRatedR17, tvRatedR17, MovieRatedR17, tvRatedPlus, ovaRatedPlus, movieRatedPlus;

    private RecyclerAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(GenreActivity.this, R.color.light_blue_600));
        setContentView(R.layout.activity_genre);

        // Main Code
        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for Interface
        AnimeHTTP animeHTTP = retrofit.create(AnimeHTTP.class);

        // Browse anime for rated G
        call = animeHTTP.getOvaAndRatedGAnimes();
        tvRatedG = animeHTTP.getTvAndRatedGAnimes();
        MovieRatedG = animeHTTP.getMovieAndRatedGAnimes();

        // Browse anime for rated PG13
        ovaRatedPG13 = animeHTTP.getOvaAndRatedPG13Animes();
        tvRatedPG13 = animeHTTP.getTvAndRatedPG13Animes();
        MovieRatedPG13 = animeHTTP.getMovieAndRatedPG13Animes();

        // Browse Anime for rated R17
        ovaRatedR17 = animeHTTP.getOvaAndRatedR17Animes();
        tvRatedR17 = animeHTTP.getTvAndRatedR17Animes();
        MovieRatedR17 = animeHTTP.getMovieAndRatedR17Animes();


        // Browse Anime for rated Rplus
        ovaRatedPlus = animeHTTP.getOvaAndRatedPlusAnimes();
        tvRatedPlus = animeHTTP.getTvAndRatedPlusAnimes();
        movieRatedPlus = animeHTTP.getMovieAndRatedPlusAnimes();

        // CREATE A CALL TO THE API AND GET THE DATA
        call.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {

                // If response is success
                if (response.code() == 200) {
                    Log.i("Logs:", "onResponse: Success");
                    ovaG = new ArrayList<>(response.body().getResults());

                    tvRatedG.enqueue(new Callback<Anime>() {
                        @Override
                        public void onResponse(Call<Anime> call, Response<Anime> response) {

                            if (response.code() == 200) {

                                listAnimes = new ArrayList<>(response.body().getResults());

                                MovieRatedG.enqueue(new Callback<Anime>() {
                                    @Override
                                    public void onResponse(Call<Anime> call, Response<Anime> response) {
                                        if (response.code() == 200) {

                                            movieG = new ArrayList<>(response.body().getResults());

                                            // Nested Call
                                            getRatedPG13Anime();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Anime> call, Throwable t) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<Anime> call, Throwable t) {

                        }
                    });


                } else {
                    Log.i("Logs:", "onResponse: Failed");
                    Toast.makeText(GenreActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });

        // Bottom Nav
        bottomNavigation();

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
                        startActivity(new Intent(GenreActivity.this, FullscreenActivity.class));
                        finish();
                        break;
                    case R.id.search:
                        startActivity(new Intent(GenreActivity.this, SearchActivity.class));
                        finish();
                        break;
                    case R.id.favorite:
                        startActivity(new Intent(GenreActivity.this, FavoriteActivity.class));
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
                return true;
            case R.id.logo:
                Intent intent4 = new Intent(this, EditUserActivity.class);
                startActivity(intent4);
                return true;
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

                startActivity(new Intent(GenreActivity.this, LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void inflateTabLayout(List<Results> listAnime,
                                  List<Results> ovaG,
                                  List<Results> movieG,
                                  List<Results> tvPG,
                                  List<Results> ovaPG,
                                  List<Results> moviePG,
                                  List<Results> tvR17,
                                  List<Results> ovaR17,
                                  List<Results> movieR17,
                                  List<Results> tvRatedPlus,
                                  List<Results> ovaRatedPlus,
                                  List<Results> movieRatedPlus) {

        tabLayout = findViewById(R.id.tabLayout);
        pager2 = findViewById(R.id.view_pager2);

        // Disable Scrolling to change tabs
        pager2.setUserInputEnabled(false);

        FragmentManager fm = getSupportFragmentManager();

        // Pass data to constructor
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle(),
                listAnime, ovaG, movieG,
                tvPG, ovaPG, moviePG,
                tvR17, ovaR17, movieR17,
                tvRatedPlus, ovaRatedPlus, movieRatedPlus);
        pager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("G"));
        tabLayout.addTab(tabLayout.newTab().setText("pg13"));
        tabLayout.addTab(tabLayout.newTab().setText("r17"));
        tabLayout.addTab(tabLayout.newTab().setText("r"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    // Create Call FROM API and Get the Data from API
    private void getRatedPG13Anime() {
        // For PG13
        ovaRatedPG13.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.code() == 200) {
                    ovaPG = new ArrayList<>(response.body().getResults());

                    tvRatedPG13.enqueue(new Callback<Anime>() {
                        @Override
                        public void onResponse(Call<Anime> call, Response<Anime> response) {
                            if (response.code() == 200) {
                                tvPG = new ArrayList<>(response.body().getResults());

                                MovieRatedPG13.enqueue(new Callback<Anime>() {
                                    @Override
                                    public void onResponse(Call<Anime> call, Response<Anime> response) {
                                        if (response.code() == 200) {
                                            moviePG = new ArrayList<>(response.body().getResults());

                                            // Pass Data to Adapter and ReyclerView

                                            // For R17
                                            getRatedR17Animes();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Anime> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Anime> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }

    private void getRatedR17Animes() {
        ovaRatedR17.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.code() == 200) {
                    ovaRatedR17List = new ArrayList<>(response.body().getResults());

                    tvRatedR17.enqueue(new Callback<Anime>() {
                        @Override
                        public void onResponse(Call<Anime> call, Response<Anime> response) {
                            if (response.code() == 200) {
                                tvRatedR17List = new ArrayList<>(response.body().getResults());

                                MovieRatedR17.enqueue(new Callback<Anime>() {
                                    @Override
                                    public void onResponse(Call<Anime> call, Response<Anime> response) {
                                        if (response.code() == 200) {
                                            MovieRatedR17List = new ArrayList<>(response.body().getResults());

                                            getRatedPlusAnimes();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Anime> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Anime> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }

    private void getRatedPlusAnimes() {

        ovaRatedPlus.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.code() == 200) {
                    ovaRatedPlusList = new ArrayList<>(response.body().getResults());

                    tvRatedPlus.enqueue(new Callback<Anime>() {
                        @Override
                        public void onResponse(Call<Anime> call, Response<Anime> response) {
                            if (response.code() == 200) {
                                tvRatedPlusList = new ArrayList<>(response.body().getResults());

                                movieRatedPlus.enqueue(new Callback<Anime>() {
                                    @Override
                                    public void onResponse(Call<Anime> call, Response<Anime> response) {
                                        if (response.code() == 200) {
                                            movieRatedPlusList = new ArrayList<>(response.body().getResults());

                                            // Show UI
                                            inflateTabLayout(listAnimes, ovaG, movieG,
                                                    tvPG, ovaPG, moviePG,
                                                    tvRatedR17List, ovaRatedR17List, MovieRatedR17List,
                                                    tvRatedPlusList, ovaRatedPlusList, movieRatedPlusList); // Show TabLayout and Data
                                            // If data has been loaded // Stop the loading
                                            LottieAnimationView lottieLoading = findViewById(R.id.loadingLottieGenre);
                                            lottieLoading.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Anime> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Anime> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });

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

}
