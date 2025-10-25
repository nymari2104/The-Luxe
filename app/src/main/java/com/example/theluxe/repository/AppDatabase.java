package com.example.theluxe.repository;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.theluxe.model.CartItem;
import com.example.theluxe.model.Order;
import com.example.theluxe.model.User;

import com.example.theluxe.model.WishlistItem;

@Database(entities = {User.class, WishlistItem.class, CartItem.class, Order.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract WishlistDao wishlistDao();
    public abstract CartDao cartDao();
    public abstract OrderDao orderDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "theluxe_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
