package com.example.imotaku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopAnime {

    @SerializedName("request_hash")
    @Expose
    private String request_hash;

    @SerializedName("request_cached")
    @Expose
    private boolean request_cached;

    @SerializedName("request_cache_expiry")
    @Expose
    private int request_cache_expiry;

    // nested object
    @SerializedName("top")
    @Expose
    private List<Results> top;

    @SerializedName("last_page")
    @Expose
    private int last_page;

    public TopAnime(String request_hash, boolean request_cached, int request_cache_expiry, List<Results> top, int last_page) {
        this.request_hash = request_hash;
        this.request_cached = request_cached;
        this.request_cache_expiry = request_cache_expiry;
        this.top = top;
        this.last_page = last_page;
    }

    public String getRequest_hash() {
        return request_hash;
    }

    public void setRequest_hash(String request_hash) {
        this.request_hash = request_hash;
    }

    public boolean isRequest_cached() {
        return request_cached;
    }

    public void setRequest_cached(boolean request_cached) {
        this.request_cached = request_cached;
    }

    public int getRequest_cache_expiry() {
        return request_cache_expiry;
    }

    public void setRequest_cache_expiry(int request_cache_expiry) {
        this.request_cache_expiry = request_cache_expiry;
    }

    public List<Results> getTop() {
        return top;
    }

    public void setTop(List<Results> top) {
        this.top = top;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }
}
