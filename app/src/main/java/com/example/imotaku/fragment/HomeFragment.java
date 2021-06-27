package com.example.imotaku.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imotaku.R;
import com.example.imotaku.adapter.PopularRecyclerAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.model.Results;

import java.util.List;

public class HomeFragment extends Fragment {

    public List<Results> listAnimes, popularAnimes;

    public HomeFragment(List<Results> listAnimes, List<Results> popularAnimes) {
        this.listAnimes = listAnimes;
        this.popularAnimes = popularAnimes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Get reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView popularRecycler = (RecyclerView) rootView.findViewById(R.id.popularRecycler);

        // 2. Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 3. Pass and Create Adapter
        // Pass Data to RecyclerView
        RecyclerAdapter adapter = new RecyclerAdapter(this.listAnimes, getActivity());


        // Set Adapter
        recyclerView.setAdapter(adapter);

        // RecyclerView2 - Popular Animes
        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        PopularRecyclerAdapter popularAdapter = new PopularRecyclerAdapter(this.popularAnimes, getActivity());
        // Set Adapter
        popularRecycler.setAdapter(popularAdapter);


        return rootView;
    }
}