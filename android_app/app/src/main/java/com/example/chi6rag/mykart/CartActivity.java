package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.chi6rag.mykart.adapters.LineItemListAdapter;
import com.example.chi6rag.mykart.async_tasks.Callback;
import com.example.chi6rag.mykart.async_tasks.FetchOrderDetailsForCartTask;
import com.example.chi6rag.mykart.models.Order;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private static final String TOTAL = "Total: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setupActionBar();

        final ListView cartLineItemsList = (ListView) findViewById(R.id.cart_line_items_list);
        ProgressBar cartProgressBar = ((ProgressBar) findViewById(R.id.cart_progress_bar));
        Button cartCheckoutButton = (Button) findViewById(R.id.cart_checkout_button);

        Order incompleteOrder = getIntent().getParcelableExtra(Order.TAG);
        new FetchOrderDetailsForCartTask(this, incompleteOrder, cartProgressBar, cartLineItemsList,
                cartCheckoutButton, new Callback<Order>() {
            @Override
            public void onSuccess(Order order) {
                LineItemListAdapter lineItemListAdapter = new LineItemListAdapter(CartActivity.this, order.lineItems);
                cartLineItemsList.setAdapter(lineItemListAdapter);
                toolbar.setTitle(TOTAL + order.displayTotal);
            }

            @Override
            public void onFailure() {
                Log.d("chi6rag", "Internet Problem");
            }
        }).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart, menu);
        return true;
    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.cart);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_home:
                finish();
                break;
            default:
        }
        return false;
    }
}
