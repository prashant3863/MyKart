package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.chi6rag.mykart.async_tasks.CreateOrderTask;
import com.example.chi6rag.mykart.async_tasks.StatusCallback;
import com.example.chi6rag.mykart.network.ConnectionDetector;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {
    public static final String TAG = "com.example.chi6rag.mykart.models.ORDER_SHARED_PREFERENCES_TAG";
    public static final String CURRENT_NUMBER_KEY = "com.example.chi6rag.mykart.models.ORDER_CURRENT_KEY";
    public static final String CURRENT_TOKEN = "com.example.chi6rag.mykart.models.ORDER_CURRENT_TOKEN";
    public static final String STATUS = "com.example.chi6rag.mykart.models.ORDER_CURRENT_STATUS";

    public String number;
    public String token;
    public String state;
    @SerializedName("payment_methods")
    List<PaymentMethod> paymentMethods;
    Integer id;
    String email;
    String currency;
    @SerializedName("line_items")
    public List<LineItem> lineItems;
    @SerializedName("display_total")
    public String displayTotal;
    @SerializedName("total_quantity")
    Integer totalQuantity;
    @SerializedName("bill_address")
    AddressForOrder billAddress;
    @SerializedName("ship_address")
    AddressForOrder shipAddress;

    public static void getCurrentInstance(final Context context, final StatusCallback<Order> orderStatusCallback) {
        String orderToken = fetchCurrentOrderToken(context);
        String orderNumber = fetchCurrentOrderNumber(context);

        if (orderNumber == null && orderToken == null) {
            if (cannotCreateNewOrder(context, orderStatusCallback)) return;
            createNewOrder(context, orderStatusCallback);
        } else {
            orderStatusCallback.onSuccess(new Order(orderNumber, orderToken, null));
        }
    }

    public boolean isValid() {
        return this.number != null && this.token != null;
    }

    private static void createNewOrder(final Context context, StatusCallback<Order> orderStatusCallback) {
        new CreateOrderTask(new StatusCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                saveOrderTokenToSharedPreferences(context, order);
                saveOrderNumberToSharedPreferences(context, order);
            }

            @Override
            public void onFailure() {
                Log.d("chi6rag", "creation of order failed");
            }
        }, orderStatusCallback).execute();
    }

    private static boolean cannotCreateNewOrder(Context context, StatusCallback<Order> orderStatusCallback) {
        ConnectionDetector connectionDetector = new ConnectionDetector(context);
        if (connectionDetector.isNotConnectedToInternet()) {
            orderStatusCallback.onFailure();
            return true;
        }
        return false;
    }

    public Order(String number, String token, String state) {
        this.number = number;
        this.token = token;
        this.state = state;
    }

    public void updateStateByComparingWith(Order that, Context context) {
        if (that == null) return;
        this.state = that.state;
        saveOrderStatusToSharedPreferences(context, that);
    }

    public boolean doesNotHaveSameStateAs(Order that) {
        if (this.state != that.state) return true;
        return false;
    }

    private static void saveOrderStatusToSharedPreferences(Context context, Order order) {
        context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.STATUS, order.state)
                .commit();
    }

    private static void saveOrderTokenToSharedPreferences(Context context, Order order) {
        context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_TOKEN, order.token)
                .commit();
    }

    private static void saveOrderNumberToSharedPreferences(Context context, Order order) {
        context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_NUMBER_KEY, order.number)
                .commit();
    }

    private static String fetchCurrentOrderToken(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Order.TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Order.CURRENT_TOKEN, null);
    }

    private static String fetchCurrentOrderNumber(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Order.TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Order.CURRENT_NUMBER_KEY, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeString(this.token);
        dest.writeString(this.state);
        dest.writeValue(this.id);
        dest.writeString(this.email);
        dest.writeString(this.currency);
        dest.writeTypedList(lineItems);
        dest.writeString(this.displayTotal);
        dest.writeValue(this.totalQuantity);
        dest.writeParcelable(this.billAddress, 0);
        dest.writeParcelable(this.shipAddress, 0);
        dest.writeTypedList(paymentMethods);
    }

    protected Order(Parcel in) {
        this.number = in.readString();
        this.token = in.readString();
        this.state = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.email = in.readString();
        this.currency = in.readString();
        this.lineItems = in.createTypedArrayList(LineItem.CREATOR);
        this.displayTotal = in.readString();
        this.totalQuantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.billAddress = in.readParcelable(AddressForOrder.class.getClassLoader());
        this.shipAddress = in.readParcelable(AddressForOrder.class.getClassLoader());
        this.paymentMethods = in.createTypedArrayList(PaymentMethod.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public PaymentMethods paymentMethods() {
        List<PaymentMethod> selectedPaymentMethods = new ArrayList<>();
        for(PaymentMethod paymentMethod : paymentMethods) {
            if(paymentMethod.isCOD()) selectedPaymentMethods.add(paymentMethod);
        }
        return new PaymentMethods(selectedPaymentMethods);
    }
}
