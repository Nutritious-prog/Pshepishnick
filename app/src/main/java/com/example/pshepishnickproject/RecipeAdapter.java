package com.example.pshepishnickproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView difficultyTextView;
        private ImageView photoImageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            difficultyTextView = itemView.findViewById(R.id.difficultyTextView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }

        public void bind(Recipe recipe) {
            titleTextView.setText(recipe.getTitle());
            descriptionTextView.setText(recipe.getDescription());
            difficultyTextView.setText("Difficulty: " + recipe.getDifficulty());
            // Load the photo using your preferred image loading library (e.g., Glide, Picasso)
            // Example using Glide:
            // Glide.with(itemView.getContext()).load(recipe.getPhotoUrl()).into(photoImageView);
        }
    }
}

