package com.scanba.solidusandroid.api;


import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SolidusInterface {

    @GET("products")
    Call<Products> getProducts(@Query("token") String token);

    @GET("product/{id}")
    Call<Product> getProduct(@Path("id") int id, @Query("token") String token);
}
