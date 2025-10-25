package com.example.theluxe.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.view.adapters.OrderAdapter;
import com.example.theluxe.viewmodel.OrderHistoryViewModel;

public class OrderHistoryActivity extends AppCompatActivity {

    private OrderHistoryViewModel viewModel;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(OrderHistoryViewModel.class);

        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail != null) {
            viewModel.fetchOrders(userEmail).observe(this, orders -> {
                adapter = new OrderAdapter(orders);
                recyclerView.setAdapter(adapter);
            });
        }
    }
}
