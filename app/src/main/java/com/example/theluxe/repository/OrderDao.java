package com.example.theluxe.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.theluxe.model.Order;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Query("SELECT * FROM orders WHERE userEmail = :userEmail")
    LiveData<List<Order>> getOrders(String userEmail);

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    LiveData<Order> getOrderById(String orderId);
}
