package com.example.popularmovies.util;

import android.net.Uri;

public class HttpHelper {



    public static Uri getUri(String query){
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/").buildUpon()
                .appendPath(query)
                .appendQueryParameter("api_key", "f8001ba43a4a45d7dd13015aaa1f230a")
                .build();
        return uri;
    }

    public static Uri getUriTrailer(String movieId){
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/").buildUpon()
                .appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter("api_key", "f8001ba43a4a45d7dd13015aaa1f230a")
                .appendQueryParameter("language", "en-US")
                .build();
        return uri;
        //https://api.themoviedb.org/3/movie/337401/videos?api_key=f8001ba43a4a45d7dd13015aaa1f230a&language=en-US
    }
    //5d221db394d8a87d3441b212

}
