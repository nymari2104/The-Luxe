package com.example.theluxe;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.theluxe.view.fragments.CartFragment;
import com.example.theluxe.view.fragments.ProductListFragment;
import com.example.theluxe.view.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.theluxe.view.fragments.WishlistFragment;

import com.google.firebase.messaging.FirebaseMessaging;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as default, select the home fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProductListFragment()).commit();
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();
                    // Log and toast
                    Log.d("FCM Token", token);
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new ProductListFragment();
                } else if (item.getItemId() == R.id.navigation_cart) {
                    selectedFragment = new CartFragment();
                } else if (item.getItemId() == R.id.navigation_wishlist) {
                    selectedFragment = new WishlistFragment();
                } else if (item.getItemId() == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };
}
