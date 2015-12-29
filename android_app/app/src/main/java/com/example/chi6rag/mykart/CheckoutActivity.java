package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.chi6rag.mykart.async_tasks.AdvanceOrderStateTask;
import com.example.chi6rag.mykart.async_tasks.ExtensibleStatusCallback;
import com.example.chi6rag.mykart.async_tasks.UIExecutor;
import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.network.Errors;
import com.example.chi6rag.mykart.network.ErrorsResource;

public class CheckoutActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        progressBar = ((ProgressBar) findViewById(R.id.checkout_progress_loader));

        final Order order = getIntent().getParcelableExtra(Order.TAG);
        new AdvanceOrderStateTask(order, new UIExecutor<Object>() {
            @Override
            public void onPreExecute() {
                startLoaderAnimation();
            }

            @Override
            public void onPostExecute(Object object) {
                stopLoaderAnimation();
                removeLoader();
            }
        }, new ExtensibleStatusCallback<Object>() {
            @Override
            public void onSuccess(Object order) {
                promptForAddress(((Order) order));
            }

            @Override
            public void onFailure(Object errorResource) {
                Errors errors = ((ErrorsResource) errorResource).errors;
                if (errors.containsErrorLike("address")) {
                    promptForAddress(order);
                }
            }
        }).execute();
    }


    private void startLoaderAnimation() {
        progressBar.animate().start();
    }

    private void removeLoader() {
        ((ViewGroup) this.progressBar.getParent()).removeView(progressBar);
    }

    private void stopLoaderAnimation() {
        this.progressBar.animate().cancel();
    }

    private void promptForAddress(Order order) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Order.TAG, order);

        AddressFragment addressFragment = new AddressFragment();
        addressFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_checkout_container, addressFragment)
                .commit();
    }
}
