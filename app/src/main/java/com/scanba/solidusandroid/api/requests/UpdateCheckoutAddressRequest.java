package com.scanba.solidusandroid.api.requests;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.Address;

public class UpdateCheckoutAddressRequest {

    @SerializedName("order")
    private Wrapper wrapper;

    public UpdateCheckoutAddressRequest(Address shippingAddress, Address billingAddress) {
        wrapper = new Wrapper(shippingAddress, billingAddress);
    }

    class Wrapper {
        @SerializedName("ship_address_attributes")
        private Address shippingAddress;

        @SerializedName("bill_address_attributes")
        private Address billingAddress;

        Wrapper(Address shippingAddress, Address billingAddress) {
            this.shippingAddress = shippingAddress;
            this.billingAddress = billingAddress;

        }
    }
}
