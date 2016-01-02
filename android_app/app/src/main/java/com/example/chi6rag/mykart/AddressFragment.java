package com.example.chi6rag.mykart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chi6rag.mykart.models.AddressForOrder;

public class AddressFragment extends Fragment {
    private Button addressNextButton;
    private OnNextButtonClickListener onNextButtonClickListener;
    private EditText addressEditFirstName;
    private EditText addressEditLastName;
    private EditText addressEditBillingAddress;
    private EditText addressEditCity;
    private EditText addressEditMobileNumber;
    private EditText addressEditZipCode;
    private EditText addressEmail;

    public interface OnNextButtonClickListener {
        void onNextButtonClick(AddressForOrder addressForOrder);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            this.onNextButtonClickListener = ((OnNextButtonClickListener) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "OnNextButtonClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment, container, false);

        initializeViews(view);
        setEventListenerForNextButton();
        return view;
    }

    private void setEventListenerForNextButton() {
        this.addressNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressForOrder addressForOrder = buildAddressUsingFormFields();
                onNextButtonClickListener.onNextButtonClick(addressForOrder);
            }
        });
    }

    private AddressForOrder buildAddressUsingFormFields() {
        String emailAddress = this.addressEmail.getText().toString();
        String firstName = this.addressEditFirstName.getText().toString();
        String lastName = this.addressEditLastName.getText().toString();
        String billingAddress = this.addressEditBillingAddress.getText().toString();
        String city = this.addressEditCity.getText().toString();
        String mobileNumber = this.addressEditMobileNumber.getText().toString();
        String zipCode = this.addressEditZipCode.getText().toString();
        Integer stateId = AddressForOrder.DELHI_STATE_ID;
        Integer countryId = AddressForOrder.INDIA_COUNTRY_ID;

        return new AddressForOrder(emailAddress, firstName, lastName, billingAddress, city,
                mobileNumber, zipCode, stateId, countryId);
    }

    private void initializeViews(View view) {
        this.addressNextButton = ((Button) view.findViewById(R.id.address_next_button));
        this.addressEmail = ((EditText) view.findViewById(R.id.address_edit_email));
        this.addressEditFirstName = (EditText) view.findViewById(R.id.address_edit_first_name);
        this.addressEditLastName = (EditText) view.findViewById(R.id.address_edit_last_name);
        this.addressEditBillingAddress = ((EditText) view.findViewById(R.id.address_edit_billing_address));
        this.addressEditCity = (EditText) view.findViewById(R.id.address_edit_city);
        this.addressEditMobileNumber = (EditText) view.findViewById(R.id.address_edit_mobile_number);
        this.addressEditZipCode = ((EditText) view.findViewById(R.id.address_edit_zip_code));
    }
}
