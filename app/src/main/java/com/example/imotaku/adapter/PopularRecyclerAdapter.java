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
import com.example.imotaku.model.Results;

import java.util.List;

public class PopularRecyclerAdapter extends RecyclerView.Adapter<PopularRecyclerAdapter.ViewHolder> {
    List<Results> animeList;
    Context context;

    public PopularRecyclerAdapter(List<Results> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @NonNull
    @Override
    public PopularRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_anime, parent, false);
        PopularRecyclerAdapter.ViewHolder viewHolder = new PopularRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRecyclerAdapter.ViewHolder holder, int position) {

        // Change the data
        holder.title.setText(animeList.get(position).getTitle());
        holder.score_no.setText(animeList.get(position).getScore());

        String isAiring = animeList.get(position).isAiring() ? "Currently airing" : "Completed";
        holder.airing.setText(isAiring);

        // Use Glide library to display images
        Glide.with(context)
                .load(animeList.get(position).getImage_url())
                .into(holder.animeImg);

    }

    @Override
    public int getItemCount() {
        return this.animeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, airing, score_no;
        ImageView animeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            animeImg = itemView.findViewById(R.id.animeImg);
            title = itemView.findViewById(R.id.title);
            airing =  itemView.findViewById(R.id.airing);
            score_no =  itemView.findViewById(R.id.score_no);

        }
    }
}
