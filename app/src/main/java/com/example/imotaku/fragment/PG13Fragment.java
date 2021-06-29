package com.example.imotaku.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imotaku.AnimeDescriptionActivity;
import com.example.imotaku.R;
import com.example.imotaku.adapter.PopularRecyclerAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.model.Results;

import java.util.List;

public class PG13Fragment extends Fragment {

    public RecyclerView recyclerView, ovaRecycler, thisYearRecycler;
    public List<Results> tvGList, ovaGList, movieGList;

    private RecyclerAdapter.RecyclerViewClickListener listener;

    public PG13Fragment(List<Results> listAnimes, List<Results> ovaG, List<Results> movieGList) {
        this.tvGList = listAnimes;
        this.ovaGList = ovaG;
        this.movieGList = movieGList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pg13, container, false);

        // 1. Get reference of RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        ovaRecycler = (RecyclerView) rootView.findViewById(R.id.ovaRecycler);
        thisYearRecycler = (RecyclerView) rootView.findViewById(R.id.thisYearRecycler);

        // Pass data to recyclerview
        tvPGRecycler();;
        ovaPGRecycler();
        moviePGRecycler();

        return rootView;
    }

    public void tvPGRecycler() {
        setOnClickListener(this.tvGList);
        // 2. Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        RecyclerAdapter adapter = new RecyclerAdapter(this.tvGList, getActivity(), listener);

        // Set Adapter
        recyclerView.setAdapter(adapter);
    }

    public void ovaPGRecycler() {
        setOnClickListener(this.ovaGList);
        // RecyclerView2 - Popular Animes
        ovaRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ovaRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        RecyclerAdapter popularAdapter = new RecyclerAdapter(this.ovaGList, getActivity(), listener);
        // Set Adapter
        ovaRecycler.setAdapter(popularAdapter);
    }

    public void moviePGRecycler() {
        setOnClickListener(this.movieGList);

        // RecyclerView2 - Popular Animes
        thisYearRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        thisYearRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        RecyclerAdapter thisYearRecyclerAdapter = new RecyclerAdapter(this.movieGList, getActivity(), listener);
        // Set Adapter
        thisYearRecycler.setAdapter(thisYearRecyclerAdapter);
    }

    private void setOnClickListener(List<Results> list) {
        // Use the click listener interface to go to each anime description by position
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getActivity(), AnimeDescriptionActivity.class);

                String mal_id = Integer.toString(list.get(position).getMal_id());

                intent.putExtra("mal_id", list.get(position).getMal_id());
                // Start the activity with data passing to the next activity
                startActivity(intent);
                //Toast.makeText(getActivity(), list.get(position).getTitle().toLowerCase() + mal_id, Toast.LENGTH_SHORT).show();
            }
        };

    }
}