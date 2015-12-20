package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chi6rag.mykart.adapters.ProductListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchProductsTask;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.ProductCategory;

public class ProductsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        final ProductCategory productCategory = intent.getParcelableExtra(ProductCategory.TAG);

        RecyclerView productsList = (RecyclerView) findViewById(R.id.products_list);
        productsList.setLayoutManager(new GridLayoutManager(this, 2));
        final ProductListAdapter productListAdapter = new ProductListAdapter(this);
        productsList.setAdapter(productListAdapter);

        new FetchProductsTask(this,
                productListAdapter,
                this.findViewById(R.id.products_progress_bar),
                productsList)
                .execute(productCategory.id);

        productsList.addOnItemTouchListener(new ProductsListTouchListener(this,
                new ProductsListTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = productListAdapter.findProductByPosition(position);
                        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                        intent.putExtra(Product.TAG, product);
                        startActivity(intent);
                    }
                }));
    }
}
