package com.scanba.solidusandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.adapters.ProductsAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = (RecyclerView) findViewById(R.id.product_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productsAdapter = new ProductsAdapter(this);
        recyclerView.setAdapter(productsAdapter);
        fetchProducts();

    }

    private void fetchProducts() {
        SolidusInterface apiService = ApiClient.getClient().create(SolidusInterface.class);
        Call<Products> call = apiService.getProducts(ApiClient.API_KEY);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                List<Product> products = response.body().getProducts();
                productsAdapter.setProducts(products);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Log.d("TEST", "TEST");
            }
        });
    }
}
