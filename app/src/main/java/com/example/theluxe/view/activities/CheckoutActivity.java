package com.example.theluxe.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.theluxe.BuildConfig;
import com.example.theluxe.R;
import com.example.theluxe.service.VNPayHelper;
import com.example.theluxe.viewmodel.CheckoutViewModel;

public class CheckoutActivity extends AppCompatActivity {

    private CheckoutViewModel viewModel;
    private TextView textViewTotalAmount;
    private Button buttonPay;

    // !!! WARNING: This is for SANDBOX testing only. In a real app, get these from your server.
    private final String VNP_TMN_CODE = BuildConfig.VNPAY_TMN_CODE; // <<< REPLACE THIS
    private final String VNP_RETURN_URL = "https://theluxe/paymentredirect";

    private final ActivityResultLauncher<Intent> vnpayLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String responseCode = result.getData().getStringExtra("response_code");
                    if ("00".equals(responseCode)) {
                        Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show();
                        String userEmail = getIntent().getStringExtra("USER_EMAIL");
                        viewModel.processPaymentAndCreateOrder(userEmail);
                    } else {
                        Toast.makeText(this, "Payment failed.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Payment cancelled.", Toast.LENGTH_LONG).show();
                }
                finish();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        buttonPay = findViewById(R.id.buttonPay);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(CheckoutViewModel.class);

        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);
        viewModel.setTotalAmount(totalAmount);

        viewModel.totalAmount.observe(this, total -> {
            long amountInVND = total.longValue(); // Amount is now in VND
            textViewTotalAmount.setText(String.format("Total: %,d₫", amountInVND));
        });

        buttonPay.setOnClickListener(v -> {
            long amountInVND = viewModel.totalAmount.getValue().longValue();
            String paymentUrl = VNPayHelper.getPaymentUrl(amountInVND, "Thanh toan don hang TheLuxe", VNP_TMN_CODE, VNP_RETURN_URL);

            Intent intent = new Intent(CheckoutActivity.this, VNPayActivity.class);
            intent.putExtra(VNPayActivity.EXTRA_URL, paymentUrl);
            intent.putExtra(VNPayActivity.EXTRA_RETURN_URL, VNP_RETURN_URL);
            vnpayLauncher.launch(intent);
        });
    }
}
