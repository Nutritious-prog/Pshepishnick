package com.example.pshepishnickproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addRecipe extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText etTitle, etDescription, etDuration, etDifficulty;
    private ImageView ivSelectedPhoto;
    private String selectedImageUri;

    TextView tvSelectedPhotoName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etDuration = view.findViewById(R.id.etDuration);
        etDifficulty = view.findViewById(R.id.etDifficulty);
        ivSelectedPhoto = view.findViewById(R.id.ivSelectedPhoto);
        tvSelectedPhotoName = view.findViewById(R.id.tvSelectedPhotoName);

        Button btnSelectPhoto = view.findViewById(R.id.btnSelectPhoto);
        Button btnSaveRecipe = view.findViewById(R.id.btnSaveRecipe);

        btnSelectPhoto.setOnClickListener(v -> openGallery());
        btnSaveRecipe.setOnClickListener(v -> saveRecipe());

        return view;
    }

    // Declare a variable for the ActivityResultLauncher
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri selectedImageUri = data.getData();
                                // Retrieve the file name from the content resolver
                                String photoName = getFileName(selectedImageUri);

                                // Update the TextView with the selected photo name
                                tvSelectedPhotoName.setText("Selected Photo: " + photoName);
                                tvSelectedPhotoName.setVisibility(View.VISIBLE);

                                uploadImageToFirebaseStorage(selectedImageUri);
                            }
                        }
                    });

    // Function to get the file name from the content resolver
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    // Inside your openGallery() method or wherever you trigger the gallery intent
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            String path = "images/" + UUID.randomUUID().toString();
            StorageReference imagesRef = storageRef.child(path);
            System.out.println("ImgRef" + imagesRef);
            System.out.println("path" + path);
            imagesRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            selectedImageUri = downloadUrl;
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e("AddRecipeFragment", "Error uploading image to Firebase Storage", e);
                    });
        }
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
        System.out.println(selectedImageUri);
        Recipe recipe = new Recipe(title, description, duration, difficulty, selectedImageUri);

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
