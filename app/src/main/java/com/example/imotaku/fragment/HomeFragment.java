package com.example.imotaku.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imotaku.AnimeDescriptionActivity;
import com.example.imotaku.R;
import com.example.imotaku.adapter.FragmentAdapter;
import com.example.imotaku.adapter.PopularRecyclerAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.model.Results;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeFragment extends Fragment {

    public RecyclerView recyclerView, popularRecycler, thisYearRecycler;
    public RecyclerAdapter recyclerAdapter;
    public List<Results> listAnimes, popularAnimes, thisYearAnimes;
    private RecyclerAdapter.RecyclerViewClickListener listener;

    public HomeFragment(List<Results> listAnimes, List<Results> popularAnimes, List<Results> thisYearAnimes) {
        this.listAnimes = listAnimes;
        this.popularAnimes = popularAnimes;
        this.thisYearAnimes = thisYearAnimes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Get reference of RecyclerView
         recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
         popularRecycler = (RecyclerView) rootView.findViewById(R.id.popularRecycler);
         thisYearRecycler = (RecyclerView) rootView.findViewById(R.id.thisYearRecycler);

         // Pass data to recyclerview
        topAnimeRecycler();;
        popularAnimesRecycler();
        thisYearAnimesRecycler();

        return rootView;
    }

    public void topAnimeRecycler() {
        setOnClickListener(this.listAnimes);
        // 2. Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        RecyclerAdapter adapter = new RecyclerAdapter(this.listAnimes, getActivity(), listener);

        // Set Adapter
        recyclerView.setAdapter(adapter);
    }

    public void popularAnimesRecycler() {

        setOnClickListener(this.popularAnimes);
        // RecyclerView2 - Popular Animes
        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        RecyclerAdapter popularAdapter = new RecyclerAdapter(this.popularAnimes, getActivity(), listener);
        // Set Adapter
        popularRecycler.setAdapter(popularAdapter);
    }

    public void thisYearAnimesRecycler() {
        setOnClickListener(this.thisYearAnimes);
        // RecyclerView2 - Popular Animes
        thisYearRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        thisYearRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        RecyclerAdapter thisYearRecyclerAdapter = new RecyclerAdapter(this.thisYearAnimes, getActivity(), listener);
        // Set Adapter
        thisYearRecycler.setAdapter(thisYearRecyclerAdapter);
    }

    private void setOnClickListener(List<Results> list) {
        // Use the click listener interface to go to each anime description by position
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getActivity(), AnimeDescriptionActivity.class);

                //String mal_id = Integer.toString(list.get(position).getMal_id());

                intent.putExtra("mal_id", list.get(position).getMal_id());
                // Start the activity with data passing to the next activity
                startActivity(intent);
                //Toast.makeText(getActivity(), list.get(position).getTitle().toLowerCase() + mal_id, Toast.LENGTH_SHORT).show();
            }
        };

    }

}