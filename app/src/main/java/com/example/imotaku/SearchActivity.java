package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.adapter.SearchRecyclerAdapter;
import com.example.imotaku.fragment.HomeFragment;
import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;
import com.example.imotaku.model.TopAnime;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    // Init Variables
    public final String BASE_URL = "https://api.jikan.moe";

    Call<Anime> call;
    Call<TopAnime> initCall;

    AnimeHTTP animeHTTP;
    List<Results> results,topAnime = new ArrayList<>();

    SearchRecyclerAdapter.RecyclerViewClickListener listener;

    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchActivity.this,  R.color.light_blue_600));
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);

        // Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for Interface
        animeHTTP = retrofit.create(AnimeHTTP.class);

        initialView();

        bottomNavigation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        // Init menu item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.equals("")) {
                    callApi(query.toLowerCase());
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void callApi(String anime) {

        // Browse anime
        call = animeHTTP.searchAnime(anime);

        call.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {

                if(response.code() != 200) {
                    Toast.makeText(SearchActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
                } else {
                    // If Response is Success

                    results = response.body().getResults();

                    if (results.size() != 0) {
                        showResults(results);
                    }

                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {

            }
        });
    }

    private void initialView() {
        initCall = animeHTTP.getTopAnimes();

        initCall.enqueue(new Callback<TopAnime>() {
            @Override
            public void onResponse(Call<TopAnime> call, Response<TopAnime> response) {

                topAnime = response.body().getTop();

                if (topAnime.size() != 0) {
                    showResults(topAnime);
                }

            }

            @Override
            public void onFailure(Call<TopAnime> call, Throwable t) {

            }
        });
    }

    private void showResults(List<Results> results) {
        setOnClickListener(results);

        // 2. Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        adapter = new SearchRecyclerAdapter(results, SearchActivity.this, listener);

        // Set Adapter
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(List<Results> list) {
        // Use the click listener interface to go to each anime description by position
        listener = new SearchRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(SearchActivity.this, AnimeDescriptionActivity.class);

                //String mal_id = Integer.toString(list.get(position).getMal_id());

                intent.putExtra("mal_id", list.get(position).getMal_id());
                // Start the activity with data passing to the next activity
                startActivity(intent);
                //Toast.makeText(getActivity(), list.get(position).getTitle().toLowerCase() + mal_id, Toast.LENGTH_SHORT).show();
                finish();
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
                        startActivity(new Intent(SearchActivity.this, FullscreenActivity.class));
                        finish();
                        break;

                    case R.id.genre:
                        startActivity(new Intent(SearchActivity.this, GenreActivity.class));
                        finish();
                        break;


                    case R.id.favorite:
                        startActivity(new Intent(SearchActivity.this, FavoriteActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
