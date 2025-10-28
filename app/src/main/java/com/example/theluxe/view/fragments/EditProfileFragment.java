package com.example.theluxe.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.theluxe.R;
import com.example.theluxe.model.User;
import com.example.theluxe.viewmodel.ProfileViewModel;

import android.content.Intent;
import com.example.theluxe.view.activities.OrderHistoryActivity;

import android.widget.AutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextInputEditText editTextName, editTextAddress, editTextPhone, editTextAge, editTextHeight, editTextWeight;
    private AutoCompleteTextView autoCompleteFashionStyle, autoCompleteGender;
    private Button buttonUpdate;
    private com.google.android.material.button.MaterialButton buttonOrderHistory;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        autoCompleteFashionStyle = view.findViewById(R.id.autoCompleteFashionStyle);
        autoCompleteGender = view.findViewById(R.id.autoCompleteGender);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonOrderHistory = view.findViewById(R.id.buttonOrderHistory);

        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.fashion_styles, android.R.layout.simple_spinner_item);
        autoCompleteFashionStyle.setAdapter(styleAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.genders, android.R.layout.simple_spinner_item);
        autoCompleteGender.setAdapter(genderAdapter);

        profileViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ProfileViewModel.class);

        String userEmail = requireActivity().getIntent().getStringExtra("USER_EMAIL");
        if (userEmail != null) {
            profileViewModel.loadUser(userEmail);
            profileViewModel.user.observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    currentUser = user; // Keep a reference for updates
                    editTextName.setText(user.getName());
                    editTextAddress.setText(user.getAddress());
                    editTextPhone.setText(user.getPhone());
                    if (user.getAge() > 0) editTextAge.setText(String.valueOf(user.getAge()));
                    if (user.getHeight() > 0) editTextHeight.setText(String.valueOf(user.getHeight()));
                    if (user.getWeight() > 0) editTextWeight.setText(String.valueOf(user.getWeight()));

                    if (user.getFashionStyle() != null) {
                        autoCompleteFashionStyle.setText(user.getFashionStyle(), false);
                    }
                    if (user.getGender() != null) {
                        autoCompleteGender.setText(user.getGender(), false);
                    }
                }
            });
        }

        profileViewModel.updateStatus.observe(getViewLifecycleOwner(), isUpdated -> {
            if (isUpdated) {
                Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        buttonUpdate.setOnClickListener(v -> {
            if (currentUser != null) {
                currentUser.setName(Objects.requireNonNull(editTextName.getText()).toString());
                currentUser.setAddress(Objects.requireNonNull(editTextAddress.getText()).toString());
                currentUser.setPhone(Objects.requireNonNull(editTextPhone.getText()).toString());
                try {
                    currentUser.setAge(Integer.parseInt(Objects.requireNonNull(editTextAge.getText()).toString()));
                    currentUser.setHeight(Double.parseDouble(Objects.requireNonNull(editTextHeight.getText()).toString()));
                    currentUser.setWeight(Double.parseDouble(Objects.requireNonNull(editTextWeight.getText()).toString()));
                } catch (NumberFormatException e) {
                    // Handle case where fields might be empty
                }
                currentUser.setFashionStyle(autoCompleteFashionStyle.getText().toString());
                currentUser.setGender(autoCompleteGender.getText().toString());
                profileViewModel.updateUser(currentUser);
            }
        });

        buttonOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }
}
