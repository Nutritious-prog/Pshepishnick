package com.example.pshepishnickproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addRecipe extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText etTitle, etDescription, etDuration, etDifficulty;
    private ImageView ivSelectedPhoto;
    private Uri selectedImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etDuration = view.findViewById(R.id.etDuration);
        etDifficulty = view.findViewById(R.id.etDifficulty);
        ivSelectedPhoto = view.findViewById(R.id.ivSelectedPhoto);

        Button btnSelectPhoto = view.findViewById(R.id.btnSelectPhoto);
        Button btnSaveRecipe = view.findViewById(R.id.btnSaveRecipe);

        btnSelectPhoto.setOnClickListener(v -> openGallery());
        btnSaveRecipe.setOnClickListener(v -> saveRecipe());

        return view;
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent);
    }
    private void saveRecipe() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String difficultyStr = etDifficulty.getText().toString().trim();

        // Validation: Ensure that required fields are not empty
        if (title.isEmpty() || description.isEmpty() || durationStr.isEmpty() || difficultyStr.isEmpty()) {
            // Handle empty fields (show a Toast, Snackbar, or other feedback)
            return;
        }

        // Convert duration and difficulty to appropriate types
        int duration = Integer.parseInt(durationStr);
        int difficulty = Integer.parseInt(difficultyStr);

        // Now, you have all the necessary data to create a Recipe object and save it to Firestore
        Recipe recipe = new Recipe(title, description, duration, difficulty, "dummy");

        System.out.println(recipe);


        // You can now save the recipe to Firestore and upload the image to Firebase Storage
        saveRecipeToFirestore(recipe);
    }


    private void saveRecipeToFirestore(Recipe recipe) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("title", recipe.getTitle());
        recipeMap.put("description", recipe.getDescription());
        recipeMap.put("difficulty", recipe.getDifficulty());
        recipeMap.put("preparationDuration", recipe.getPreparationDuration());
        recipeMap.put("photoUrl", recipe.getPhotoUrl());
        recipeMap.put("ownerID", "narazieBrakID");

// Add a new document with a generated ID
        db.collection("recipes")
                .add(recipeMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}
