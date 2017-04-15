package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.scanba.solidusandroid.api.ApiClient;

@DatabaseTable(tableName = "addresses")
public class Address extends Base {
    @SerializedName("id")
    @DatabaseField(id = true)
    public int id;

    @SerializedName("firstname")
    @DatabaseField
    private String firstName;

    @SerializedName("lastname")
    @DatabaseField
    private String lastName;

    @SerializedName("address1")
    @DatabaseField
    private String address1;

    @SerializedName("address2")
    @DatabaseField
    private String address2;

    @SerializedName("city")
    @DatabaseField
    private String city;

    @SerializedName("phone")
    @DatabaseField
    private String phone;

    @SerializedName("zipcode")
    @DatabaseField
    private String zipcode;

    @SerializedName("country_id")
    @DatabaseField
    private int countryId;

    @SerializedName("state_id")
    @DatabaseField
    private int stateId;

    public Address() {

    }

    public Address(String firstName, String lastName, String address1, String address2, String city, String phone, String zipcode, int stateId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.phone = phone;
        this.zipcode = zipcode;
        this.countryId = ApiClient.COUNTRY_ID;
        this.stateId = stateId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public int getStateId() {
        return stateId;
    }
}
