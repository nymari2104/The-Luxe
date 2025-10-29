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
import java.util.ArrayList;
import java.util.List;
import com.example.theluxe.R;
import com.example.theluxe.model.Product;
import com.example.theluxe.view.activities.ProductDetailActivity;
import com.example.theluxe.viewmodel.WishlistViewModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private final WishlistViewModel wishlistViewModel;
    private List<Product> wishlist = new ArrayList<>();
    private final String userEmail;
    private int lastPosition = -1;

    public ProductAdapter(WishlistViewModel wishlistViewModel, String userEmail) {
        super(DIFF_CALLBACK);
        this.wishlistViewModel = wishlistViewModel;
        this.userEmail = userEmail;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateWishlist(List<Product> newWishlist) {
        this.wishlist = newWishlist;
        notifyDataSetChanged(); // Notify to re-check the star state
    }
    
    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    // Add more checks if needed for content changes
                    return oldItem.getName().equals(newItem.getName()) &&
                           oldItem.getPrice() == newItem.getPrice();
                }
            };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = getItem(position);
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductBrand.setText(product.getBrand());
        holder.textViewProductPrice.setText(String.format("%,.0f₫", product.getPrice()));

        loadProductImage(holder.itemView.getContext(), holder.imageViewProduct, product.getImageUrl());

        boolean isWishlisted = wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()));
        holder.buttonAddToWishlist.setImageResource(isWishlisted ? R.drawable.ic_star_filled : R.drawable.ic_star_border);

        if (position > lastPosition) {
            holder.itemView.setAlpha(0f);
            holder.itemView.animate().alpha(1f).setDuration(300).setStartDelay((position - lastPosition) * 50L).start();
            lastPosition = position;
        }

        holder.itemView.setOnClickListener(v -> {
            v.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY);
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            intent.putExtra("USER_EMAIL", userEmail);
            v.getContext().startActivity(intent);
        });

        holder.buttonAddToWishlist.setOnClickListener(v -> {
            v.performHapticFeedback(android.view.HapticFeedbackConstants.CLOCK_TICK);
            boolean isCurrentlyWishlisted = wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()));
            if (isCurrentlyWishlisted) {
                wishlistViewModel.removeFromWishlist(product);
                Toast.makeText(v.getContext(), "💔 Removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                wishlistViewModel.addToWishlist(product);
                Toast.makeText(v.getContext(), "❤️ Added to wishlist", Toast.LENGTH_SHORT).show();
            }
        });
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
