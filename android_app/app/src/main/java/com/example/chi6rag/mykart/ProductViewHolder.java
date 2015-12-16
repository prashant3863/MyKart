package com.example.chi6rag.mykart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    protected LinearLayout productView;
    protected ImageView productImage;
    protected TextView productName;
    protected TextView productPrice;

    public ProductViewHolder(View itemView) {
        super(itemView);
        this.productView = (LinearLayout) itemView;
        this.productImage = (ImageView) itemView.findViewById(R.id.product_list_item_image);
        this.productName = (TextView) itemView.findViewById(R.id.product_list_item_name);
        this.productPrice = (TextView) itemView.findViewById(R.id.product_list_item_price);
    }
}
