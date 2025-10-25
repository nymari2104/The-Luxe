package com.example.theluxe.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String email;

    private String password;
    private String name;
    private String address;
    private String phone;
    private int age;
    private double height;
    private double weight;
    private String fashionStyle;

    // A no-arg constructor is needed for Room
    public User() {
        this.email = ""; // Initialize non-null field
    }
    
    // You might want to keep a constructor for convenience
    public User(@NonNull String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    @NonNull
    public String getEmail() { return email; }
    public void setEmail(@NonNull String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getFashionStyle() { return fashionStyle; }
    public void setFashionStyle(String fashionStyle) { this.fashionStyle = fashionStyle; }
}
