package com.example.imotaku.model;

public class Genre {

    private int mal_id;

    private String type;

    private String name;

    private String url;

    public Genre(int mal_id, String type, String name, String url) {
        this.mal_id = mal_id;
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
