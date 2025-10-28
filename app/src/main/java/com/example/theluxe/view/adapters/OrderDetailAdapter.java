package com.example.theluxe.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.model.CartItemWithProduct;

public class OrderDetailAdapter extends ListAdapter<CartItemWithProduct, OrderDetailAdapter.ViewHolder> {

    public OrderDetailAdapter() {
        super(DIFF_CALLBACK);
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemWithProduct item = getItem(position);
        holder.productName.setText(item.getProduct().getName());
        holder.productQuantity.setText("Qty: " + item.getQuantity());
        holder.productPrice.setText(String.format("%,.0f₫", item.getProduct().getPrice()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity, productPrice;
        ImageView productImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textViewProductName);
            productQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productImage = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}
