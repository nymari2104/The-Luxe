package com.example.theluxe.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.theluxe.R;
import com.example.theluxe.view.activities.ContactActivity;
import com.example.theluxe.view.activities.OrderHistoryActivity;
import com.example.theluxe.view.activities.SettingsActivity;
import com.example.theluxe.view.activities.SupportActivity;
import com.google.android.material.card.MaterialCardView;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialCardView cardEditProfile = view.findViewById(R.id.cardEditProfile);
        MaterialCardView cardOrderHistory = view.findViewById(R.id.cardOrderHistory);
        MaterialCardView cardSettings = view.findViewById(R.id.cardSettings);
        MaterialCardView cardSupport = view.findViewById(R.id.cardSupport);
        MaterialCardView cardContact = view.findViewById(R.id.cardContact);

        cardEditProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new EditProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        cardOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            intent.putExtra("USER_EMAIL", requireActivity().getIntent().getStringExtra("USER_EMAIL"));
            startActivity(intent);
        });

        cardSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        cardSupport.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SupportActivity.class);
            startActivity(intent);
        });

        cardContact.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactActivity.class);
            startActivity(intent);
        });
    }
}
