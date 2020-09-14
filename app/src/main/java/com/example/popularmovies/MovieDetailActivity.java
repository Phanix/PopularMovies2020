package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {


    TextView voteAverage;
    TextView description;
    TextView releaseDate;
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieImage = findViewById(R.id.movie_image);
        voteAverage = findViewById(R.id.tv_rate);
        description = findViewById(R.id.tv_description);
        releaseDate = findViewById(R.id.tv_release_date);

        Movie movie = getIntent().getExtras().getParcelable("movie");
        Log.i("TAG", "onCreate: " + movie.getTitle());
        Picasso.get().load("http://image.tmdb.org/t/p/w185" + movie.getPoster()).into(movieImage);
        voteAverage.setText(String.valueOf(movie.getVoteAverage()) + "/10");
        description.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());

        setTitle(movie.getTitle());



    }
}