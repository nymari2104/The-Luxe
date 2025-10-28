package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.theluxe.model.CartItemWithProduct;
import com.example.theluxe.model.Product;
import com.example.theluxe.repository.CartRepository;
import java.util.List;

import androidx.lifecycle.MediatorLiveData;

public class CartViewModel extends AndroidViewModel {

    private final CartRepository cartRepository;
    private LiveData<List<CartItemWithProduct>> cartItems;
    private final MediatorLiveData<Double> totalAmount = new MediatorLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = CartRepository.getInstance(application);
    }

    public void init(String userEmail) {
        if (cartItems == null) {
            cartItems = cartRepository.getCartWithProducts(userEmail);
            totalAmount.addSource(cartItems, items -> {
                double sum = 0;
                if (items != null) {
                    for (CartItemWithProduct item : items) {
                        sum += item.getProduct().getPrice() * item.getQuantity();
                    }
                }
                totalAmount.setValue(sum);
            });
        }
    }

    public LiveData<List<CartItemWithProduct>> getCartWithProducts() {
        return cartItems;
    }

    public LiveData<Double> getTotalAmount() {
        return totalAmount;
    }

    public void updateQuantity(String userEmail, String productId, int quantity) {
        cartRepository.updateQuantity(userEmail, productId, quantity);
    }

    public void deleteItem(String userEmail, String productId) {
        cartRepository.deleteItem(userEmail, productId);
    }
}
