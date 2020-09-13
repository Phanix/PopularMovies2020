package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.util.HttpHelper;
import com.example.popularmovies.util.JsonHelper;
import com.example.popularmovies.util.MovieRecylerViewAdapter;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieRecylerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MovieRecylerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        new GetDataAsyncTask().execute();
    }

    private class GetDataAsyncTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            Uri uri = HttpHelper.getUri();
            try {
                URL url = new URL(uri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                StringBuilder builder = new StringBuilder();
                String line = "";
                int data = reader.read();
                if(data != -1) {
                    do {
                        builder.append((char) data);
                    } while ((data = reader.read()) != -1);
                }
                return builder.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                Log.i("TAG", "onPostExecute: " + s);
                List<Movie> movies =  JsonHelper.getMovies(s);
                adapter.setMovies(movies);

            }else{
                Log.i("TAG", "onPostExecute: no data");
            }
        }
    }
}