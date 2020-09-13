package com.example.popularmovies.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecylerViewAdapter  extends RecyclerView.Adapter<MovieRecylerViewAdapter.MovieViewHolder>{

    private Context context;
    protected MovieClickHandler clickHandler;
    protected List<Movie> movies;

    public MovieRecylerViewAdapter(Context context, MovieClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position), context);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_poster);
            imageView.setOnClickListener(this);
        }

        public void bind(Movie movie, Context context){
            Picasso.get().load("http://image.tmdb.org/t/p/w185" + movie.getPoster()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            clickHandler.onMovieClick(movies.get(getAdapterPosition()));
        }
    }


    public interface MovieClickHandler{
        void onMovieClick(Movie movie);
    }
}
