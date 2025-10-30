package com.example.theluxe.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "orders")
@TypeConverters(Converters.class)
public class Order {

    @PrimaryKey
    @NonNull
    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private List<OrderDetailItem> items;
    private String status;
    private String userEmail;

    public Order(@NonNull String orderId, Date orderDate, double totalAmount, List<OrderDetailItem> items, String status, String userEmail) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.items = items;
        this.status = status;
        this.userEmail = userEmail;
    }

    public static Order fromCartItems(String orderId, Date orderDate, double totalAmount, List<CartItemWithProduct> itemsWithProducts, String status, String userEmail) {
        List<OrderDetailItem> items = new ArrayList<>();
        for (CartItemWithProduct item : itemsWithProducts) {
            items.add(new OrderDetailItem(item.getProduct().getId(), item.getProduct().getName(), item.getProduct().getBrand(), item.getProduct().getPrice(), item.getQuantity(), "N/A", item.getProduct().getImageUrl()));
        }
        return new Order(orderId, orderDate, totalAmount, items, status, userEmail);
    }

    // Getters
    @NonNull
    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderDetailItem> getItems() { return items; }
    public String getStatus() { return status; }
    public String getUserEmail() { return userEmail; }

    // Setters
    public void setOrderId(@NonNull String orderId) { this.orderId = orderId; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setItems(List<OrderDetailItem> items) { this.items = items; }
    public void setStatus(String status) { this.status = status; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
