package com.example.theluxe.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.example.theluxe.MainActivity;
import com.example.theluxe.R;
import com.example.theluxe.viewmodel.LoginViewModel;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegisterLink = findViewById(R.id.textViewRegisterLink);

        loginViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(LoginViewModel.class);

        loginViewModel.loggedInUser.observe(this, user -> {
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USER_EMAIL", user.getEmail());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            loginViewModel.login(email, password);
        });

        textViewRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
