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

public class ProductDetailViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    public MutableLiveData<Product> product = new MutableLiveData<>();
    public MutableLiveData<List<Product>> outfitRecommendations = new MutableLiveData<>();

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        productRepository = ProductRepository.getInstance();
        cartRepository = CartRepository.getInstance(application);
        wishlistRepository = WishlistRepository.getInstance(application);
    }

    public void getProductById(String productId) {
        product.setValue(productRepository.getProductById(productId));
        productRepository.getOutfitRecommendations(productId, outfitRecommendations);
    }

    public void addToCart(String userEmail) {
        if (product.getValue() != null && userEmail != null) {
            cartRepository.addToCart(userEmail, product.getValue());
        }
    }

    public void addToWishlist(String userEmail) {
        if (product.getValue() != null && userEmail != null) {
            wishlistRepository.addToWishlist(userEmail, product.getValue().getId());
        }
    }
}
