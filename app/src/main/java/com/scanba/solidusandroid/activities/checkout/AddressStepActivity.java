package com.scanba.solidusandroid.activities.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.BaseActivity;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.requests.UpdateCheckoutAddressRequest;
import com.scanba.solidusandroid.components.CustomEditText;
import com.scanba.solidusandroid.components.CustomSpinner;
import com.scanba.solidusandroid.models.Address;
import com.scanba.solidusandroid.models.Order;
import com.scanba.solidusandroid.models.locale.State;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressStepActivity extends BaseActivity {

    private CustomEditText shippingFirstName, shippingLastName, shippingAddressLine1,
            shippingCity, shippingPhone, shippingZipcode;
    private CustomSpinner shippingState;
    private List<State> states;
    private Dao<State, Integer> stateDao;
    private Dao<Order, Integer> orderDao;
    private Dao<Address, Integer> addressDao;
    private Address persistedAddress;
    private CustomEditText shippingAddressLine2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_step);

        initToolbar();
        initDatabase();
        initAPI();
        initUI();
    }

    @Override
    protected void initDatabase() {
        super.initDatabase();
        try {
            stateDao = databaseHelper.getStateDao();
            orderDao = databaseHelper.getOrderDao();
            addressDao = databaseHelper.getAddressDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initUI() {
        shippingFirstName = (CustomEditText) findViewById(R.id.shipping_first_name);
        shippingLastName = (CustomEditText) findViewById(R.id.shipping_last_name);
        shippingAddressLine1 = (CustomEditText) findViewById(R.id.shipping_address_line1);
        shippingAddressLine2 = (CustomEditText) findViewById(R.id.shipping_address_line2);
        shippingCity = (CustomEditText) findViewById(R.id.shipping_city);
        shippingPhone = (CustomEditText) findViewById(R.id.shipping_phone);
        shippingZipcode = (CustomEditText) findViewById(R.id.shipping_zipcode);
        shippingState = (CustomSpinner) findViewById(R.id.shipping_state);
        persistedAddress = Address.first(addressDao);
        if(persistedAddress != null) {
            shippingFirstName.setEditText(persistedAddress.getFirstName());
            shippingLastName.setEditText(persistedAddress.getLastName());
            shippingAddressLine1.setEditText(persistedAddress.getAddress1());
            shippingAddressLine2.setEditText(persistedAddress.getAddress2());
            shippingCity.setEditText(persistedAddress.getCity());
            shippingPhone.setEditText(persistedAddress.getPhone());
            shippingZipcode.setEditText(persistedAddress.getZipcode());
            try {
                states = stateDao.queryForAll();
                int position = 0;
                for(State state : states) {
                    if(state.getId() == persistedAddress.getStateId())
                        break;
                    position++;
                }
                shippingState.init(getStates(), position);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            shippingState.init(getStates(), 0);

    }

    private List<String> getStates() {
        List<String> list = new ArrayList<>();
        for(State state : states)
            list.add(state.getName());
        return list;
    }

    public void updateAddress(View view) {
        Order order = Order.first(orderDao);
        final Address shippingAddress = new Address(
                shippingFirstName.getEditText(),
                shippingLastName.getEditText(),
                shippingAddressLine1.getEditText(),
                shippingAddressLine2.getEditText(),
                shippingCity.getEditText(),
                shippingPhone.getEditText(),
                shippingZipcode.getEditText(),
                states.get(shippingState.getSelectedItemPosition()).getId()
        );
        shippingAddress.id = persistedAddress.id;
        Call<Order> call = apiService.updateCheckoutAddress(order.getNumber(), new UpdateCheckoutAddressRequest(shippingAddress, shippingAddress), ApiClient.API_KEY);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                try {
                    addressDao.update(shippingAddress);
                    //Intent intent = new Intent(AddressStepActivity.this, DeliveryStepActivity.class);
                    //startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
}
