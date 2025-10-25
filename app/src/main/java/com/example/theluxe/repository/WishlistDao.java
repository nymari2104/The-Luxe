package com.example.theluxe.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.theluxe.model.WishlistItem;
import java.util.List;

@Dao
public interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WishlistItem item);

    @Delete
    void delete(WishlistItem item);

    @Query("SELECT productId FROM wishlist WHERE userEmail = :userEmail")
    LiveData<List<String>> getWishlistedIdsForUser(String userEmail);
}
