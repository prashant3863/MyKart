package com.example.chi6rag.mykart.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chi6rag.mykart.ProductViewHolder;
import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.ProductResource;
import com.example.chi6rag.mykart.models.ProductsResource;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private ArrayList<ProductResource> products;

    public ProductListAdapter() {
        this.products = new ArrayList<ProductResource>();
    }

    public ProductListAdapter(ArrayList<ProductResource> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductResource product = this.products.get(position);
        holder.productView.setTag(R.id.product_id_tag, product.id);
        holder.productName.setText(product.name);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(ProductsResource productsResource) {
        this.products.addAll(productsResource.products);
    }
}
