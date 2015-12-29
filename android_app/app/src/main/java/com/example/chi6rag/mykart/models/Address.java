package com.example.chi6rag.mykart.models;

import com.google.gson.Gson;

public class Address {
    public static final Integer INDIA_COUNTRY_ID = 105;
    String firstname;
    String lastname;
    String address1;
    String city;
    String phone;
    String zipcode;
    Integer stateId;
    Integer countryId;

    public Address(String firstname, String lastname, String address1, String city,
                    String phone, String zipcode, Integer stateId, Integer countryId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address1 = address1;
        this.city = city;
        this.phone = phone;
        this.zipcode = zipcode;
        this.stateId = stateId;
        this.countryId = countryId;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
