package com.example.imotaku.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.imotaku.fragment.PG13Fragment;
import com.example.imotaku.fragment.OvaFragment;
import com.example.imotaku.fragment.SpecialFragment;
import com.example.imotaku.model.Results;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    public List<Results> listAnimes, ovaG, movieG, tvPG, ovaPG, moviePG;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                           List<Results> listAnimes, List<Results> ovaG, List<Results> movieG,
                           List<Results> tvPG, List<Results> ovaPG, List<Results> moviePG) {
        super(fragmentManager, lifecycle);

        this.listAnimes = listAnimes;
        this.ovaG = ovaG;
        this.movieG = movieG;

        this.tvPG = tvPG;
        this.ovaPG = ovaPG;
        this.moviePG = moviePG;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new PG13Fragment(tvPG, ovaPG, moviePG);
            case 2 :
                return new SpecialFragment();
        }

        return new OvaFragment(listAnimes, ovaG, movieG);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}