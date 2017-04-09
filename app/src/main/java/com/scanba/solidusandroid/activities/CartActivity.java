package com.scanba.solidusandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.checkout.EmailStepActivity;
import com.scanba.solidusandroid.adapters.CartItemsAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.components.ListItemDecorator;
import com.scanba.solidusandroid.models.Order;

import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TextView mCartEmptyMessage;
    private FrameLayout mCheckout;
    private Dao<Order, Integer> orderDao;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();
        initDatabase();
        initAPI();
        fetchOrder();
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_items);
        mCheckout = (FrameLayout) findViewById(R.id.checkout);
        mCartEmptyMessage = (TextView) findViewById(R.id.cart_empty_message);
        progressDialog = new CustomProgressDialog(this, "Loading...");
    }

    private void fetchOrder() {
        try {
            orderDao = databaseHelper.getOrderDao();
            Order order = Order.first(orderDao);
            if(order != null) {
                progressDialog.show();
                Call<Order> container = apiService.getOrder(order.getNumber(), ApiClient.API_KEY);
                container.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        setupCart(order);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
            else {
                mCartEmptyMessage.setVisibility(TextView.VISIBLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setupCart(Order order) {
        if(order.getLineItems().size() == 0)
            cartIsEmpty();
        else {
            cartIsNotEmpty();
            setupCartItems(order);
        }
    }

    private void cartIsEmpty() {
        mCartEmptyMessage.setVisibility(TextView.VISIBLE);
    }

    private void cartIsNotEmpty() {
        mCheckout.setVisibility(FrameLayout.VISIBLE);
    }

    private void setupCartItems(Order order) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartItemsAdapter adapter = new CartItemsAdapter(this, order.getLineItems());
        mRecyclerView.setAdapter(adapter);
        ListItemDecorator decorator = new ListItemDecorator(5);
        mRecyclerView.addItemDecoration(decorator);
    }

    public void checkout(View view) {
        Intent intent = new Intent(this, EmailStepActivity.class);
        startActivity(intent);
    }
}
