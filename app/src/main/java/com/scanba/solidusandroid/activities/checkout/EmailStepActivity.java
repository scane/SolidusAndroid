package com.scanba.solidusandroid.activities.checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.components.CustomEditText;

public class EmailStepActivity extends AppCompatActivity {

    CustomEditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_step);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();
    }

    private void initUI() {
        email = (CustomEditText) findViewById(R.id.email);
        email.init("Email ID");
    }
}
