package com.example.theluxe.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static volatile ProductRepository instance;
    private List<Product> productList = new ArrayList<>();

    private ProductRepository() {
        // Add mock products
        productList.add(new Product("1", "Classic T-Shirt", "BrandA", "A classic white t-shirt.", 499000, "", "Classic", "outfit1"));
        productList.add(new Product("6", "Classic Blue Jeans", "BrandA", "Classic blue jeans.", 1299000, "", "Classic", "outfit1"));
        productList.add(new Product("7", "White Sneakers", "BrandA", "Classic white sneakers.", 999000, "", "Classic", "outfit1"));

        productList.add(new Product("2", "Leather Jacket", "BrandB", "A stylish leather jacket.", 2999000, "", "Streetwear", "outfit2"));
        productList.add(new Product("5", "Graphic Hoodie", "BrandB", "A comfortable graphic hoodie.", 1299000, "", "Streetwear", "outfit2"));
        productList.add(new Product("8", "Black Cargo Pants", "BrandB", "Stylish black cargo pants.", 1499000, "", "Streetwear", "outfit2"));

        productList.add(new Product("3", "Designer Jeans", "BrandC", "High-end designer jeans.", 1499000, "", "Chic", null));
        productList.add(new Product("4", "Linen Shirt", "BrandA", "A classic linen shirt.", 899000, "", "Classic", null));
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public void getProducts(MutableLiveData<List<Product>> products) {
        products.setValue(productList);
    }

    public Product getProductById(String productId) {
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void getOutfitRecommendations(String productId, MutableLiveData<List<Product>> recommendations) {
        Product currentProduct = getProductById(productId);
        if (currentProduct == null || currentProduct.getOutfitId() == null) {
            recommendations.setValue(new ArrayList<>());
            return;
        }

        String outfitId = currentProduct.getOutfitId();
        List<Product> recommendedProducts = new ArrayList<>();
        for (Product product : productList) {
            if (outfitId.equals(product.getOutfitId()) && !productId.equals(product.getId())) {
                recommendedProducts.add(product);
            }
        }
        recommendations.setValue(recommendedProducts);
    }
}
