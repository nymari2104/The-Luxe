package com.example.theluxe.view.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.view.adapters.OrderDetailAdapter;
import com.example.theluxe.model.CartItemWithProduct;
import com.example.theluxe.repository.ProductRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import com.example.theluxe.viewmodel.OrderHistoryViewModel;
import java.util.stream.Collectors;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderHistoryViewModel viewModel;
    private TextView textViewOrderId, textViewOrderDate, textViewOrderStatus, textViewOrderTotal;
    private RecyclerView recyclerViewItems;
    private OrderDetailAdapter adapter;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        textViewOrderId = findViewById(R.id.textViewDetailOrderId);
        textViewOrderDate = findViewById(R.id.textViewDetailOrderDate);
        textViewOrderStatus = findViewById(R.id.textViewDetailOrderStatus);
        textViewOrderTotal = findViewById(R.id.textViewDetailOrderTotal);
        recyclerViewItems = findViewById(R.id.recyclerViewOrderDetailItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderDetailAdapter();
        recyclerViewItems.setAdapter(adapter);

        productRepository = ProductRepository.getInstance(); // Get repository instance

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(OrderHistoryViewModel.class);

        String orderId = getIntent().getStringExtra("ORDER_ID");
        if (orderId != null) {
            viewModel.getOrderById(orderId).observe(this, order -> {
                if (order != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    textViewOrderId.setText("Order #" + order.getOrderId().substring(0, 8));
                    textViewOrderDate.setText("Date: " + sdf.format(order.getOrderDate()));
                    textViewOrderStatus.setText("Status: " + order.getStatus());
                    textViewOrderTotal.setText(String.format("Total: %,.0f₫", order.getTotalAmount()));

                    List<CartItemWithProduct> itemsWithProducts = order.getItems().stream()
                            .map(cartItem -> new CartItemWithProduct(productRepository.getProductById(cartItem.getProductId()), cartItem.getQuantity()))
                            .collect(Collectors.toList());
                    adapter.submitList(itemsWithProducts);
                }
            });
        }
    }
}
