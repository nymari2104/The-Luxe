package com.example.theluxe.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theluxe.R;

public class VNPayActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_RETURN_URL = "extra_return_url";

    private WebView webView;
    private ProgressBar progressBar;
    private String returnUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vnpay);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra(EXTRA_URL);
        returnUrl = getIntent().getStringExtra(EXTRA_RETURN_URL);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                if (url.startsWith(returnUrl)) {
                    Uri uri = Uri.parse(url);
                    String responseCode = uri.getQueryParameter("vnp_ResponseCode");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("response_code", responseCode);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
        webView.loadUrl(url);
    }
}
