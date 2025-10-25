package com.example.theluxe.view.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theluxe.R;
import com.google.android.material.card.MaterialCardView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialCardView cardPrivacyPolicy = findViewById(R.id.cardPrivacyPolicy);
        MaterialCardView cardTermsOfService = findViewById(R.id.cardTermsOfService);
        MaterialCardView cardCopyright = findViewById(R.id.cardCopyright);

        cardPrivacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(this, TextDisplayActivity.class);
            intent.putExtra("TITLE", "Privacy Policy");
            intent.putExtra("CONTENT", getString(R.string.privacy_policy_content));
            startActivity(intent);
        });

        cardTermsOfService.setOnClickListener(v -> {
            Intent intent = new Intent(this, TextDisplayActivity.class);
            intent.putExtra("TITLE", "Terms of Service");
            intent.putExtra("CONTENT", getString(R.string.terms_of_service_content));
            startActivity(intent);
        });

        cardCopyright.setOnClickListener(v -> {
            Intent intent = new Intent(this, TextDisplayActivity.class);
            intent.putExtra("TITLE", "Copyright Policy");
            intent.putExtra("CONTENT", getString(R.string.copyright_policy_content));
            startActivity(intent);
        });
    }
}
