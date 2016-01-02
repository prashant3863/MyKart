package com.example.chi6rag.mykart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.async_tasks.AdvanceOrderStateTask;
import com.example.chi6rag.mykart.async_tasks.ExtensibleStatusCallback;
import com.example.chi6rag.mykart.async_tasks.UIExecutor;
import com.example.chi6rag.mykart.async_tasks.UpdateOrderAddressTask;
import com.example.chi6rag.mykart.models.AddressForOrder;
import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.network.Errors;
import com.example.chi6rag.mykart.network.ErrorsResource;
import com.example.chi6rag.mykart.view_models.ErrorsViewModel;

public class CheckoutActivity extends AppCompatActivity implements
        AddressFragment.OnNextButtonClickListener {
    private static final String ADDRESS = "address";
    private static final String CART = "cart";
    private static final String DELIVERY = "delivery";
    private static final String LOG_TAG = "chi6rag";

    private ProgressBar progressBar;
    private Order order;
    private RelativeLayout progressBarWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        progressBar = ((ProgressBar) findViewById(R.id.checkout_progress_loader));
        progressBarWrapper = ((RelativeLayout) findViewById(R.id.checkout_progress_loader_wrapper));

        this.order = getIntent().getParcelableExtra(Order.TAG);

        switch (this.order.state) {
            case CART:
                advanceOrderStateToAddress();
                break;
            case ADDRESS:
                promptForAddress();
                break;
            case DELIVERY:
                Log.d(LOG_TAG, "Prompt for choosing shipping!");
                finish();
            default:
                finish();
                break;
        }
    }

    private void advanceOrderStateToAddress() {
        new AdvanceOrderStateTask(order, new UIExecutor<Object>() {
            @Override
            public void onPreExecute() {
                startLoaderAnimation();
            }

            @Override
            public void onPostExecute(Object object) {
                stopLoaderAnimation();
                removeLoaderWrapperFromView();
            }
        }, new ExtensibleStatusCallback<Object>() {
            @Override
            public void onSuccess(Object updatedOrder) {
                Order orderWithUpdatedState = ((Order) updatedOrder);
                if (order.doesNotHaveSameStateAs(orderWithUpdatedState)) {
                    order.updateStateByComparingWith(orderWithUpdatedState, CheckoutActivity.this);
                }
                promptForAddress();
            }

            @Override
            public void onFailure(Object errorResource) {
                Errors errors = ((ErrorsResource) errorResource).errors;
                if (errors.containsErrorLike("address")) {
                    promptForAddress();
                }
            }
        }).execute();
    }

    private void startLoaderAnimation() {
        progressBar.animate().start();
    }

    private void showLoaderWrapperInView() {
        this.progressBarWrapper.setVisibility(RelativeLayout.VISIBLE);
        this.progressBarWrapper.bringToFront();
    }

    private void removeLoaderWrapperFromView() {
        this.progressBarWrapper.setVisibility(RelativeLayout.GONE);
    }

    private void stopLoaderAnimation() {
        this.progressBar.animate().cancel();
    }

    private void promptForAddress() {
        Bundle bundle = new Bundle();

        AddressFragment addressFragment = new AddressFragment();
        addressFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_checkout_container, addressFragment)
                .commit();
    }

    @Override
    public void onNextButtonClick(AddressForOrder addressForOrder) {
        new UpdateOrderAddressTask(order, addressForOrder.toJson(), new UIExecutor<Object>() {
            @Override
            public void onPreExecute() {
                showLoaderWrapperInView();
                startLoaderAnimation();
            }

            @Override
            public void onPostExecute(Object o) {
                stopLoaderAnimation();
                removeLoaderWrapperFromView();
            }
        }, new ExtensibleStatusCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                advanceOrderStateToShipping();
            }

            @Override
            public void onFailure(Object o) {
                Errors errors = ((ErrorsResource) o).errors;
                ErrorsViewModel errorsViewModel = new ErrorsViewModel(errors);

                promptForAddress();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CheckoutActivity.this);
                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(errorsViewModel.formattedErrors());
            }
        }).execute();
    }

    private void advanceOrderStateToShipping() {
        Log.d(LOG_TAG, "State advanced to shipping!");
    }
}
