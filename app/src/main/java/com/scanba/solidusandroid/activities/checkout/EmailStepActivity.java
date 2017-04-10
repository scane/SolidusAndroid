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
import com.scanba.solidusandroid.models.Order;

import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailStepActivity extends BaseActivity {

    CustomEditText mEmail;
    Dao<Order, Integer> mOrderDao;
    Order mOrder;

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

    private void initUI() {
        mEmail = (CustomEditText) findViewById(R.id.email);
        mEmail.init("Email ID");
    }

    private void initOrder() {
        try {
            mOrderDao = databaseHelper.getOrderDao();
            mOrder = Order.first(mOrderDao);
            mEmail.setEditText(mOrder.email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(View view) {
        String email = mEmail.getEditText();
        mOrder.email = email;
        try {
            mOrderDao.update(mOrder);
            Call<Order> call = apiService.updateOrderEmail(mOrder.getNumber(), mOrder.email, ApiClient.API_KEY);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    goToAddressStep();
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToAddressStep() {
        Call<Order> call = apiService.advanceOrderToNextStep(mOrder.getNumber(), ApiClient.API_KEY);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Intent intent = new Intent(EmailStepActivity.this, AddressStepActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
}
