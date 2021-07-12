package com.example.imotaku;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.database.DatabaseHelper;
import com.example.imotaku.model.FavoriteAnime;
import com.example.imotaku.model.Genre;
import com.example.imotaku.model.SingleAnime;
import com.example.imotaku.utility.NetworkChangeListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeDescriptionActivity extends AppCompatActivity {

    // For Broadcast Receiver
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    //LifeCycle
    private static final String TAG = "animeDescriptionActivity";

    // Default Values
    public final String BASE_URL = "https://api.jikan.moe";
    public String url = "";
    public String videoId = "9jo51nJrO0k";
    public String background = "No Description";
    public String ranks = "N/A";

    // Views
    TextView animeName, score, rank, favorite, type, status, episodes, genre, sypnosis, rating, popularity, duration, source;
    ImageView animeImg;

    private Call<SingleAnime> singleAnimeCall;
    private List<Genre> genreList = new ArrayList<>();

    // Global
    public int mal_id;
    public String title, types, scores, episode, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOG
        Log.i(TAG,"onCreate");

        Toast.makeText(AnimeDescriptionActivity.this, "onCreate", Toast.LENGTH_SHORT).show();

        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(AnimeDescriptionActivity.this, R.color.light_blue_600));

        setContentView(R.layout.activity_anime_description);

        // Find view ID
        animeName = findViewById(R.id.animeTitle);
        animeImg = findViewById(R.id.animeImg);
        score = findViewById(R.id.score);
        rank = findViewById(R.id.rank);
        favorite = findViewById(R.id.favorite);
        type = findViewById(R.id.type);
        status = findViewById(R.id.status);
        episodes = findViewById(R.id.episodes);
        genre = findViewById(R.id.genre);
        sypnosis = findViewById(R.id.sypnosisTxt);
        rating = findViewById(R.id.rating);
        popularity = findViewById(R.id.popularity);
        duration = findViewById(R.id.duration);
        source = findViewById(R.id.source);

        // Get the ID
        mal_id = 20;
        // Get the extras that we passed on the intent
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mal_id = extras.getInt("mal_id");
        }


        // Retrofit Builder for calling API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Instance for Interface
        AnimeHTTP animeHTTP = retrofit.create(AnimeHTTP.class);

         singleAnimeCall = animeHTTP.getSingleAnime(mal_id);

         singleAnimeCall.enqueue(new Callback<SingleAnime>() {
             @Override
             public void onResponse(Call<SingleAnime> call, Response<SingleAnime> response) {

                 if(response.code() == 200) {

                     String trailer_url = (response.body().getTrailer_url() != null) ? response.body().getTrailer_url() : videoId ;

                    if (response.body().getTrailer_url() != null) {
                        String[] urlParts = trailer_url.split("/"); // Split the url by slash

                        String lastPart = urlParts[urlParts.length - 1]; // get the last index

                        String[] id = lastPart.split("\\?"); // Split the url by question mark

                        videoId =  id[0];
                    }

                     url = response.body().getUrl();
                     title = response.body().getTitle();
                     img = response.body().getImage_url();
                     scores = response.body().getScore();
                     ranks = (response.body().getRank() != null) ? response.body().getRank() : ranks;
                     String favorites = response.body().getFavorites();
                     types = response.body().getType();
                     episode = response.body().getEpisodes();

                     String rating = response.body().getRating();
                     String source = response.body().getSource();
                     String popularity = Integer.toString(response.body().getPopularity());
                     String duration = response.body().getDuration();

                     genreList = response.body().getGenres();

                     background = (response.body().getSynopsis() != null) ? response.body().getSynopsis() : background;

                     // For Youtube Trailer
                     startYouTubeVideo(videoId);

                     // For Viewss
                     updateUI(title, img, scores, ranks, favorites, types, episode, genreList, background, rating, popularity, source, duration);

                 } else {
                     Toast.makeText(AnimeDescriptionActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                 }


             }

             @Override
             public void onFailure(Call<SingleAnime> call, Throwable t) {

             }
         });

    }

    private void updateUI(String title,
                          String img,
                          String scores,
                          String ranks,
                          String favorites,
                          String types,
                          String episode,
                          List<Genre> genres,
                          String background,
                          String rate,
                          String popular,
                          String sources,
                          String durations) {


        String animeGenres = "";
        String animeEpisodes = "Ep: "+ episode;
        String animeRank = "#"+ranks;

        for(Genre genre : genres) {
               animeGenres += genre.getName() + " ";
        }

        // Update the UI with the correct values

        // Change Image
        Glide.with(AnimeDescriptionActivity.this)
                .load(img)
                .into(animeImg);

        animeName.setText(title);
        score.setText(scores);
        rank.setText(animeRank);
        favorite.setText(favorites);
        type.setText(types);
        episodes.setText(animeEpisodes);
        genre.setText(animeGenres);
        sypnosis.setText(background);
        rating.setText(rate);
        popularity.setText(popular);
        duration.setText(durations);
        source.setText(sources);

        addToFavorites();

    }


    private void startYouTubeVideo(String videoId) {
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }

    // For Read More Button
    public void readMore(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
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
                editor.putBoolean("isLoggedIn",false);
                editor.commit();

                startActivity(new Intent(AnimeDescriptionActivity.this, LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    // SQLITE Database
    private void addToFavorites() {

        animeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call DB Helper
                DatabaseHelper databaseHelper = new DatabaseHelper(AnimeDescriptionActivity.this);

                // Add a new Favorite anime
                FavoriteAnime favoriteAnime = new FavoriteAnime(mal_id, title, types, scores, episode, img);

                if (databaseHelper.getOne(mal_id)) {

                    databaseHelper.deleteOne(favoriteAnime);

                    Toast.makeText(AnimeDescriptionActivity.this, "This anime has been removed on your list", Toast.LENGTH_SHORT).show();
                }
                else {

                    String success = databaseHelper.addOne(favoriteAnime) ? "Added to your favorites" : "Something went wrong adding your anime";

                    Toast.makeText(AnimeDescriptionActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void removeFromFavorites() {

        animeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Call DB Helper
                DatabaseHelper databaseHelper = new DatabaseHelper(AnimeDescriptionActivity.this);
                
                // Check if anime is already existing on the db.
                if(databaseHelper.getOne(mal_id)) {
                    // Add a new Favorite anime
                    Toast.makeText(AnimeDescriptionActivity.this, "This anime is already added in your list", Toast.LENGTH_SHORT).show();

                } else {
                    FavoriteAnime favoriteAnime = new FavoriteAnime(mal_id, title, types, scores, episode, img);

                    Toast.makeText(AnimeDescriptionActivity.this, favoriteAnime.toString(), Toast.LENGTH_SHORT).show();

                    String success = databaseHelper.addOne(favoriteAnime) ? "Added to your favorites" : "Something went wrong adding your anime";

                    Toast.makeText(AnimeDescriptionActivity.this, success, Toast.LENGTH_SHORT).show();
                }
                


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        // Register our broadcast receiver
        registerReceiver(networkChangeListener, filter);

        Log.i(TAG,"onStart");
        Toast.makeText(AnimeDescriptionActivity.this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        Toast.makeText(AnimeDescriptionActivity.this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        Toast.makeText(AnimeDescriptionActivity.this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(networkChangeListener);

        Log.i(TAG,"onStop");
        Toast.makeText(AnimeDescriptionActivity.this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        Toast.makeText(AnimeDescriptionActivity.this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
        Toast.makeText(AnimeDescriptionActivity.this, "onRestart", Toast.LENGTH_SHORT).show();
    }
}