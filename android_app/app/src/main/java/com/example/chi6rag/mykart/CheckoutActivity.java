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
    private static final String PAYMENT = "payment";
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
        continueCheckoutProcessBasedOnOrderState(this.order);
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
                updateOrder((Order) updatedOrder);
                continueCheckoutProcessBasedOnOrderState(order);
            }

            @Override
            public void onFailure(Object errorResource) {
                Errors errors = ((ErrorsResource) errorResource).errors;
                continueCheckoutProcessBasedOnOrderState(order);
            }
        }).execute();
    }

    private void updateOrder(Order updatedOrder) {
        Order orderWithUpdatedState = updatedOrder;
        if (order.doesNotHaveSameStateAs(orderWithUpdatedState)) {
            order.updateStateByComparingWith(orderWithUpdatedState, CheckoutActivity.this);
        }
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
        AddressFragment addressFragment = new AddressFragment();
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
            public void onSuccess(Object updatedOrder) {
                updateOrder((Order) updatedOrder);
                continueCheckoutProcessBasedOnOrderState(order);
            }

            @Override
            public void onFailure(Object o) {
                Errors errors = ((ErrorsResource) o).errors;
                ErrorsViewModel errorsViewModel = new ErrorsViewModel(errors);

                continueCheckoutProcessBasedOnOrderState(order);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CheckoutActivity.this);
                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(errorsViewModel.formattedErrors());
            }
        }).execute();
    }

    private void advanceOrderStateToPayment(Order order) {
        new AdvanceOrderStateTask(order, new UIExecutor<Object>() {
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
            public void onSuccess(Object order) {
                continueCheckoutProcessBasedOnOrderState(((Order) order));
            }

            @Override
            public void onFailure(Object o) {
                Log.d(LOG_TAG, "Log Some Error");
            }
        }).execute();
    }

    private void promptForPayment(Order order) {
        stopLoaderAnimation();
        removeLoaderWrapperFromView();

        Bundle paymentFragmentBundle = new Bundle();
        paymentFragmentBundle.putParcelable(Order.TAG, order);

        PaymentFragment paymentFragment = new PaymentFragment();
        paymentFragment.setArguments(paymentFragmentBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_checkout_container, paymentFragment)
                .commit();
    }

    private void continueCheckoutProcessBasedOnOrderState(Order order) {
        switch (order.state) {
            case CART:
                advanceOrderStateToAddress();
                break;
            case ADDRESS:
                promptForAddress();
                break;
            case DELIVERY:
                advanceOrderStateToPayment(order);
                break;
            case PAYMENT:
                promptForPayment(order);
                break;
            default:
                finish();
                break;
        }
    }
}
