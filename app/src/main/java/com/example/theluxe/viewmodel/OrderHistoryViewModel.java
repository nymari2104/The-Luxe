package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.theluxe.model.Order;
import com.example.theluxe.repository.OrderRepository;
import java.util.List;

public class OrderHistoryViewModel extends AndroidViewModel {

    private final OrderRepository orderRepository;

    public OrderHistoryViewModel(@NonNull Application application) {
        super(application);
        orderRepository = OrderRepository.getInstance(application);
    }

    public LiveData<List<Order>> fetchOrders(String userEmail) {
        return orderRepository.getOrdersForUser(userEmail);
    }
}
