package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chi6rag.mykart.models.Product;
import com.squareup.picasso.Picasso;

// TODO: Amir - 23/12/15 - remove editor warnings
public class ProductActivity extends AppCompatActivity {
    // TODO: Amir - 23/12/15 - not constants
    private String HOST;
    private String PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HOST = this.getString(R.string.host);
        PORT = this.getString(R.string.port);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productName = (TextView) findViewById(R.id.product_name);
        TextView productPrice = (TextView) findViewById(R.id.product_price);
        TextView productDescription = (TextView) findViewById(R.id.product_description);

        Product product = getIntent().getParcelableExtra(Product.TAG);

        productName.setText(product.name);
// TODO: Amir - 23/12/15 - use view model and unit test
        productPrice.setText(product.formattedPrice());

        Picasso.with(this)
                .load(HOST + PORT + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder_2)
                .into(productImage);

        productDescription.setText(product.description);
    }
}
