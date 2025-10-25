package com.example.theluxe.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.Product;
import com.example.theluxe.model.User;
import com.example.theluxe.repository.ProductRepository;
import com.example.theluxe.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class RecommendationViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MediatorLiveData<List<Product>> recommendations = new MediatorLiveData<>();
    private LiveData<User> userSource;
    private LiveData<List<Product>> productsSource;

    public RecommendationViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        productRepository = ProductRepository.getInstance();
    }

    public LiveData<List<Product>> getRecommendations(String userEmail) {
        userSource = userRepository.getUser(userEmail);
        MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
        productRepository.getProducts(productsLiveData);
        productsSource = productsLiveData;

        recommendations.addSource(userSource, user -> filterProducts());
        recommendations.addSource(productsSource, products -> filterProducts());
        
        return recommendations;
    }

    private void filterProducts() {
        User user = userSource.getValue();
        List<Product> allProducts = productsSource.getValue();

        if (user != null && allProducts != null && user.getFashionStyle() != null) {
            String userStyle = user.getFashionStyle();
            List<Product> recommendedProducts = new ArrayList<>();
            for (Product product : allProducts) {
                if (userStyle.equals(product.getStyleTag())) {
                    recommendedProducts.add(product);
                }
            }
            recommendations.setValue(recommendedProducts);
        } else {
            recommendations.setValue(new ArrayList<>());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (userSource != null) {
            recommendations.removeSource(userSource);
        }
        if (productsSource != null) {
            recommendations.removeSource(productsSource);
        }
    }
}
