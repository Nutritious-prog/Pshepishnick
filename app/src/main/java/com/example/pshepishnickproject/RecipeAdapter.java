package com.example.pshepishnickproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> allRecipes;
    private List<Recipe> displayedRecipes;

    public RecipeAdapter(List<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
        this.displayedRecipes = new ArrayList<>(allRecipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = displayedRecipes.get(position);
        holder.bind(recipe);

        holder.itemView.setOnClickListener(v -> {
            openRecipeDetailFragment(recipe, holder);
        });
    }

    private void openRecipeDetailFragment(Recipe recipe, @NonNull RecipeViewHolder holder) {
        // Create a Bundle to pass data to RecipeDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("recipeTitle", recipe.getTitle());
        bundle.putString("recipeDescription", recipe.getDescription());
        bundle.putInt("recipeTime", recipe.getPreparationDuration());
        bundle.putInt("recipeDiff", recipe.getDifficulty());
        bundle.putString("recipePhoto", recipe.getPhotoUrl());

        // Navigate to RecipeDetailFragment
        Navigation.findNavController(holder.itemView).navigate(R.id.action_recipesList_to_recipeDetails, bundle);
    }

    @Override
    public int getItemCount() {
        return displayedRecipes.size();
    }

    public void filter(String query) {
        if (allRecipes == null || allRecipes.isEmpty()) {
            return;
        }

        displayedRecipes.clear();

        if (query.isEmpty()) {
            displayedRecipes.addAll(allRecipes);
        } else {
            String lowercaseQuery = query.toLowerCase();
            for (Recipe recipe : allRecipes) {
                if (recipe.getTitle().toLowerCase().contains(lowercaseQuery)) {
                    displayedRecipes.add(recipe);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView timeTextView;
        private TextView difficultyTextView;
        private ImageView photoImageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            difficultyTextView = itemView.findViewById(R.id.difficultyTextView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }

        public void bind(Recipe recipe) {
            titleTextView.setText(recipe.getTitle());
            timeTextView.setText("Time: " + recipe.getPreparationDuration() + " min");
            difficultyTextView.setText("Difficulty: " + recipe.getDifficulty());
            Glide.with(itemView.getContext()).load(recipe.getPhotoUrl()).into(photoImageView);
        }
    }
}

