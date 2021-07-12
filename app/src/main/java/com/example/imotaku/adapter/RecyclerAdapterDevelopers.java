package com.example.imotaku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imotaku.R;

public class RecyclerAdapterDevelopers extends RecyclerView.Adapter<RecyclerAdapterDevelopers.ViewHolder> {

    String data1[],data2[], ourTitle[];
    int images[];
    Context context;

    public RecyclerAdapterDevelopers (Context context, String[] data1, String[] data2, int[] images, String title[]) {
        this.data1 = data1;
        this.data2 = data2;
        this.images = images;
        this.context = context;
        this.ourTitle  = title;
    }

    @NonNull
    @Override
    public RecyclerAdapterDevelopers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.developer_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterDevelopers.ViewHolder holder, int position) {
        holder.name.setText(data1[position]);
        holder.bday.setText(data2[position]);
        holder.title.setText(ourTitle[position]);
        holder.personImg.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, bday, title;
        ImageView personImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.email_enter);
            bday = itemView.findViewById(R.id.bday);
            personImg = itemView.findViewById(R.id.personImg);
            title = itemView.findViewById(R.id.title);
        }
    }
}
