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
    public static String fromOrderDetailItemList(List<OrderDetailItem> orderDetailItems) {
        if (orderDetailItems == null) {
            return null;
        }
        Type listType = new TypeToken<List<OrderDetailItem>>() {}.getType();
        return gson.toJson(orderDetailItems, listType);
    }

    @TypeConverter
    public static List<OrderDetailItem> toOrderDetailItemList(String orderDetailItemsString) {
        if (orderDetailItemsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<OrderDetailItem>>() {}.getType();
        return gson.fromJson(orderDetailItemsString, listType);
    }
}
