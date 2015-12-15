package com.example.chi6rag.mykart;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    static float density = 0;
    private ArrayList<Product> products;

    public ProductListAdapter(Activity context, ArrayList<Product> products) {
        density = context.getResources().getDisplayMetrics().density;
        this.products = products;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        protected ImageView productImage;
        protected TextView productName;
        protected TextView productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView) itemView.findViewById(R.id.product_list_item_image);
            this.productName = (TextView) itemView.findViewById(R.id.product_list_item_name);
            this.productPrice = (TextView) itemView.findViewById(R.id.product_list_item_price);
        }
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
        holder.productName.setText(product.name);
        holder.productPrice.setText(String.valueOf(product.price));
        holder.productImage.setImageResource(product.imageResources[0]);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

