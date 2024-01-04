package com.example.pshepishnickproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userProfile extends Fragment {

    private TextView welcomeTextView, emailTextView;
    private Button logoutButton;

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

        return view;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent loginIntent = new Intent(requireContext(), Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);

        requireActivity().finish();
    }
}
