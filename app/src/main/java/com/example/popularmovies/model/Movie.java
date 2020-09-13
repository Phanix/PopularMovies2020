package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
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

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster);
        parcel.writeInt(id);
        parcel.writeDouble(voteAverage);
        parcel.writeString(title);
        parcel.writeString(language);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }

    protected Movie(Parcel parcel){
        this.poster = parcel.readString();
        this.id = parcel.readInt();
        this.voteAverage = parcel.readDouble();
        this.title = parcel.readString();
        this.language = parcel.readString();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
    }

}
