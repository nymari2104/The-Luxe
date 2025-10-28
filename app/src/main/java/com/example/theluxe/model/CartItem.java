package com.example.theluxe.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey
    @NonNull
    private String productId;
    private int quantity;
    private String userEmail;
    private String size;

    // Constructors
    public CartItem(@NonNull String productId, int quantity, String userEmail, String size) {
        this.productId = productId;
        this.quantity = quantity;
        this.userEmail = userEmail;
        this.size = size;
    }

    // Getters
    @NonNull
    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public String getSize() { return size; }

    // Setters
    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setSize(String size) { this.size = size; }
}
