package com.example.theluxe.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.example.theluxe.model.CartItem;
import com.example.theluxe.model.CartItemWithProduct;
import com.example.theluxe.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepository {

    private static volatile CartRepository instance;
    private final CartDao cartDao;
    private final ProductRepository productRepository;
    private final ExecutorService executorService;

    private CartRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cartDao = db.cartDao();
        productRepository = ProductRepository.getInstance();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static CartRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (CartRepository.class) {
                if (instance == null) {
                    instance = new CartRepository(application);
                }
            }
        }
        return instance;
    }

    public void addToCart(String userEmail, Product product, String size, int quantity) {
        executorService.execute(() -> {
            CartItem existingItem = cartDao.getCartItem(userEmail, product.getId());
            if (existingItem != null) {
                // Item exists, update quantity
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartDao.insert(existingItem);
            } else {
                // New item, add to cart
                CartItem newItem = new CartItem(product.getId(), quantity, userEmail, size);
                cartDao.insert(newItem);
            }
        });
    }

    public void updateQuantity(String userEmail, String productId, int quantity) {
        executorService.execute(() -> cartDao.updateQuantity(userEmail, productId, quantity));
    }

    public void deleteItem(String userEmail, String productId) {
        executorService.execute(() -> cartDao.deleteItem(userEmail, productId));
    }

    public LiveData<List<CartItemWithProduct>> getCartWithProducts(String userEmail) {
        return Transformations.map(cartDao.getCartItems(userEmail), cartItems -> {
            List<CartItemWithProduct> result = new ArrayList<>();
            for (CartItem item : cartItems) {
                Product product = productRepository.getProductById(item.getProductId());
                if (product != null) {
                    result.add(new CartItemWithProduct(product, item.getQuantity(), item.getSize()));
                }
            }
            return result;
        });
    }

    public void clearCart(String userEmail) {
        executorService.execute(() -> {
            cartDao.deleteCart(userEmail);
        });
    }
}
