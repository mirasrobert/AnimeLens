package com.example.imotaku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imotaku.R;
import com.example.imotaku.model.FavoriteAnime;
import com.example.imotaku.model.Results;

import java.util.List;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    List<FavoriteAnime> animeList;
    Context context;
    RecyclerViewClickListener listener;

    public FavoriteRecyclerAdapter(List<FavoriteAnime> animeList, Context context, RecyclerViewClickListener listener) {
        this.animeList = animeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerAdapter.ViewHolder holder, int position) {

        // Change the data
        holder.title.setText(animeList.get(position).getName());
        holder.type.setText(animeList.get(position).getType());
        holder.score_no.setText(animeList.get(position).getScore());

        String episode = "Ep " + animeList.get(position).getEpisodes();
        holder.episodes.setText(episode);

        // Use Glide library to display images
        Glide.with(context)
                .load(animeList.get(position).getImage_url())
                .into(holder.animeImg);

    }

    @Override
    public int getItemCount() {
        return this.animeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, type, score_no, episodes;
        ImageView animeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            animeImg = itemView.findViewById(R.id.imgAnime);
            title = itemView.findViewById(R.id.animeName);
            type =  itemView.findViewById(R.id.type);
            score_no =  itemView.findViewById(R.id.score);
            episodes = itemView.findViewById(R.id.episodes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

}