package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imotaku.API.AnimeHTTP;
import com.example.imotaku.model.Genre;
import com.example.imotaku.model.SingleAnime;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(AnimeDescriptionActivity.this, R.color.bg_primary));

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
        int mal_id = 20;
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
                     String title = response.body().getTitle();
                     String img = response.body().getImage_url();
                     String scores = response.body().getScore();
                     ranks = (response.body().getRank() != null) ? response.body().getRank() : ranks;
                     String favorites = response.body().getFavorites();
                     String types = response.body().getType();
                     String episode = response.body().getEpisodes();

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
}