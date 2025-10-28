package com.example.theluxe.view.adapters;

import android.content.Context;
import android.view.HapticFeedbackConstants;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theluxe.R;
import com.example.theluxe.model.CartItemWithProduct;
import com.example.theluxe.viewmodel.CartViewModel;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CartAdapter extends ListAdapter<CartItemWithProduct, CartAdapter.CartViewHolder> {

    private final CartViewModel viewModel;
    private final String userEmail;

    public CartAdapter(CartViewModel viewModel, String userEmail) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
        this.userEmail = userEmail;
    }

    private static final DiffUtil.ItemCallback<CartItemWithProduct> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CartItemWithProduct>() {
                @Override
                public boolean areItemsTheSame(@NonNull CartItemWithProduct oldItem, @NonNull CartItemWithProduct newItem) {
                    return oldItem.getProduct().getId().equals(newItem.getProduct().getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CartItemWithProduct oldItem, @NonNull CartItemWithProduct newItem) {
                    return oldItem.getQuantity() == newItem.getQuantity();
                }
            };
            
    public CartItemWithProduct getItemAt(int position) {
        return getItem(position);
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemWithProduct item = getItem(position);
        holder.textViewCartItemName.setText(item.getProduct().getName());
        holder.textViewCartItemPrice.setText(String.format("%,.0f₫", item.getProduct().getPrice()));
        holder.textViewCartItemQuantity.setText(String.valueOf(item.getQuantity()));

        double itemTotal = item.getProduct().getPrice() * item.getQuantity();
        holder.textViewItemTotal.setText(String.format("Total: %,.0f₫", itemTotal));

        loadProductImage(holder.itemView.getContext(), holder.imageViewCartItem, item.getProduct().getImageUrl());

        holder.itemView.setAlpha(0f);
        holder.itemView.animate().alpha(1f).setDuration(300).start();

        holder.buttonIncrease.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            viewModel.updateQuantity(userEmail, item.getProduct().getId(), item.getQuantity() + 1);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity > 0) {
                viewModel.updateQuantity(userEmail, item.getProduct().getId(), newQuantity);
            } else {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Remove Item")
                        .setMessage("Are you sure you want to remove this item from the cart?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                            viewModel.deleteItem(userEmail, item.getProduct().getId());
                        })
                        .setNegativeButton("No", null)
                        .show();
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

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCartItem;
        TextView textViewCartItemName;
        TextView textViewCartItemPrice;
        TextView textViewCartItemQuantity;
        TextView textViewItemTotal;
        ImageButton buttonIncrease;
        ImageButton buttonDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCartItem = itemView.findViewById(R.id.imageViewCartItem);
            textViewCartItemName = itemView.findViewById(R.id.textViewCartItemName);
            textViewCartItemPrice = itemView.findViewById(R.id.textViewCartItemPrice);
            textViewCartItemQuantity = itemView.findViewById(R.id.textViewCartItemQuantity);
            textViewItemTotal = itemView.findViewById(R.id.textViewItemTotal);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
        }
    }
}
