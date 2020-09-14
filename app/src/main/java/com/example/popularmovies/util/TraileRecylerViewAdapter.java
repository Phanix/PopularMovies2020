package com.example.popularmovies.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;

import java.util.List;

public class TraileRecylerViewAdapter extends  RecyclerView.Adapter<TraileRecylerViewAdapter.TrailerViewHolder>{

    protected List<Trailer> trailers;
    protected TrailerClickHandler trailerClickHandler;

    public interface TrailerClickHandler{
        void onTrailerClick(Trailer trailer);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public TraileRecylerViewAdapter(TrailerClickHandler trailerClickHandler) {
        this.trailerClickHandler = trailerClickHandler;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_view, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView trailerIndex;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerIndex = itemView.findViewById(R.id.tv_trailer);
            itemView.setOnClickListener(this);

        }
        public void bind(){
            trailerIndex.setText("Trailer: " + (getAdapterPosition() + 1));
        }

        @Override
        public void onClick(View v) {
            trailerClickHandler.onTrailerClick(trailers.get(getAdapterPosition()));
        }
    }
}
