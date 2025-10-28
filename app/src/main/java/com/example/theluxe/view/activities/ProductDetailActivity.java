package com.example.theluxe.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    private android.view.View outfitSection;

    @SuppressLint("DefaultLocale")
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
        outfitSection = findViewById(R.id.outfitSection);

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
                // Load product detail image with Glide (supports both local and online)
                loadProductImage(product.getImageUrl(), imageViewProductDetail);

                textViewProductNameDetail.setText(product.getName());
                textViewProductBrandDetail.setText(product.getBrand());
                textViewProductDescriptionDetail.setText(product.getDescription());
                textViewProductPriceDetail.setText(String.format("%,.0f₫", product.getPrice()));
            }
        });

        // Observe outfit recommendations and wishlist together
        wishlistViewModel.getWishlist().observe(this, wishlist -> {
            viewModel.outfitRecommendations.observe(this, products -> {
                if (products != null && !products.isEmpty()) {
                    // Show entire outfit section
                    outfitSection.setVisibility(android.view.View.VISIBLE);
                    outfitAdapter = new ProductAdapter(products, wishlistViewModel, wishlist, userEmail);
                    recyclerViewOutfit.setAdapter(outfitAdapter);
                } else {
                    // Hide entire section if no recommendations
                    outfitSection.setVisibility(android.view.View.GONE);
                }
            });
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

    /**
     * Helper method to load images from both drawable resources and online URLs
     * Supports both "drawable://filename" and "http(s)://..." formats
     */
    private void loadProductImage(String imageUrl, ImageView imageView) {
        if (imageUrl.startsWith("drawable://")) {
            // Load from local drawable resource
            String drawableName = imageUrl.replace("drawable://", "").toLowerCase();
            int resourceId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            
            Glide.with(this)
                    .load(resourceId > 0 ? resourceId : R.drawable.error_image)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .into(imageView);
        } else if (imageUrl.startsWith("http")) {
            // Load from online URL
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {
            // Invalid URL format
            imageView.setImageResource(R.drawable.error_image);
        }
    }
}
