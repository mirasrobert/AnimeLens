package com.example.imotaku.API;

import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeHTTP {
    // /v3/search/anime?status=publishing&order_by=score&rated=r17
    // hentai: /v3/search/anime?order_by=score&rated=r&rated=rx

    @GET("/v3/search/anime?status=publishing&order_by=score&rated=r17")
    Call<Anime> getAnimes();

    @GET("v3/search/anime?order_by=score&type=movie")
    Call<Anime> getPopularAnimes();
}
