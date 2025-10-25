package com.example.theluxe.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.theluxe.model.Order;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {

    private static volatile OrderRepository instance;
    private final OrderDao orderDao;
    private final ExecutorService executorService;

    private OrderRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        orderDao = db.orderDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static OrderRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository(application);
                }
            }
        }
        return instance;
    }

    public void addOrder(Order order) {
        executorService.execute(() -> {
            orderDao.insert(order);
        });
    }

    public LiveData<List<Order>> getOrdersForUser(String userEmail) {
        return orderDao.getOrders(userEmail);
    }
}
