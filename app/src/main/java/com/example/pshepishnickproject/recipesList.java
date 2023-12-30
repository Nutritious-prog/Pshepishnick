package com.example.pshepishnickproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class recipesList extends Fragment {

    public recipesList() {
        // Required empty public constructor
    }

    public static recipesList newInstance(String param1, String param2) {
        recipesList fragment = new recipesList();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipies_list, container, false);
        return view;
    }
}