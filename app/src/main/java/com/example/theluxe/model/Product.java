package com.example.theluxe.model;

public class Product {
    private String id;
    private String name;
    private String brand;
    private String description;
    private double price;
    private String imageUrl;
    private String styleTag;
    private String outfitId;

    public Product(String id, String name, String brand, String description, double price, String imageUrl, String styleTag, String outfitId) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.styleTag = styleTag;
        this.outfitId = outfitId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getStyleTag() { return styleTag; }
    public String getOutfitId() { return outfitId; }
}
