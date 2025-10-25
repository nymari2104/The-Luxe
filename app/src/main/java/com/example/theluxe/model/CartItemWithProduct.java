package com.example.theluxe.model;

public class CartItemWithProduct {
    private final Product product;
    private final int quantity;

    public CartItemWithProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
