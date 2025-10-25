package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.Order;
import com.example.theluxe.repository.CartRepository;
import com.example.theluxe.repository.OrderRepository;
import java.util.Date;
import java.util.UUID;

public class CheckoutViewModel extends AndroidViewModel {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
    public MutableLiveData<Boolean> paymentStatus = new MutableLiveData<>();

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        cartRepository = CartRepository.getInstance(application);
        orderRepository = OrderRepository.getInstance(application);
    }

    public void setTotalAmount(double total) {
        totalAmount.setValue(total);
    }

    public void processPaymentAndCreateOrder(String userEmail) {
        // 1. Simulate payment processing
        paymentStatus.setValue(true); // Assume payment is successful

        // 2. Create and save the order
        if (Boolean.TRUE.equals(paymentStatus.getValue())) {
            String orderId = UUID.randomUUID().toString();
            cartRepository.getCartWithProducts(userEmail).observeForever(cartItems -> {
                if (cartItems != null) {
                    Order newOrder = Order.fromCartItems(
                            orderId,
                            new Date(),
                            totalAmount.getValue(),
                            cartItems,
                            "Processing",
                            userEmail
                    );
                    orderRepository.addOrder(newOrder);
                    cartRepository.clearCart(userEmail);
                }
            });
        }
    }
}
