package com.example.theluxe.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.theluxe.model.CartItem;
import java.util.List;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem... cartItem);

    @Query("SELECT * from cart_items WHERE userEmail = :userEmail AND productId = :productId")
    CartItem getCartItem(String userEmail, String productId);

    @Query("UPDATE cart_items SET quantity = :quantity WHERE userEmail = :userEmail AND productId = :productId")
    void updateQuantity(String userEmail, String productId, int quantity);

    @Query("DELETE FROM cart_items WHERE userEmail = :userEmail AND productId = :productId")
    void deleteItem(String userEmail, String productId);
    
    @Query("SELECT * FROM cart_items WHERE userEmail = :userEmail")
    LiveData<List<CartItem>> getCartItems(String userEmail);

    @Query("DELETE FROM cart_items WHERE userEmail = :userEmail")
    void deleteCart(String userEmail);
}
