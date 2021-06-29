package com.example.imotaku.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.imotaku.fragment.PG13Fragment;
import com.example.imotaku.fragment.OvaFragment;
import com.example.imotaku.fragment.R17Fragment;


import com.example.imotaku.model.Results;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    public List<Results> listAnimes, ovaG, movieG, tvPG, ovaPG, moviePG, ovaRatedR17List,tvRatedR17List, MovieRatedR17List;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                           List<Results> listAnimes, List<Results> ovaG, List<Results> movieG,
                           List<Results> tvPG, List<Results> ovaPG, List<Results> moviePG,
                           List<Results> tvRatedR17List,  List<Results> ovaRatedR17List, List<Results> MovieRatedR17List) {
        super(fragmentManager, lifecycle);

        this.listAnimes = listAnimes;
        this.ovaG = ovaG;
        this.movieG = movieG;

        this.tvPG = tvPG;
        this.ovaPG = ovaPG;
        this.moviePG = moviePG;

        this.ovaRatedR17List = ovaRatedR17List;
        this.tvRatedR17List = tvRatedR17List;
        this.MovieRatedR17List = MovieRatedR17List;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new PG13Fragment(tvPG, ovaPG, moviePG);
            case 2 :
                return new R17Fragment(tvRatedR17List, ovaRatedR17List, MovieRatedR17List);
        }

        return new OvaFragment(listAnimes, ovaG, movieG);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}