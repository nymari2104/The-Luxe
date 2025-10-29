package com.example.theluxe.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.theluxe.model.Product;
import com.example.theluxe.repository.CartRepository;
import com.example.theluxe.repository.ProductRepository;

import com.example.theluxe.repository.WishlistRepository;

import java.util.List;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.theluxe.model.User;
import com.example.theluxe.repository.UserRepository;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MediatorLiveData;

public class ProductDetailViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    public MutableLiveData<Product> product = new MutableLiveData<>();
    public MutableLiveData<List<Product>> outfitRecommendations = new MutableLiveData<>();

    private final UserRepository userRepository;
    public LiveData<User> user;
    
    private LiveData<List<Product>> wishlist;
    public final MediatorLiveData<Boolean> isWishlisted = new MediatorLiveData<>();

    private boolean initialized = false;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        productRepository = ProductRepository.getInstance();
        cartRepository = CartRepository.getInstance(application);
        wishlistRepository = WishlistRepository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
    }

    public void init(String userEmail, String productId) {
        if (initialized) return; // tránh addSource lần nữa
        initialized = true;
        user = userRepository.getUser(userEmail);
        getProductById(productId);
        wishlist = wishlistRepository.getWishlistForUser(userEmail);

        isWishlisted.addSource(product, p -> updateIsWishlisted());
        isWishlisted.addSource(wishlist, w -> updateIsWishlisted());
    }

    private void updateIsWishlisted() {
        Product currentProduct = product.getValue();
        List<Product> currentWishlist = wishlist.getValue();
        if (currentProduct != null && currentWishlist != null) {
            boolean isProductInWishlist = currentWishlist.stream().anyMatch(p -> p.getId().equals(currentProduct.getId()));
            isWishlisted.setValue(isProductInWishlist);
        } else {
            isWishlisted.setValue(false);
        }
    }

    public void getProductById(String productId) {
        product.setValue(productRepository.getProductById(productId));
        productRepository.getOutfitRecommendations(productId, outfitRecommendations);
    }

    public void addToCart(String userEmail, String size, int quantity) {
        if (product.getValue() != null && userEmail != null) {
            cartRepository.addToCart(userEmail, product.getValue(), size, quantity);
        }
    }

    public void addToWishlist(String userEmail) {
        if (product.getValue() != null && userEmail != null) {
            wishlistRepository.addToWishlist(userEmail, product.getValue().getId());
        }
    }

    public LiveData<List<Product>> getWishlist(String userEmail) {
        return wishlistRepository.getWishlistForUser(userEmail);
    }

    public void toggleWishlist() {
        if (product.getValue() != null && user.getValue() != null) {
            wishlistRepository.toggleWishlist(user.getValue().getEmail(), product.getValue().getId());
        }
    }
}
