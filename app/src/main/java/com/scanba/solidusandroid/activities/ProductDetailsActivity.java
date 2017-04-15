package com.scanba.solidusandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.adapters.ProductOptionTypesAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.components.ProductImagesComponent;
import com.scanba.solidusandroid.listeners.ProductOptionValueChangeListener;
import com.scanba.solidusandroid.models.Address;
import com.scanba.solidusandroid.models.Order;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.order.OrderLineItem;
import com.scanba.solidusandroid.models.product.ProductOptionType;
import com.scanba.solidusandroid.models.product.ProductVariant;

import java.sql.SQLException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends BaseActivity implements ProductOptionValueChangeListener {

    private ProductImagesComponent productImagesComponent;
    private TextView productName, productPrice, productDescription;
    private RelativeLayout productContainer;
    private RecyclerView productOptionTypes;
    private Product product;
    private int selectedVariantId;
    private HashMap<Integer, String> selectedOptionValues; // option_type_id: option_type_value
    private Dao<Order, Integer> orderDao;
    private CustomProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initDatabase();
        initAPI();
        initToolbar();
        getSupportActionBar().setTitle("");
        initUI();
        Intent intent = getIntent();
        fetchProduct(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initDatabase() {
        super.initDatabase();
        try {
            orderDao = databaseHelper.getOrderDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        productContainer = (RelativeLayout) findViewById(R.id.product_container);
        productImagesComponent = (ProductImagesComponent) findViewById(R.id.product_images_container);
        productName = (TextView) findViewById(R.id.product_name);
        productPrice = (TextView) findViewById(R.id.product_price);
        productDescription = (TextView) findViewById(R.id.product_description);
        productOptionTypes = (RecyclerView) findViewById(R.id.product_option_types);
    }

    private void fetchProduct(final int productId) {
        final CustomProgressDialog dialog = new CustomProgressDialog(this, "Loading...");
        dialog.show();
        Call<Product> call = apiService.getProduct(productId, ApiClient.API_KEY);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();

                ProductVariant masterVariant = product.getMasterVariant();
                selectedVariantId = masterVariant.getId();
                productImagesComponent.init(masterVariant.getImages());
                productName.setText(product.getName());
                productPrice.setText(product.getDisplayPrice());
                productDescription.setText(product.getDescription());
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
            selectedOptionValues = new HashMap<>();
            productOptionTypes.setVisibility(RecyclerView.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            productOptionTypes.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
            productOptionTypes.addItemDecoration(dividerItemDecoration);
            productOptionTypes.setAdapter(new ProductOptionTypesAdapter(this, product, this));
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
    public void onOptionValueChange(int optionTypeId, String optionValue) {
        selectedOptionValues.put(optionTypeId, optionValue);
        short j;
        if(allOptionsSelected()) { //No null values
            for(ProductVariant productVariant : product.getVariants()) {
                j = 0;
                for(ProductVariant.OptionValue productOptionValue : productVariant.getOptionValues()) {
                    if(selectedOptionValues.get(productOptionValue.getOptionTypeId()) != productOptionValue.getPresentation())
                        break;
                    j++;
                }
                if(j == productVariant.getOptionValues().size()) {
                    productPrice.setText(productVariant.getDisplayPrice());
                    selectedVariantId = productVariant.getId();
                    break;
                }
            }
        }
    }

    public boolean allOptionsSelected() {
        if(product.isHasVariants()) {
            short i = 0;
            int optionTypesCount = product.getOptionTypes().size();
            for(ProductOptionType optionType : product.getOptionTypes()) { //Null values check
                if (selectedOptionValues.get(optionType.getId()) == null)
                    break;
                i++;
            }
            if(i == optionTypesCount)
                return true;
            else
                return false;
        }
        else
            return true;
    }

    public void addToCart(View view) {
        if(allOptionsSelected()) {
            progressDialog = new CustomProgressDialog(this, "Please wait...");
            progressDialog.show();
            Order order = Order.first(orderDao);
            if(order == null) {
                Call<Order> call = apiService.createNewOrder(ApiClient.API_KEY);
                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        try {
                            orderDao.create(order);
                            addItemToOrder(order);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
            else {
                addItemToOrder(order);
            }

        }
        else
            Toast.makeText(this, "Please select all the options", Toast.LENGTH_SHORT).show();

    }

    public void addItemToOrder(Order order) {
        Call<OrderLineItem> call = apiService.addItemToOrder(order.getNumber(), selectedVariantId, 1, ApiClient.API_KEY);
        call.enqueue(new Callback<OrderLineItem>() {
            @Override
            public void onResponse(Call<OrderLineItem> call, Response<OrderLineItem> response) {
                progressDialog.dismiss();
                addToCartSuccess();
            }

            @Override
            public void onFailure(Call<OrderLineItem> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void addToCartSuccess() {
        Toast.makeText(this, "Successfully added product to cart", Toast.LENGTH_SHORT).show();
    }
}
