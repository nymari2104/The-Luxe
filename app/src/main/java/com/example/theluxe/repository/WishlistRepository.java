package com.example.theluxe.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.example.theluxe.model.Product;
import com.example.theluxe.model.WishlistItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WishlistRepository {

    private static volatile WishlistRepository instance;
    private final WishlistDao wishlistDao;
    private final ProductRepository productRepository;
    private final ExecutorService executorService;

    private WishlistRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        wishlistDao = db.wishlistDao();
        productRepository = ProductRepository.getInstance();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static WishlistRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (WishlistRepository.class) {
                if (instance == null) {
                    instance = new WishlistRepository(application);
                }
            }
        }
        return instance;
    }

    public LiveData<List<Product>> getWishlistForUser(String userEmail) {
        return Transformations.map(wishlistDao.getWishlistedIdsForUser(userEmail), ids -> {
            List<Product> wishlistProducts = new ArrayList<>();
            // This is inefficient but works for the demo.
            // A real app would use a database JOIN.
            for (String productId : ids) {
                Product product = productRepository.getProductById(productId);
                if (product != null) {
                    wishlistProducts.add(product);
                }
            }
            return wishlistProducts;
        });
    }

    public void addToWishlist(String userEmail, String productId) {
        executorService.execute(() -> {
            wishlistDao.insert(new WishlistItem(userEmail, productId));
        });
    }

    public void removeFromWishlist(String userEmail, String productId) {
        executorService.execute(() -> {
            wishlistDao.delete(new WishlistItem(userEmail, productId));
        });
    }
    
    public LiveData<List<String>> getWishlistedIds(String userEmail) {
        return wishlistDao.getWishlistedIdsForUser(userEmail);
    }
}
