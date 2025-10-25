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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.view.adapters.FeaturedProductAdapter;
import com.example.theluxe.view.adapters.ProductAdapter;
import com.example.theluxe.viewmodel.ProductViewModel;
import com.example.theluxe.viewmodel.RecommendationViewModel;
import com.example.theluxe.viewmodel.WishlistViewModel;

public class ProductListFragment extends Fragment {

    private ProductViewModel productViewModel;
    private RecommendationViewModel recommendationViewModel;
    private WishlistViewModel wishlistViewModel;

    private RecyclerView recyclerViewProducts, recyclerViewRecommendations;
    private ProductAdapter productAdapter;
    private FeaturedProductAdapter featuredAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Views
        recyclerViewProducts = view.findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        recyclerViewRecommendations = view.findViewById(R.id.recyclerViewRecommendations);
        recyclerViewRecommendations.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(recyclerViewRecommendations);

        // Setup ViewModels
        wishlistViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(WishlistViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        recommendationViewModel = new ViewModelProvider(this, 
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(RecommendationViewModel.class);
        
        String userEmail = requireActivity().getIntent().getStringExtra("USER_EMAIL");
        if (userEmail != null) {
            wishlistViewModel.init(userEmail);
        }

        // Fetch products once
        productViewModel.getProducts();

        // Observe data
        wishlistViewModel.getWishlist().observe(getViewLifecycleOwner(), wishlist -> productViewModel.products.observe(getViewLifecycleOwner(), products -> {
            productAdapter = new ProductAdapter(products, wishlistViewModel, wishlist, userEmail);
            recyclerViewProducts.setAdapter(productAdapter);
        }));
        
        if (userEmail != null) {
            recommendationViewModel.getRecommendations(userEmail).observe(getViewLifecycleOwner(), recommendedProducts -> {
                featuredAdapter = new FeaturedProductAdapter(recommendedProducts);
                recyclerViewRecommendations.setAdapter(featuredAdapter);
            });
        }
    }
}
