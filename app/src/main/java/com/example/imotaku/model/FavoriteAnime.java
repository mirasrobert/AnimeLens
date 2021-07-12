package com.example.imotaku.model;

public class FavoriteAnime {

    private int id;
    private String name;
    private String type;
    private String score;
    private String episodes;
    private String image_url;

    public FavoriteAnime(int id, String name, String type, String score, String episodes, String image_url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.score = score;
        this.episodes = episodes;
        this.image_url = image_url;
    }

    public FavoriteAnime() { }

    @Override
    public String
    toString() {
        return "FavoriteAnime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", score='" + score + '\'' +
                ", episodes='" + episodes + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
