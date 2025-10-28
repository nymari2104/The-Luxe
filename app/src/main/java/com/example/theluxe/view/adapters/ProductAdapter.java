package com.example.theluxe.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theluxe.R;
import com.example.theluxe.model.Product;
import com.example.theluxe.view.activities.ProductDetailActivity;
import com.example.theluxe.viewmodel.WishlistViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final WishlistViewModel wishlistViewModel;
    private final List<Product> wishlist;
    private final String userEmail;
    private int lastPosition = -1;

    public ProductAdapter(List<Product> productList, WishlistViewModel wishlistViewModel, List<Product> wishlist, String userEmail) {
        this.productList = productList;
        this.wishlistViewModel = wishlistViewModel;
        this.wishlist = wishlist;
        this.userEmail = userEmail;
        setHasStableIds(true); // Optimize RecyclerView
    }

    @Override
    public long getItemId(int position) {
        // Use product ID for stable IDs
        return productList.get(position).getId().hashCode();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductBrand.setText(product.getBrand());
        holder.textViewProductPrice.setText(String.format("%,.0f₫", product.getPrice()));

        // Load product image with Glide (supports both local and online)
        loadProductImage(holder.itemView.getContext(), holder.imageViewProduct, product.getImageUrl());

        if (wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            holder.buttonAddToWishlist.setImageResource(R.drawable.ic_star_filled);
        } else {
            holder.buttonAddToWishlist.setImageResource(R.drawable.ic_star_border);
        }

        // Add fade-in animation only for new items
        if (position > lastPosition) {
            holder.itemView.setAlpha(0f);
            holder.itemView.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setStartDelay((position - lastPosition) * 50L)
                    .start();
            lastPosition = position;
        }

        holder.itemView.setOnClickListener(v -> {
            // Add haptic feedback
            v.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY);
            
            // Add scale animation on click
            v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start();
                        
                        Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                        intent.putExtra("PRODUCT_ID", product.getId());
                        intent.putExtra("USER_EMAIL", userEmail);
                        v.getContext().startActivity(intent);
                    })
                    .start();
        });

        holder.buttonAddToWishlist.setOnClickListener(v -> {
            // Add haptic feedback
            v.performHapticFeedback(android.view.HapticFeedbackConstants.CLOCK_TICK);
            
            // Add scale animation for wishlist button
            v.animate()
                    .scaleX(1.3f)
                    .scaleY(1.3f)
                    .setDuration(150)
                    .withEndAction(() -> {
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(150)
                                .start();
                    })
                    .start();

            if (wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
                wishlistViewModel.removeFromWishlist(product);
                Toast.makeText(v.getContext(), "💔 Removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                wishlistViewModel.addToWishlist(product);
                Toast.makeText(v.getContext(), "❤️ Added to wishlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (productList != null) ? productList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWishlist(List<Product> wishlist) {
        this.wishlist.clear();
        this.wishlist.addAll(wishlist);
        notifyDataSetChanged();
    }

    /**
     * Helper method to load images from both drawable resources and online URLs
     * Supports both "drawable://filename" and "http(s)://..." formats
     */
    private void loadProductImage(Context context, ImageView imageView, String imageUrl) {
        if (imageUrl.startsWith("drawable://")) {
            // Load from local drawable resource
            String drawableName = imageUrl.replace("drawable://", "").toLowerCase();
            int resourceId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            
            Glide.with(context)
                    .load(resourceId > 0 ? resourceId : R.drawable.error_image)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .into(imageView);
        } else if (imageUrl.startsWith("http")) {
            // Load from online URL
            Glide.with(context)
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

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewProductBrand;
        TextView textViewProductPrice;
        ImageButton buttonAddToWishlist;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductBrand = itemView.findViewById(R.id.textViewProductBrand);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            buttonAddToWishlist = itemView.findViewById(R.id.buttonAddToWishlist);
        }
    }
}
