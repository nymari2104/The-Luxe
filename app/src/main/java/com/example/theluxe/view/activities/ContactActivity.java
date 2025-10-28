package com.example.theluxe.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theluxe.R;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ContactActivity extends AppCompatActivity {
    private Button buttonViewMap;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        buttonViewMap = findViewById(R.id.buttonViewMap);
        buttonViewMap.setOnClickListener(v -> {
            try {
                String address = "Vinhome granpark S107, Nguyễn Xiển, Tp Thủ Đức";
                String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + encodedAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + encodedAddress;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(webIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
