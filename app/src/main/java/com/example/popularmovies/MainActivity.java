package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements MovieRecylerViewAdapter.MovieClickHandler {
    SharedPreferences sharedPreferences;
    String [] options = {"popular", "top_rated"};
    private ImageView imgNoInternet;
    RecyclerView recyclerView;
    ListView favoriteListView;
    MovieRecylerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.example.popularmovies", MODE_PRIVATE);

        favoriteListView = findViewById(R.id.favorites__list_view);
        imgNoInternet = findViewById(R.id.img_no_internet);
        imgNoInternet.setVisibility(GONE);
        recyclerView = findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MovieRecylerViewAdapter(this, this);
        recyclerView.setAdapter(adapter);
        checkNetWork(options[0]);

    }



    private void getFavorites(){
        favoriteListView.setVisibility(View.VISIBLE);
        Set<String> favorites =  sharedPreferences.getStringSet("favorites", null);
        if(favorites == null) return;
        String [] fa = new String[favorites.size()];
        int i = 0;
        for(String fav : favorites){
            fa[i] = fav;
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fa);
        favoriteListView.setAdapter(adapter);
    }

    private void checkNetWork(String option){
        favoriteListView.setVisibility(GONE);
        favoriteListView.setVisibility(GONE);
        if(isOnline()) {
            imgNoInternet.setVisibility(GONE);
            new GetDataAsyncTask().execute(option);
        }
        else{
            imgNoInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private class GetDataAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            Uri uri = HttpHelper.getUri(strings[0]);
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

                List<Movie> movies =  JsonHelper.getMovies(s);
                adapter.setMovies(movies);

            }else{
                Log.i("TAG", "onPostExecute: no data");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_popular:
               checkNetWork(options[0]);
                return true;
            case R.id.action_top_rated:
                checkNetWork(options[1]);
                return true;
            case R.id.action_favorites:
                getFavorites();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}