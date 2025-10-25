package com.example.theluxe.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.theluxe.model.Product;
import com.example.theluxe.repository.ProductRepository;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private ProductRepository productRepository;
    public MutableLiveData<List<Product>> products = new MutableLiveData<>();

    public ProductViewModel() {
        productRepository = ProductRepository.getInstance();
    }

    public void getProducts() {
        productRepository.getProducts(products);
    }
}
