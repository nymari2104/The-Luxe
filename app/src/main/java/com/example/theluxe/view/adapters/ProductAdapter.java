package com.example.theluxe.view.adapters;

import android.annotation.SuppressLint;
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

    public ProductAdapter(List<Product> productList, WishlistViewModel wishlistViewModel, List<Product> wishlist, String userEmail) {
        this.productList = productList;
        this.wishlistViewModel = wishlistViewModel;
        this.wishlist = wishlist;
        this.userEmail = userEmail;
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

        if (wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            holder.buttonAddToWishlist.setImageResource(R.drawable.ic_star_filled);
        } else {
            holder.buttonAddToWishlist.setImageResource(R.drawable.ic_star_border);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            intent.putExtra("USER_EMAIL", userEmail); // Add userEmail to the intent
            v.getContext().startActivity(intent);
        });

        holder.buttonAddToWishlist.setOnClickListener(v -> {
            if (wishlist.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
                wishlistViewModel.removeFromWishlist(product);
                Toast.makeText(v.getContext(), "Removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                wishlistViewModel.addToWishlist(product);
                Toast.makeText(v.getContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
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
