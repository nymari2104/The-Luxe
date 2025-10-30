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
import com.bumptech.glide.Glide;
import android.content.Context;
import com.example.theluxe.model.OrderDetailItem;

public class OrderDetailAdapter extends ListAdapter<OrderDetailItem, OrderDetailAdapter.ViewHolder> {

    public OrderDetailAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<OrderDetailItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<OrderDetailItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull OrderDetailItem oldItem, @NonNull OrderDetailItem newItem) {
                    return oldItem.getProductId().equals(newItem.getProductId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull OrderDetailItem oldItem, @NonNull OrderDetailItem newItem) {
                    return oldItem.getQuantity() == newItem.getQuantity() && oldItem.getPrice() == newItem.getPrice();
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
        OrderDetailItem item = getItem(position);
        holder.productName.setText(item.getName());
        holder.productQuantity.setText("Qty: " + item.getQuantity());
        holder.productPrice.setText(String.format("%,.0f₫", item.getPrice()));
        holder.productSize.setText("Size: " + item.getSize());

        loadProductImage(holder.itemView.getContext(), holder.productImage, item.getImageUrl());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity, productPrice, productSize;
        ImageView productImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textViewProductName);
            productQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productSize = itemView.findViewById(R.id.textViewProductSize);
            productImage = itemView.findViewById(R.id.imageViewProduct);
        }
    }
    
    private void loadProductImage(Context context, ImageView imageView, String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("drawable://")) {
            String drawableName = imageUrl.replace("drawable://", "").toLowerCase();
            int resourceId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            
            Glide.with(context)
                    .load(resourceId > 0 ? resourceId : R.drawable.error_image)
                    .into(imageView);
        } else if (imageUrl != null && imageUrl.startsWith("http")) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.error_image);
        }
    }
}
