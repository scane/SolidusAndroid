package com.scanba.solidusandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.sqlite.DatabaseHelper;

public class BaseActivity extends AppCompatActivity {

    protected DatabaseHelper databaseHelper;
    protected SolidusInterface apiService;
    protected Toolbar toolbar;

    protected void initDatabase() {
        databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
    }

    protected void initAPI() {
        apiService = ApiClient.getClient().create(SolidusInterface.class);
    }

    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
