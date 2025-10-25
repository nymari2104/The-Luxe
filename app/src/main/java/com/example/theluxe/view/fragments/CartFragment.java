package com.example.theluxe.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import android.widget.TextView;
import com.example.theluxe.model.CartItemWithProduct;
import com.example.theluxe.view.activities.CheckoutActivity;
import com.example.theluxe.view.adapters.CartAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.example.theluxe.viewmodel.CartViewModel;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private Button buttonCheckout;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        buttonCheckout = view.findViewById(R.id.buttonCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);

        cartViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(CartViewModel.class);

        userEmail = requireActivity().getIntent().getStringExtra("USER_EMAIL");
        
        if (userEmail != null) {
            cartViewModel.init(userEmail);
        }

        adapter = new CartAdapter(cartViewModel, userEmail);
        recyclerView.setAdapter(adapter);

        cartViewModel.getCartWithProducts().observe(getViewLifecycleOwner(), cartItems -> {
            adapter.setCartItems(cartItems);
        });

        cartViewModel.getTotalAmount().observe(getViewLifecycleOwner(), total -> {
            textViewTotalAmount.setText(String.format("Total: %,.0f₫", total));
        });

        buttonCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            intent.putExtra("TOTAL_AMOUNT", cartViewModel.getTotalAmount().getValue());
            startActivity(intent);
        });

        // Add swipe-to-delete functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CartItemWithProduct item = adapter.getItem(position);

                new AlertDialog.Builder(getContext())
                        .setTitle("Remove Item")
                        .setMessage("Are you sure you want to remove this item from the cart?")
                        .setPositiveButton("Yes", (dialog, which) -> cartViewModel.deleteItem(userEmail, item.getProduct().getId()))
                        .setNegativeButton("No", (dialog, which) -> adapter.notifyItemChanged(position)) // Revert swipe
                        .setOnCancelListener(dialog -> adapter.notifyItemChanged(position)) // Revert swipe on cancel
                        .show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
