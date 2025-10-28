package com.example.theluxe.view.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theluxe.R;
import com.example.theluxe.model.User;
import com.example.theluxe.util.SizeUtils;
import com.example.theluxe.viewmodel.ProductDetailViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.view.adapters.ProductAdapter;
import java.util.ArrayList;

import com.example.theluxe.viewmodel.ProfileViewModel;
import com.example.theluxe.viewmodel.WishlistViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductDetailViewModel viewModel;
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

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProductDetailViewModel.class);

        recyclerViewOutfit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        
        String productId = getIntent().getStringExtra("PRODUCT_ID");
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        
        viewModel.init(userEmail, productId);

        viewModel.user.observe(this, user -> {
            // Now you have the user object to use for size suggestions
        });
        
        viewModel.product.observe(this, product -> {
            if (product != null) {
                loadProductImage(product.getImageUrl(), imageViewProductDetail);
                textViewProductNameDetail.setText(product.getName());
                textViewProductBrandDetail.setText(product.getBrand());
                textViewProductDescriptionDetail.setText(product.getDescription());
                textViewProductPriceDetail.setText(String.format("%,.0f₫", product.getPrice()));
            }
        });

        WishlistViewModel wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        outfitAdapter = new ProductAdapter(wishlistViewModel, userEmail);
        recyclerViewOutfit.setAdapter(outfitAdapter);

        viewModel.getWishlist(userEmail).observe(this, wishlist -> {
            outfitAdapter.updateWishlist(wishlist);
        });
        
        viewModel.outfitRecommendations.observe(this, products -> {
            if (products != null && !products.isEmpty()) {
                outfitSection.setVisibility(View.VISIBLE);
                outfitAdapter.submitList(products);
            } else {
                outfitSection.setVisibility(View.GONE);
            }
        });

        buttonAddToCart.setOnClickListener(v -> {
            showAddToCartDialog();
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

    private void showAddToCartDialog() {
        if (viewModel.product.getValue() == null) {
            Toast.makeText(this, "Product details not loaded yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_to_cart, null);
        builder.setView(dialogView);

        TextView productName = dialogView.findViewById(R.id.textViewDialogProductName);
        RadioGroup radioGroupSize = dialogView.findViewById(R.id.radioGroupSize);
        TextView quantityView = dialogView.findViewById(R.id.textViewDialogQuantity);
        ImageButton increaseBtn = dialogView.findViewById(R.id.buttonDialogIncrease);
        ImageButton decreaseBtn = dialogView.findViewById(R.id.buttonDialogDecrease);
        Button addToCartBtn = dialogView.findViewById(R.id.buttonDialogAddToCart);

        productName.setText(viewModel.product.getValue().getName());
        final int[] quantity = {1};
        quantityView.setText("1");

        increaseBtn.setOnClickListener(v -> {
            quantity[0]++;
            quantityView.setText(String.valueOf(quantity[0]));
        });

        decreaseBtn.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                quantityView.setText(String.valueOf(quantity[0]));
            }
        });

        // Suggest and pre-select size
        User currentUser = viewModel.user.getValue();
        if (currentUser != null) {
            String suggestedSize = SizeUtils.suggestSize(currentUser.getGender(), currentUser.getHeight(), currentUser.getWeight());
            if (suggestedSize != null) {
                for (int i = 0; i < radioGroupSize.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroupSize.getChildAt(i);
                    if (radioButton.getText().toString().equalsIgnoreCase(suggestedSize)) {
                        radioButton.setChecked(true);
                        break;
                    }
                }
            }
        }
        
        AlertDialog dialog = builder.create();

        addToCartBtn.setOnClickListener(v -> {
            int selectedId = radioGroupSize.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a size", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedRadioButton = dialogView.findViewById(selectedId);
            String size = selectedRadioButton.getText().toString();
            String userEmail = getIntent().getStringExtra("USER_EMAIL");

            viewModel.addToCart(userEmail, size, quantity[0]);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
