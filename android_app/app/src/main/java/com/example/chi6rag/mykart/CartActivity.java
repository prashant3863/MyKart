package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chi6rag.mykart.adapters.LineItemListAdapter;
import com.example.chi6rag.mykart.async_tasks.StatusCallback;
import com.example.chi6rag.mykart.async_tasks.FetchOrderDetailsForCartTask;
import com.example.chi6rag.mykart.models.Order;

public class CartActivity extends AppCompatActivity {
    private static final String TOTAL = "Total: ";

    private Toolbar toolbar;
    private ListView cartLineItemsList;
    private ProgressBar cartProgressBar;
    private Button cartCheckoutButton;
    private TextView cartEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setupActionBar();
        initializeViewElements();

        Order order = getIntent().getParcelableExtra(Order.TAG);
        buildCartFor(order);
    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.cart);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeViewElements() {
        this.cartLineItemsList = (ListView) findViewById(R.id.cart_line_items_list);
        this.cartProgressBar = ((ProgressBar) findViewById(R.id.cart_progress_bar));
        this.cartCheckoutButton = (Button) findViewById(R.id.cart_checkout_button);
        this.cartEmptyTextView = (TextView) findViewById(R.id.cart_empty_text_view);
    }

    private void buildCartFor(Order order) {
        new FetchOrderDetailsForCartTask(
                this,
                order,
                cartProgressBar,
                new StatusCallback<Order>() {
                    @Override
                    public void onSuccess(Order order) {
                        if (order.lineItems.size() > 0) {
                            showCheckoutButton();
                            showLineItemsList();
                            populateCart(order);
                            showTotalAmountInActionBar(order);
                            listenForCheckoutEventTrigger(order);
                        } else {
                            showCartEmptyMessage();
                        }
                    }

                    private void showCartEmptyMessage() {
                        cartEmptyTextView.setVisibility(TextView.VISIBLE);
                    }

                    private void showTotalAmountInActionBar(Order order) {
                        toolbar.setTitle(TOTAL + order.displayTotal);
                    }

                    private void populateCart(Order order) {
                        LineItemListAdapter lineItemListAdapter = new LineItemListAdapter(CartActivity.this, order.lineItems);
                        cartLineItemsList.setAdapter(lineItemListAdapter);
                    }

                    private void showCheckoutButton() {
                        cartCheckoutButton.setVisibility(Button.VISIBLE);
                    }

                    private void showLineItemsList() {
                        cartLineItemsList.setVisibility(ListView.VISIBLE);
                    }

                    private void listenForCheckoutEventTrigger(final Order order) {
                        cartCheckoutButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                                intent.putExtra(Order.TAG, order);
                                startActivity(intent);
                            }
                        });
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
