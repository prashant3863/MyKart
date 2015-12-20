package com.example.chi6rag.mykart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chi6rag.mykart.ProductViewHolder;
import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.ProductsResource;
import com.squareup.picasso.Picasso;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private final Context context;
    private ProductsResource products;
    private final String HOST;
    private final String PORT;

    public ProductListAdapter(Context context) {
        this.context = context;
        HOST = context.getString(R.string.host);
        PORT = context.getString(R.string.port).replace("/", "");
        this.products = new ProductsResource();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = this.products.get(position).product;
        holder.productView.setTag(R.id.product_id_tag, product.id);
        holder.productName.setText(product.name);
        holder.productPrice.setText(product.formattedPrice());
        Picasso.with(context)
                .load(HOST + PORT + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(ProductsResource productsResource) {
        this.products.addAll(productsResource);
    }

    public Product findProductByPosition(int position) {
        return this.products.get(position).product;
    }
}
