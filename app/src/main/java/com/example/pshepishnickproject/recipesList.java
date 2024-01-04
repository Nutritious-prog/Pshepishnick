package com.example.pshepishnickproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class recipesList extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.searchView);


        // Fetch recipes from Firestore and set up the adapter
        fetchRecipesFromFirestore();

        return view;
    }

    private void fetchRecipesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Recipe> recipes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Recipe recipe = document.toObject(Recipe.class);
                        recipes.add(recipe);
                    }

                    // Set up the adapter with the fetched recipes
                    recipeAdapter = new RecipeAdapter(recipes);
                    recyclerView.setAdapter(recipeAdapter);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            // Handle the query when the user submits
                            filterRecipes(query);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            // Handle the query text as it changes
                            filterRecipes(newText);
                            return true;
                        }
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle the failure
                    Log.e("RecipeListFragment", "Error fetching recipes", e);
                });
    }

    private void filterRecipes(String query) {
        // Update the adapter with the filtered data
        recipeAdapter.filter(query);
    }
}
