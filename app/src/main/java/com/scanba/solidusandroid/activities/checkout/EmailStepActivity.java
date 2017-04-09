package com.scanba.solidusandroid.activities.checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.BaseActivity;
import com.scanba.solidusandroid.components.CustomEditText;
import com.scanba.solidusandroid.models.Order;

import java.sql.SQLException;

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
            mEmail.setEditText(mOrder.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
