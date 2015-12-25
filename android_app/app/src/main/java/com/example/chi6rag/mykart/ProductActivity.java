package com.example.chi6rag.mykart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chi6rag.mykart.async_tasks.CreateOrderTask;
import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.view_models.ProductViewModel;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private String host;
    private String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        host = this.getString(R.string.host);
        port = this.getString(R.string.port);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productName = (TextView) findViewById(R.id.product_name);
        TextView productPrice = (TextView) findViewById(R.id.product_price);
        TextView productDescription = (TextView) findViewById(R.id.product_description);
        Button addToCartButton = (Button) findViewById(R.id.add_to_cart_button);

        final Product product = getIntent().getParcelableExtra(Product.TAG);
        ProductViewModel productViewModel = new ProductViewModel(product, getResources());

        productName.setText(product.name);
        productPrice.setText(productViewModel.formattedPrice());

        Picasso.with(this)
                .load(host + port + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder_2)
                .into(productImage);

        productDescription.setText(product.description);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumber = fetchCurrentOrderNumber();
                if (orderNumber == null) {
                    new CreateOrderTask(getApplicationContext()).execute();
                }
            }
        });
    }

    private String fetchCurrentOrderNumber() {
        SharedPreferences orderSharedPreferences = getSharedPreferences(Order.TAG, MODE_PRIVATE);
        return orderSharedPreferences.getString(Order.CURRENT_NUMBER_KEY, null);
    }
}
