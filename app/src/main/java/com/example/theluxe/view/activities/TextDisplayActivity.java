package com.example.theluxe.view.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theluxe.R;

public class TextDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_display);

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewContent = findViewById(R.id.textViewContent);

        String title = getIntent().getStringExtra("TITLE");
        String content = getIntent().getStringExtra("CONTENT");

        textViewTitle.setText(title);
        textViewContent.setText(content);
    }
}
