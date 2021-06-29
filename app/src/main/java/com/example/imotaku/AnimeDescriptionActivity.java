package com.example.imotaku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    public final String BASE_URL = "https://api.jikan.moe";

    // Views
    TextView animeName, score, rank, favorite, type, status, episodes, genre, sypnosis;
    ImageView animeImg;

    private Call<SingleAnime> singleAnimeCall;
    private List<SingleAnime> singleAnimeList = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        sypnosis = findViewById(R.id.sypnosis);

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


                     String title = response.body().getTitle();
                     String img = response.body().getImage_url();
                     String scores = response.body().getScore();
                     String ranks = response.body().getRank();
                     String favorites = response.body().getFavorites();
                     String types = response.body().getType();
                     String episode = response.body().getEpisodes();

                     genreList = response.body().getGenres();

                     String background = response.body().getSynopsis();
                     String trailer_url = response.body().getTrailer_url();

                     updateUI(title, img, scores, ranks, favorites, types, episode, genreList, background, trailer_url);

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
                          String trailer_url) {

        // Chdck for null values
        String animeTitle = (title == null) ? "N/A" : title;
        String animeScore = (scores == null) ? "N/A" : scores;
        String animeRank = (ranks == null) ? "N/A" : ranks;
        String animeFavorite = (favorites == null) ? "N/A" : favorites;
        String animeType = (types == null) ? "N/A" : types;
        String animeEpisodes = (episode == null) ? "N/A" : "Ep: " + episode;

        String animeGenres = "";

        for(Genre genre : genres) {
               animeGenres += genre.getName() + " ";
        }

        String animeDescription = (background == null) ? "N/A" : background;

        // Update the UI with the correct values

        // Change Image
        Glide.with(AnimeDescriptionActivity.this)
                .load(img)
                .into(animeImg);

        animeName.setText(animeTitle);
        score.setText(animeScore);
        rank.setText(animeRank);
        favorite.setText(animeFavorite);
        type.setText(animeType);
        episodes.setText(animeEpisodes);
        genre.setText(animeGenres);
        sypnosis.setText(animeDescription);

        startYouTubeVideo(getYoutubeId(trailer_url));

    }

    private String getYoutubeId(String url) {

        String[] urlParts = url.split("/"); // Split the url by slash

        String lastPart = urlParts[urlParts.length - 1]; // get the last index

        String[] mal_id = lastPart.split("\\?"); // Split the url by question mark

        return mal_id[0]; // Return the id
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
}