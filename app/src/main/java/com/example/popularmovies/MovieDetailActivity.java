package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.util.HttpHelper;
import com.example.popularmovies.util.JsonHelper;
import com.example.popularmovies.util.TraileRecylerViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.microedition.khronos.egl.EGLDisplay;

import static com.example.popularmovies.MainActivity.KEY_FAVORITES;
import static com.example.popularmovies.MainActivity.KEY_MOVIE_EXTRA;
import static com.example.popularmovies.MainActivity.KEY_SHARED_PREFERENCES;

public class MovieDetailActivity extends AppCompatActivity implements TraileRecylerViewAdapter.TrailerClickHandler {


    TextView voteAverage;
    TextView description;
    TextView releaseDate;
    ImageView movieImage;
    Button favoriteButton;

    private TraileRecylerViewAdapter adapter;
    private RecyclerView trailerRecyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isFav;
    Set<String> popularMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Movie movie = getIntent().getExtras().getParcelable(MainActivity.KEY_MOVIE_EXTRA);
        sharedPreferences = getSharedPreferences( KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        favoriteButton = findViewById(R.id.btn_fav);

        popularMovies = sharedPreferences.getStringSet(KEY_FAVORITES, null);


        if(popularMovies != null){
            if(popularMovies.contains(movie.getTitle())){
                favoriteButton.setBackgroundColor(Color.RED);
                isFav = true;
            }
        }


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav){
                    favoriteButton.setBackgroundColor(Color.GREEN);
                    popularMovies.remove(movie.getTitle());
                    editor.clear();
                    editor.apply();
                    editor.putStringSet(KEY_FAVORITES, popularMovies);
                    editor.apply();
                    return;
                }
                favoriteButton.setBackgroundColor(Color.RED);
                if(popularMovies == null){
                    popularMovies = new HashSet<>();
                }
                if(!popularMovies.contains(movie.getTitle())) {
                    popularMovies.add(movie.getTitle());
                    editor.putStringSet(KEY_FAVORITES, popularMovies);
                    editor.apply();
                }

            }
        });

        movieImage = findViewById(R.id.movie_image);
        voteAverage = findViewById(R.id.tv_rate);
        description = findViewById(R.id.tv_description);
        releaseDate = findViewById(R.id.tv_release_date);

        trailerRecyclerView = findViewById(R.id.rv_trailers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(trailerRecyclerView.getContext(), linearLayoutManager.getOrientation());
        trailerRecyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new TraileRecylerViewAdapter(this);
        trailerRecyclerView.setAdapter(adapter);



        Picasso.get().load("http://image.tmdb.org/t/p/w185" + movie.getPoster()).into(movieImage);
        voteAverage.setText(String.valueOf(movie.getVoteAverage()) + "/10");
        description.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        // /movie/337401/videos


        setTitle(movie.getTitle());

        new GetTrailerAsyncTask().execute(String.valueOf(movie.getId()));


    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        Uri uri = Uri.parse("vnd.youtube://" + trailer.getTrailerId());
        Intent intent  = new Intent(Intent.ACTION_VIEW, uri);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public class GetTrailerAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder trailers = new StringBuilder();

            try {
                URL url = new URL(HttpHelper.getUriTrailer(strings[0]).toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                if(data != -1){
                    do{
                        trailers.append((char)data);
                    }while((data = reader.read()) != -1);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return trailers.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<Trailer> trailerList = JsonHelper.getTrailers(s);
            adapter.setTrailers(trailerList);
        }
    }
}