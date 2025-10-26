package com.example.theluxe.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.theluxe.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static volatile ProductRepository instance;
    private List<Product> productList = new ArrayList<>();

    private ProductRepository() {
        // Add mock products with real images from Unsplash
        
        // Classic Outfit 1
        productList.add(new Product("1", "Classic White T-Shirt", "Essentials", 
            "Premium cotton t-shirt with a timeless design. Perfect for everyday wear.", 
            499000, 
            "drawable://classic_white_t_shirt", 
            "Classic", "outfit1"));
            
        productList.add(new Product("6", "Classic Blue Jeans", "Denim Co", 
            "Classic fit jeans made from premium denim. Comfortable and durable.", 
            1299000, 
            "drawable://classic_blue_jeans", 
            "Classic", "outfit1"));
            
        productList.add(new Product("7", "White Leather Sneakers", "Urban Steps", 
            "Clean white sneakers with premium leather finish. Minimalist design.", 
            999000, 
            "drawable://sneaker", 
            "Classic", "outfit1"));

        // Streetwear Outfit 2
        productList.add(new Product("2", "Black Leather Jacket", "Street Elite", 
            "Genuine leather jacket with modern cut. Perfect for street style.", 
            2999000, 
            "drawable://black_leather_jacket", 
            "Streetwear", "outfit2"));
            
        productList.add(new Product("5", "Graphic Hoodie", "Urban Wear", 
            "Oversized hoodie with unique graphic design. 100% cotton fleece.", 
            1299000, 
            "drawable://graphic_hoodie", 
            "Streetwear", "outfit2"));
            
        productList.add(new Product("8", "Black Cargo Pants", "Tactical Style", 
            "Multi-pocket cargo pants with modern fit. Durable and stylish.", 
            1499000, 
            "drawable://black_pants", 
            "Streetwear", "outfit2"));

        // Chic Outfit 3
        productList.add(new Product("3", "Designer Slim Jeans", "Luxury Denim", 
            "High-end designer jeans with perfect fit. Premium Italian denim.", 
            1499000, 
            "drawable://slim_jeans", 
            "Chic", "outfit3"));
            
        productList.add(new Product("4", "Linen Summer Shirt", "Coastal Wear", 
            "Breathable linen shirt perfect for summer. Relaxed fit.", 
            899000, 
            "drawable://summer_shirt", 
            "Chic", "outfit3"));
            
        productList.add(new Product("9", "Minimalist Watch", "Timepiece Co", 
            "Swiss-made minimalist watch with leather strap. Elegant design.", 
            3499000, 
            "drawable://mini_watch", 
            "Chic", "outfit3"));
            
        productList.add(new Product("10", "Leather Crossbody Bag", "Luxury Goods", 
            "Handcrafted Italian leather bag. Perfect for daily essentials.", 
            2299000, 
            "drawable://bag", 
            "Chic", "outfit3"));
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

    public void searchProducts(String query, MutableLiveData<List<Product>> searchResults) {
        List<Product> filteredProducts = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            searchResults.setValue(productList);
            return;
        }
        String lowerCaseQuery = query.toLowerCase();
        for (Product product : productList) {
            if (product.getName().toLowerCase().startsWith(lowerCaseQuery)) {
                filteredProducts.add(product);
            }
        }
        searchResults.setValue(filteredProducts);
    }
}
