package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chi6rag.mykart.adapters.ProductListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchProductsTask;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.ProductCategory;

public class ProductsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, container, false);

        final ProductCategory productCategory = getArguments().getParcelable(ProductCategory.TAG);

        RecyclerView productsList = (RecyclerView) view.findViewById(R.id.products_list);
        productsList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        final ProductListAdapter productListAdapter = new ProductListAdapter(getContext());
        productsList.setAdapter(productListAdapter);

        new FetchProductsTask(getContext(),
                productListAdapter,
                view.findViewById(R.id.products_progress_bar),
                productsList)
                .execute(productCategory.id);

        productsList.addOnItemTouchListener(new ProductsListTouchListener(getContext(),
                new ProductsListTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = productListAdapter.findProductByPosition(position);
                        Intent intent = new Intent(getContext(), ProductActivity.class);
                        intent.putExtra(Product.TAG, product);
                        startActivity(intent);
                    }
                }));
        return view;
    }
}
