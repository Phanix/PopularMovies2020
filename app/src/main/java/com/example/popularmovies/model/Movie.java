package com.example.popularmovies.model;

public class Movie {
    private String poster;
    private int id;
    private double voteAverage;
    private String title;
    private String language;
    private String overview;
    private String releaseDate;

    public Movie(String poster, int id, double voteAverage, String title, String language, String overview, String releaseDate) {
        this.poster = poster;
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.language = language;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie() {
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
