package com.example.imotaku.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imotaku.AnimeDescriptionActivity;
import com.example.imotaku.R;
import com.example.imotaku.adapter.PopularRecyclerAdapter;
import com.example.imotaku.adapter.RecyclerAdapter;
import com.example.imotaku.model.Results;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OvaFragment extends Fragment {

    private static String TAG = "Category Fragment";

    public RecyclerView recyclerView, ovaRecycler, thisYearRecycler;
    public List<Results> listAnimes, ovaGList, movieGList;

    public RecyclerAdapter recyclerAdapter;

    private RecyclerAdapter.RecyclerViewClickListener listener;

    public OvaFragment(List<Results> listAnimes, List<Results> ovaG, List<Results> movieGList) {
        this.listAnimes = listAnimes;
        this.ovaGList = ovaG;
        this.movieGList = movieGList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_ova, container, false);

        // 1. Get reference of RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        ovaRecycler = (RecyclerView) rootView.findViewById(R.id.ovaRecycler);
        thisYearRecycler = (RecyclerView) rootView.findViewById(R.id.thisYearRecycler);

        // Pass data to recyclerview
        tvGRecycler();;
        ovaGRecycler();
        movieGRecycler();

        return rootView;
    }

    public void tvGRecycler() {
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

    public void ovaGRecycler() {
        setOnClickListener(this.ovaGList);
        // RecyclerView2 - Popular Animes
        ovaRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ovaRecycler.setItemAnimator(new DefaultItemAnimator());

        // Pass Data to RecyclerView
        RecyclerAdapter popularAdapter = new RecyclerAdapter(this.ovaGList, getActivity(), listener);
        // Set Adapter
        ovaRecycler.setAdapter(popularAdapter);
    }

    public void movieGRecycler() {

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

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach");
        Toast.makeText(getActivity(),"onAttach",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        Toast.makeText(getActivity(),"onCreate",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"onActivityCreated");
        Toast.makeText(getActivity(),"onActivityCreated",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
        Toast.makeText(getActivity(),"onStart",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        Toast.makeText(getActivity(),"onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        Toast.makeText(getActivity(),"onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
        Toast.makeText(getActivity(),"onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView");
        Toast.makeText(getActivity(),"onDestroyView",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        Toast.makeText(getActivity(),"onDestroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach");
        Toast.makeText(getActivity(),"onDetach",Toast.LENGTH_SHORT).show();
    }

}