package com.example.popularmovies.util;

import android.net.Uri;

public class HttpHelper {


    public static Uri getUri(){
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                .appendQueryParameter("api_key", "f8001ba43a4a45d7dd13015aaa1f230a")
                .build();
        return uri;
    }

}
