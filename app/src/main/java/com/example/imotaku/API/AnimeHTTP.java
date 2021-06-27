package com.example.imotaku.API;

import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeHTTP {
    // /v3/search/anime?status=publishing&order_by=score&rated=r17
    // hentai: /v3/search/anime?order_by=score&rated=r&rated=rx
    // upcming: /v3/search/anime?status=upcoming
    // thisyear: /v3/search/anime?start_date=2021-01-01
    // airing : /v3/search/anime?status=airing

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

}
