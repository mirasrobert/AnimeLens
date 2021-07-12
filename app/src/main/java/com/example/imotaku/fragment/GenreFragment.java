package com.example.imotaku.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imotaku.AnimeDescriptionActivity;
import com.example.imotaku.R;

import org.jetbrains.annotations.NotNull;

public class GenreFragment extends Fragment {
    //fragment life cycle
    private static String TAG = "genreFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //lifecycle
        Log.i(TAG,"onCreateView");
        Toast.makeText(getActivity(),"onCreateView",Toast.LENGTH_SHORT).show();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre, container, false);

    }
}