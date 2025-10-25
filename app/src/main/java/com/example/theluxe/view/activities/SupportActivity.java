package com.example.theluxe.view.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theluxe.R;
import com.google.android.material.card.MaterialCardView;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        MaterialCardView cardFaq = findViewById(R.id.cardFaq);
        MaterialCardView cardChat = findViewById(R.id.cardChat);

        cardFaq.setOnClickListener(v -> {
            Intent intent = new Intent(this, TextDisplayActivity.class);
            intent.putExtra("TITLE", "FAQ");
            intent.putExtra("CONTENT", getString(R.string.faq_content));
            startActivity(intent);
        });

        cardChat.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
    }
}
