package com.scanba.solidusandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.adapters.ProductOptionTypesAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.components.ProductImagesComponent;
import com.scanba.solidusandroid.listeners.ProductOptionValueChangeListener;
import com.scanba.solidusandroid.models.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements ProductOptionValueChangeListener {

    private ProductImagesComponent mProductImagesComponent;
    private TextView mProductName, mProductPrice, mProductDescription;
    private Toolbar toolbar;
    private RelativeLayout productContainer;
    private RecyclerView mProductOptionTypes;
    private Product product;
    private String[] mSelectedOptionValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initUI();
        Intent intent = getIntent();
        fetchProduct(1);
    }

    private void initUI() {
        productContainer = (RelativeLayout) findViewById(R.id.product_container);
        mProductImagesComponent = (ProductImagesComponent) findViewById(R.id.product_images_container);
        mProductName = (TextView) findViewById(R.id.product_name);
        mProductPrice = (TextView) findViewById(R.id.product_price);
        mProductDescription = (TextView) findViewById(R.id.product_description);
        mProductOptionTypes = (RecyclerView) findViewById(R.id.product_option_types);
    }

    private void fetchProduct(final int productId) {
        final CustomProgressDialog dialog = new CustomProgressDialog(this, "Loading...");
        dialog.show();
        SolidusInterface apiService = ApiClient.getClient().create(SolidusInterface.class);
        Call<Product> call = apiService.getProduct(productId, ApiClient.API_KEY);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();

                mProductImagesComponent.init(product.getMasterVariant().getImages());
                mProductName.setText(product.getName());
                mProductPrice.setText(product.getDisplayPrice());
                mProductDescription.setText(product.getDescription());
                toolbar.setTitle(product.getName());
                setupProductOptionTypes();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                dialog.dismiss();
                handleError(productId);
            }
        });
    }

    private void setupProductOptionTypes() {
        if(product.isHasVariants()) {
            mSelectedOptionValues = new String[product.getOptionTypes().size()];
            mProductOptionTypes.setVisibility(RecyclerView.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mProductOptionTypes.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
            mProductOptionTypes.addItemDecoration(dividerItemDecoration);
            mProductOptionTypes.setAdapter(new ProductOptionTypesAdapter(this, product, this));
        }
    }

    private void handleError(final int productId) {
        Snackbar.make(productContainer, "Failed to load product", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchProduct(productId);
                    }
                }).setActionTextColor(Color.RED).show();
    }

    @Override
    public void onOptionValueChange(int optionIndex, String optionValue) {
        mSelectedOptionValues[optionIndex] = optionValue;
    }
}
