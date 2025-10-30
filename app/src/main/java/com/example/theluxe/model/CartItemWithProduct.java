package com.example.theluxe.model;

public class CartItemWithProduct {
    private final Product product;
    private final int quantity;
    private final String size;

    public CartItemWithProduct(Product product, int quantity, String size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }
}
