package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chi6rag.mykart.models.Cart;
import com.example.chi6rag.mykart.models.Product;

public class ProductActivity extends AppCompatActivity implements ProductDetailFragment.OnInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Product product = getIntent().getParcelableExtra(Product.TAG);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.TAG, product);

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.product_container, productDetailFragment)
                .commit();
    }

    @Override
    public void onAddToCartButtonClick(Product product) {
        Cart cart = Cart.getInstance(this);
        cart.addProduct(product);
    }
}
