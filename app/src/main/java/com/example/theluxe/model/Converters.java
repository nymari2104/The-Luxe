package com.example.theluxe.model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static String fromCartItemList(List<CartItem> cartItems) {
        if (cartItems == null) {
            return null;
        }
        Type listType = new TypeToken<List<CartItem>>() {}.getType();
        return gson.toJson(cartItems, listType);
    }

    @TypeConverter
    public static List<CartItem> toCartItemList(String cartItemsString) {
        if (cartItemsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(cartItemsString, listType);
    }
}
