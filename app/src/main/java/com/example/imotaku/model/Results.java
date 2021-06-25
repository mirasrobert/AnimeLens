package com.example.imotaku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    // Data Model for nested results object array from API
    @SerializedName("mal_id")
    @Expose
    private int mal_id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("airing")
    @Expose
    private boolean airing;

    @SerializedName("synopsis")
    @Expose
    private String synopsis;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("episodes")
    @Expose
    private String episodes;

    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("start_date")
    @Expose
    private String start_date;

    @SerializedName("end_date")
    @Expose
    private String end_date;

    @SerializedName("members")
    @Expose
    private String members;

    @SerializedName("rated")
    @Expose
    private String rated;


    public Results(int mal_id, String url, String image_url, String title, boolean airing, String synopsis, String type, String episodes, String score, String start_date, String end_date, String members, String rated) {
        this.mal_id = mal_id;
        this.url = url;
        this.image_url = image_url;
        this.title = title;
        this.airing = airing;
        this.synopsis = synopsis;
        this.type = type;
        this.episodes = episodes;
        this.score = score;
        this.start_date = start_date;
        this.end_date = end_date;
        this.members = members;
        this.rated = rated;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAiring() {
        return airing;
    }

    public void setAiring(boolean airing) {
        this.airing = airing;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }


}
