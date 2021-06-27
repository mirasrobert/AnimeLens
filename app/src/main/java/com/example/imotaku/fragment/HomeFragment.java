package com.example.imotaku.fragment;

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

import com.example.imotaku.R;
import com.example.imotaku.adapter.FragmentAdapter;
import com.example.imotaku.adapter.PopularRecyclerAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.model.Results;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeFragment extends Fragment {

    public RecyclerView recyclerView, popularRecycler, thisYearRecycler;
    public List<Results> listAnimes, popularAnimes, thisYearAnimes;

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
        // 2. Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        RecyclerAdapter adapter = new RecyclerAdapter(this.listAnimes, getActivity());

        // Set Adapter
        recyclerView.setAdapter(adapter);
    }

    public void popularAnimesRecycler() {
        // RecyclerView2 - Popular Animes
        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        PopularRecyclerAdapter popularAdapter = new PopularRecyclerAdapter(this.popularAnimes, getActivity());
        // Set Adapter
        popularRecycler.setAdapter(popularAdapter);
    }

    public void thisYearAnimesRecycler() {
        // RecyclerView2 - Popular Animes
        thisYearRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        thisYearRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        PopularRecyclerAdapter thisYearRecyclerAdapter = new PopularRecyclerAdapter(this.thisYearAnimes, getActivity());
        // Set Adapter
        thisYearRecycler.setAdapter(thisYearRecyclerAdapter);
    }
}