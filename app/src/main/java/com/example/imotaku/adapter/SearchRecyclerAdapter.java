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

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    List<Results> animeList;
    Context context;
    private RecyclerViewClickListener listener;

    public SearchRecyclerAdapter(List<Results> animeList, Context context, RecyclerViewClickListener listener) {
        this.animeList = animeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {

        // Change the data
        holder.name.setText(animeList.get(position).getTitle());

        String date = animeList.get(position).getStart_date();

        String[] dateParts = date.split("-"); // Split the url by slash

        date =  dateParts[0];

        String dateWithEp = date + ", Ep: " + animeList.get(position).getEpisodes();

        holder.date.setText(dateWithEp);

        holder.type.setText(animeList.get(position).getType());

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

        TextView name, date, type;
        ImageView animeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            animeImg = itemView.findViewById(R.id.animeImg);
            name = itemView.findViewById(R.id.name);
            date =  itemView.findViewById(R.id.date);
            type =  itemView.findViewById(R.id.type);

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