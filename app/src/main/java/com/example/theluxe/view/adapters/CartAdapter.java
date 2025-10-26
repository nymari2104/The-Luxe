package com.example.theluxe.view.adapters;

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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItemWithProduct> items = new ArrayList<>();
    private final CartViewModel viewModel;
    private final String userEmail;

    public CartAdapter(CartViewModel viewModel, String userEmail) {
        this.viewModel = viewModel;
        this.userEmail = userEmail;
        setHasStableIds(true); // Optimize RecyclerView
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getProduct().getId().hashCode();
    }

    public void setCartItems(List<CartItemWithProduct> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public CartItemWithProduct getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemWithProduct item = items.get(position);
        holder.textViewCartItemName.setText(item.getProduct().getName());
        holder.textViewCartItemPrice.setText(String.format("%,.0f₫", item.getProduct().getPrice()));
        holder.textViewCartItemQuantity.setText(String.valueOf(item.getQuantity()));

        double itemTotal = item.getProduct().getPrice() * item.getQuantity();
        holder.textViewItemTotal.setText(String.format("Total: %,.0f₫", itemTotal));

        // Load cart item image with Glide
        Glide.with(holder.itemView.getContext())
                .load(item.getProduct().getImageUrl())
                .placeholder(R.drawable.placeholder_product)
                .error(R.drawable.error_image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewCartItem);

        // Add fade-in animation for cart items
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(300)
                .start();

        holder.buttonIncrease.setOnClickListener(v -> {
            // Add haptic feedback
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            
            // Add scale animation
            v.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start();
                    })
                    .start();
            
            viewModel.updateQuantity(userEmail, item.getProduct().getId(), item.getQuantity() + 1);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            // Add haptic feedback
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            
            // Add scale animation
            v.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start();
                    })
                    .start();
            
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity > 0) {
                viewModel.updateQuantity(userEmail, item.getProduct().getId(), newQuantity);
            } else {
                // Show confirmation dialog
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

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
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
