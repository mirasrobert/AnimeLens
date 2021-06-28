package com.example.imotaku.API;

import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;
import com.example.imotaku.model.TopAnime;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeHTTP {
    // /v3/search/anime?status=publishing&order_by=score&rated=r17
    // hentai: /v3/search/anime?order_by=score&rated=r&rated=rx
    // upcming: /v3/search/anime?status=upcoming
    // thisyear: /v3/search/anime?start_date=2021-01-01
    // airing : /v3/search/anime?status=airing
    // OVA & RATED G  /v3/search/anime?type=ova&rated=g
    // TV & RATED G /v3/search/anime?type=tv&rated=g
    // MOVIE & RATED G /v3/search/anime?type=movie&rated=g
    // top: /v3/top/anime/1/bypopularity

    @GET("/v3/search/anime?status=publishing&order_by=score&rated=r17")
    Call<Anime> getAnimes();

    @GET("v3/search/anime?order_by=score&type=movie")
    Call<Anime> getPopularAnimes();

    @GET("/v3/search/anime?status=upcoming")
    Call<Anime> getUpcomingAnimes();

    @GET("/v3/search/anime?start_date=2021-01-01&order_by=score")
    Call<Anime> getThisYearAnimes();

    @GET("/v3/search/anime?status=airing")
    Call<Anime> getAiringAnimes();

    @GET("/v3/search/anime?type=tv&rated=g")
    Call<Anime> getTvAndRatedGAnimes();

    @GET("/v3/search/anime?type=ova&rated=g")
    Call<Anime> getOvaAndRatedGAnimes();

    @GET("/v3/search/anime?type=movie&rated=g")
    Call<Anime> getMovieAndRatedGAnimes();

    @GET("/v3/search/anime?type=tv&rated=pg13")
    Call<Anime> getTvAndRatedPG13Animes();

    @GET("/v3/search/anime?type=ova&rated=pg13")
    Call<Anime> getOvaAndRatedPG13Animes();

    @GET("/v3/search/anime?type=movie&rated=pg13")
    Call<Anime> getMovieAndRatedPG13Animes();

    @GET("/v3/top/anime/1/bypopularity")
    Call<TopAnime> getTopAnimes();

}
