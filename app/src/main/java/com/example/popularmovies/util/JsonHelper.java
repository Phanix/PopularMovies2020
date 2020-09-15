package com.example.popularmovies.util;

import android.util.Log;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static final String TAG = JsonHelper.class.getSimpleName();
/*
    private String poster;
    private int id;
    private double voteAverage;
    private String title;
    private String language;
    private String overview;
    private String releaseDate;
    */
    public static List<Movie> getMovies(String jsonData) {

        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int totalResults = jsonObject.getInt("total_results");

            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                String posterPath = jsonArray.getJSONObject(i).getString("poster_path");
                int id = jsonArray.getJSONObject(i).getInt("id");
                String title =       jsonArray.getJSONObject(i).getString("title");
                double voteAverage = jsonArray.getJSONObject(i).getDouble("vote_average");
                String overview = jsonArray.getJSONObject(i).getString("overview");
                String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
                String language = jsonArray.getJSONObject(i).getString("original_language");
                Movie movie = new Movie(posterPath, id, voteAverage, title, language, overview, releaseDate);
                movies.add(movie);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Trailer> getTrailers(String jsonData){
        List<Trailer>trailers = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getJSONObject(i).getString("key");
                trailers.add(new Trailer(id));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return trailers;

    }
}
