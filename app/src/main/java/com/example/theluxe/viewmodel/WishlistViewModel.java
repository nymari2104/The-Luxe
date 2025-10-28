package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.theluxe.model.Product;
import com.example.theluxe.repository.WishlistRepository;
import java.util.List;

public class WishlistViewModel extends AndroidViewModel {

    private final WishlistRepository wishlistRepository;
    private LiveData<List<String>> wishlistedIds;
    private String currentUserEmail;

    public WishlistViewModel(@NonNull Application application) {
        super(application);
        wishlistRepository = WishlistRepository.getInstance(application);
    }

    public void init(String userEmail) {
        if (this.currentUserEmail != null && this.currentUserEmail.equals(userEmail)) {
            return; // Already initialized for this user
        }
        this.currentUserEmail = userEmail;
        wishlistedIds = wishlistRepository.getWishlistedIds(userEmail);
    }

    public LiveData<List<Product>> getWishlist(String userEmail) {
        return wishlistRepository.getWishlistForUser(userEmail);
    }

    public LiveData<List<String>> getWishlistedIds() {
        return wishlistedIds;
    }

    public void addToWishlist(Product product) {
        if (currentUserEmail != null) {
            wishlistRepository.addToWishlist(currentUserEmail, product.getId());
        }
    }

    public void removeFromWishlist(Product product) {
        if (currentUserEmail != null) {
            wishlistRepository.removeFromWishlist(currentUserEmail, product.getId());
        }
    }
}
