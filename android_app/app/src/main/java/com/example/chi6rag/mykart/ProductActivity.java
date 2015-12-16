package com.example.chi6rag.mykart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private static final int DEFAULT_ID_VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Product.ID_TAG, DEFAULT_ID_VALUE);
        Product product = findById(id);

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productName = (TextView) findViewById(R.id.product_name);
        TextView productPrice = (TextView) findViewById(R.id.product_price);

        productImage.setImageResource(product.firstImageResource());
        productName.setText(product.name);
        productPrice.setText(product.formattedPrice());
    }

    private Product findById(int id) {
        for(Product product : MainActivity.PRODUCTS) {
            if(product.id == id)
                return product;
        }
        return null;
    }
}
