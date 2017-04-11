package com.scanba.solidusandroid.activities.checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.BaseActivity;

public class AddressStepActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_step);

        initToolbar();
    }
}
