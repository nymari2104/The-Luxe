package com.example.theluxe.model;

public class OrderDetailItem {
    private String productId;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String size;

    // No-arg constructor for Gson
    public OrderDetailItem() {}

    private String imageUrl;

    public OrderDetailItem(String productId, String name, String brand, double price, int quantity, String size, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getSize() { return size; }
    public String getImageUrl() { return imageUrl; }
}
