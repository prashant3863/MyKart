package com.example.chi6rag.mykart.models;

public class AddressForOrder {
    public static final Integer INDIA_COUNTRY_ID = 105;
    public static final Integer DELHI_STATE_ID = 1400;
    private static final String FIRSTNAME_KEY = "\"firstname\":";
    private static final String LASTNAME_KEY = "\"lastname\":";
    private static final String ADDRESS1_KEY = "\"address1\":";
    private static final String CITY_KEY = "\"city\":";
    private static final String PHONE_KEY = "\"phone\":";
    private static final String ZIPCODE_KEY = "\"zipcode\":";
    private static final String STATE_ID_KEY = "\"state_id\":";
    private static final String COUNTRY_ID_KEY = "\"country_id\":";
    private static final String COMMA = ", ";
    private static final String BRACKET_END = "}";
    private static final String BRACKET_START = "{";
    private static final String ORDER_KEY = "\"order\":";
    private static final String BILL_ADDRESS_ATTRIBUTES_KEY = "\"bill_address_attributes\":";
    private static final String SHIP_ADDRESS_ATTRIBUTES_KEY = "\"ship_address_attributes\":";
    private static final String EMAIL_ADDRESS_KEY = "\"email\":";

    String email;
    String firstname;
    String lastname;
    String address1;
    String city;
    String phone;
    String zipcode;
    Integer stateId;
    Integer countryId;

    public AddressForOrder(String email, String firstname, String lastname, String address1, String city,
                           String phone, String zipcode, Integer stateId, Integer countryId) {
        this.email = email;
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
        String jsonString = BRACKET_START +
                ORDER_KEY + BRACKET_START +
                BILL_ADDRESS_ATTRIBUTES_KEY + addressAttributes() + COMMA +
                SHIP_ADDRESS_ATTRIBUTES_KEY + addressAttributes() + COMMA +
                EMAIL_ADDRESS_KEY + addDoubleQuotesAtEnds(this.email) +
                BRACKET_END + BRACKET_END;
        return jsonString;
    }

    private String addressAttributes() {
        return BRACKET_START +
                FIRSTNAME_KEY + addDoubleQuotesAtEnds(this.firstname) + COMMA +
                LASTNAME_KEY + addDoubleQuotesAtEnds(this.lastname) + COMMA +
                ADDRESS1_KEY + addDoubleQuotesAtEnds(this.address1) + COMMA +
                CITY_KEY + addDoubleQuotesAtEnds(this.city) + COMMA +
                PHONE_KEY + addDoubleQuotesAtEnds(this.phone) + COMMA +
                ZIPCODE_KEY + addDoubleQuotesAtEnds(this.zipcode) + COMMA +
                STATE_ID_KEY + this.stateId + COMMA +
                COUNTRY_ID_KEY + this.countryId +
                BRACKET_END;
    }

    private String addDoubleQuotesAtEnds(String string) {
        return "\"" + string + "\"";
    }
}
