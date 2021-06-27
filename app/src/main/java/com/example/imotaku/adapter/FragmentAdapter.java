package com.example.imotaku.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.imotaku.fragment.HomeFragment;
import com.example.imotaku.fragment.MangaFragment;
import com.example.imotaku.fragment.OvaFragment;
import com.example.imotaku.fragment.SpecialFragment;
import com.example.imotaku.model.Anime;
import com.example.imotaku.model.Results;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        // To Select Fragments according to position

        Fragment fragment = null;

        switch (position)
        {
            case 1:
                return new MangaFragment();

        }

        return new OvaFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
