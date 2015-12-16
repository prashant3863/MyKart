package com.example.chi6rag.mykart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private ArrayList<Product> products;

    public ProductListAdapter(ArrayList<Product> products) {
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
        Product product = this.products.get(position);
        holder.productView.setTag(R.id.product_id_tag, product.id);
        holder.productName.setText(product.name);
        holder.productPrice.setText(product.formattedPrice());
        holder.productImage.setImageResource(product.imageResources[0]);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
