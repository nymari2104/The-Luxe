package com.example.theluxe.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.view.adapters.ProductAdapter;
import com.example.theluxe.viewmodel.WishlistViewModel;

public class WishlistFragment extends Fragment {

    private WishlistViewModel wishlistViewModel;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewWishlist);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Using GridLayout here as well

        wishlistViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(WishlistViewModel.class);

        String userEmail = requireActivity().getIntent().getStringExtra("USER_EMAIL");
        wishlistViewModel.init(userEmail);


        adapter = new ProductAdapter(wishlistViewModel, userEmail);
        recyclerView.setAdapter(adapter);

        wishlistViewModel.getWishlist(userEmail).observe(getViewLifecycleOwner(), wishlist -> {
            adapter.submitList(wishlist);
            adapter.updateWishlist(wishlist); // Also update the internal wishlist for star state
        });
    }
}
