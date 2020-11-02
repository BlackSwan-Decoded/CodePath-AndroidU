package com.codepath.application.flixter.adapters;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codepath.application.flixter.DetailActivity;
import com.codepath.application.flixter.MainActivity;
import com.codepath.application.flixter.R;
import com.codepath.application.flixter.databinding.ItemMoviesBinding;
import com.codepath.application.flixter.databinding.ItemMoviesPopularBinding;
import com.codepath.application.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    public final int UNPOPULAR = 0, POPULAR = 1;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getRating() > 6)
            return POPULAR;
        return UNPOPULAR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case POPULAR:
                View v1 = inflater.inflate(R.layout.item_movies_popular, parent, false);
                viewHolder = new ViewHolderPopular(v1);
                break;
            case UNPOPULAR:
            default:
                View v2 = inflater.inflate(R.layout.item_movies, parent, false);
                viewHolder = new ViewHolderUnpopular(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Movie movie;

        switch (viewHolder.getItemViewType()) {
            case POPULAR:
                ViewHolderPopular vh1 = (ViewHolderPopular) viewHolder;
                Log.d("MovieAdapter", "onBindViewHolder " + position);
                //Get the movie at the passed in position
                movie = movies.get(position);
                //Bind the movie data into the VH
                vh1.bind(movie);
                break;
            case UNPOPULAR:
            default:
                ViewHolderUnpopular vh2 = (ViewHolderUnpopular) viewHolder;
                Log.d("MovieAdapter", "onBindViewHolder " + position);
                //Get the movie at the passed in position
                movie = movies.get(position);
                //Bind the movie data into the VH
                vh2.bind(movie);
                break;
        }
    }

    public class ViewHolderPopular extends RecyclerView.ViewHolder{

        RelativeLayout container;
        ImageView ivPoster;
        ImageView playIcon;

        final ItemMoviesPopularBinding binding;

        public ViewHolderPopular (@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            playIcon = itemView.findViewById(R.id.playIcon);
            container = itemView.findViewById(R.id.container);
            binding = ItemMoviesPopularBinding.bind(itemView);
        }

        public void bind(final Movie movie) {
            String imageUrl = movie.getBackdropPath();

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                binding.tvTitle.setText(movie.getTitle());
                binding.tvOverview.setText(movie.getOverview());
                binding.executePendingBindings();
            }

            Glide.with(context).load(imageUrl)
                    .placeholder(R.drawable.ic_iconfinder_camera_1216589)
                    .fitCenter()
                    .transform(new RoundedCorners(10))
                    .into(ivPoster);

            Glide.with(context).load(R.drawable.ic_baseline_play_circle_outline_24)
                    .into(playIcon);

            //1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context, (View)container, "profile");
                    context.startActivity(i);
                }
            });
        }
    }

    public class ViewHolderUnpopular extends RecyclerView.ViewHolder{

        RelativeLayout container;
        ImageView ivPoster;
        ImageView playIcon;

        final ItemMoviesBinding binding;

        public ViewHolderUnpopular(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            playIcon = itemView.findViewById(R.id.playIcon);
            container = itemView.findViewById(R.id.container);
            binding = ItemMoviesBinding.bind(itemView);
        }

        public void bind(final Movie movie) {

            String imageUrl;

            binding.tvTitle.setText(movie.getTitle());
            binding.tvOverview.setText(movie.getOverview());
            binding.executePendingBindings();

            //if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //then imageUrl = back drop path
                imageUrl = movie.getBackdropPath();
                Glide.with(context).load(R.drawable.ic_baseline_play_circle_outline_24)
                        .into(playIcon);
            }else{
                //else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl)
                    .placeholder(R.drawable.ic_iconfinder_camera_1216589)
                    .fitCenter()
                    .transform(new RoundedCorners(10))
                    .into(ivPoster);

            //1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context, (View)binding.tvOverview, "profile");
                    context.startActivity(i);
                }
            });
        }
    }
}
