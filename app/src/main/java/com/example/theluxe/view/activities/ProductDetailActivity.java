package com.example.theluxe.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.theluxe.R;
import com.example.theluxe.viewmodel.ProductDetailViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.view.adapters.ProductAdapter;
import java.util.ArrayList;

import com.example.theluxe.viewmodel.WishlistViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductDetailViewModel viewModel;
    private WishlistViewModel wishlistViewModel; // Added
    private ImageView imageViewProductDetail;
    private TextView textViewProductNameDetail, textViewProductBrandDetail, textViewProductDescriptionDetail, textViewProductPriceDetail;
    private Button buttonAddToCart, buttonAddToWishlist;
    private RecyclerView recyclerViewOutfit;
    private ProductAdapter outfitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageViewProductDetail = findViewById(R.id.imageViewProductDetail);
        textViewProductNameDetail = findViewById(R.id.textViewProductNameDetail);
        textViewProductBrandDetail = findViewById(R.id.textViewProductBrandDetail);
        textViewProductDescriptionDetail = findViewById(R.id.textViewProductDescriptionDetail);
        textViewProductPriceDetail = findViewById(R.id.textViewProductPriceDetail);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);
        buttonAddToWishlist = findViewById(R.id.buttonAddToWishlist);
        recyclerViewOutfit = findViewById(R.id.recyclerViewOutfit);

        wishlistViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WishlistViewModel.class);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProductDetailViewModel.class);

        recyclerViewOutfit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        
        String productId = getIntent().getStringExtra("PRODUCT_ID");
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        
        if (userEmail != null) {
            wishlistViewModel.init(userEmail);
        }
        
        viewModel.getProductById(productId);

        viewModel.product.observe(this, product -> {
            if (product != null) {
                // Load image using Glide or Picasso in a real app
                textViewProductNameDetail.setText(product.getName());
                textViewProductBrandDetail.setText(product.getBrand());
                textViewProductDescriptionDetail.setText(product.getDescription());
                textViewProductPriceDetail.setText(String.format("%,.0f₫", product.getPrice()));
            }
        });

        // Observe wishlist and recommendations
        wishlistViewModel.getWishlist().observe(this, wishlist -> {
            if (outfitAdapter != null) {
                outfitAdapter.setWishlist(wishlist);
            }
        });

        viewModel.outfitRecommendations.observe(this, products -> {
            outfitAdapter = new ProductAdapter(products, wishlistViewModel, new ArrayList<>(), userEmail);
            recyclerViewOutfit.setAdapter(outfitAdapter);
        });

        buttonAddToCart.setOnClickListener(v -> {
            viewModel.addToCart(userEmail);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        buttonAddToWishlist.setOnClickListener(v -> {
            viewModel.addToWishlist(userEmail);
            Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();
        });
    }
}
