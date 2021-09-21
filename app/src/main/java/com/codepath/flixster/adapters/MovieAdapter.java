package com.codepath.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.flixster.DetailActivity;
import com.codepath.flixster.R;
import com.codepath.flixster.databinding.ItemMovieBinding;
import com.codepath.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the ViewHolder
        holder.binding.setMovie(movie); // included :1. binding.tvTitle.setText(movie.getTitle()); 2.binding.tvOverview.setText(movie.getOverview());
        holder.binding.executePendingBindings();
        holder.showImage(movie);
        holder.bind(movie);
    }


    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final ItemMovieBinding binding;
        /*
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        */

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = ItemMovieBinding.bind(itemView);
            /*
            tvTitle = binding.tvTitle;
            tvOverview = binding.tvOverview;
            ivPoster = binding.ivPoster;
            container = binding.container;
             */
        }

        public void showImage(Movie movie){
            String imageUrl;
            // if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // then imageUrl =  back drop image
                imageUrl = movie.getBackdropPath();
            }else{
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }

            int radius = 50;
            Glide.with(context).load(imageUrl).transform(new CenterInside(),new RoundedCorners(radius)).into(binding.ivPoster);
        }


        public void bind(Movie movie) {
            // 1. Register click listener on the whole row
            binding.container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });
        }
    }

}
