package com.scanba.solidusandroid.activities.checkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.BaseActivity;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.components.CustomEditText;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.models.Address;
import com.scanba.solidusandroid.models.Order;

import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailStepActivity extends BaseActivity {

    private CustomEditText emailEditText;
    private Dao<Order, Integer> orderDao;
    private Dao<Address, Integer> addressDao;
    private Order order;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_step);
        initToolbar();
        initDatabase();
        initAPI();
        initUI();
        initOrder();
    }

    @Override
    protected void initDatabase() {
        super.initDatabase();
        try {
            orderDao = databaseHelper.getOrderDao();
            addressDao = databaseHelper.getAddressDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initUI() {
        emailEditText = (CustomEditText) findViewById(R.id.email);
    }

    private void initOrder() {
        order = Order.first(orderDao);
        emailEditText.setEditText(order.email);
    }

    public void updateEmail(View view) {
        String email = emailEditText.getEditText();
        order.email = email;
        try {
            orderDao.update(order);
            progressDialog = new CustomProgressDialog(this, "Please wait...");
            progressDialog.show();
            Call<Order> call = apiService.updateOrderEmail(order.getNumber(), order.email, ApiClient.API_KEY);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    goToAddressStep();
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToAddressStep() {
        Call<Order> call = apiService.advanceOrderToNextStep(order.getNumber(), ApiClient.API_KEY);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                Address shippingAddress = order.getShipAddress();
                if(shippingAddress != null && Address.first(addressDao) == null) {
                    try {
                        addressDao.create(shippingAddress);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(EmailStepActivity.this, AddressStepActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
