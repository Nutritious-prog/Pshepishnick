package com.example.pshepishnickproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class RecipeDetails extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView timeTextView;
    private TextView difficultyTextView;
    private ImageView photoImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        timeTextView = view.findViewById(R.id.timeTextView);
        difficultyTextView = view.findViewById(R.id.difficultyTextView);
        photoImageView = view.findViewById(R.id.photoImageView);

        // Get data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("recipeTitle", "");
            String description = args.getString("recipeDescription", "");
            String time = String.valueOf(args.getInt("recipeTime", 0));
            String diff = String.valueOf(args.getInt("recipeDiff", 0));
            String photo = args.getString("recipePhoto", "");

            // Bind data to views
            titleTextView.setText(title);
            descriptionTextView.setText(description);
            timeTextView.setText("Time: " + time);
            difficultyTextView.setText("Difficulty: " + diff);
            Glide.with(view.getContext()).load(photo).into(photoImageView);
            // Bind additional details
        }

        // Set up back button click listener
        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
