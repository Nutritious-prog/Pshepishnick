package com.example.pshepishnickproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class userProfile extends Fragment {

    private TextView welcomeTextView, emailTextView;
    private Button logoutButton;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Retrieve the current user's email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            welcomeTextView.setText("Welcome to Pshepisnick");
            emailTextView.setText(userEmail);
        }

        // Set onClickListener for logout button
        logoutButton.setOnClickListener(v -> logout());

        recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch recipes from Firestore and set up the adapter
        fetchRecipesFromFirestore();

        return view;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent loginIntent = new Intent(requireContext(), Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);

        requireActivity().finish();
    }

    private void fetchRecipesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            db.collection("recipes")
                    .whereEqualTo("ownerID", currentUserId)
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

                        // Add any other setup code you need
                    })
                    .addOnFailureListener(e -> {
                        // Handle the failure
                        Log.e("RecipeListFragment", "Error fetching recipes", e);
                    });
        }
    }
}
