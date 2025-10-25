package com.example.theluxe.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "wishlist",
        primaryKeys = {"userEmail", "productId"},
        foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "email",
                                  childColumns = "userEmail",
                                  onDelete = ForeignKey.CASCADE),
        indices = {@Index("userEmail")})
public class WishlistItem {
    @NonNull
    public String userEmail;

    @NonNull
    public String productId;

    public WishlistItem(@NonNull String userEmail, @NonNull String productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }
}
