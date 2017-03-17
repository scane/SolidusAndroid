package com.scanba.solidusandroid.api;


import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.containers.ProductsContainer;
import com.scanba.solidusandroid.models.containers.TaxonomiesContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SolidusInterface {

    @GET("taxons/products")
    Call<ProductsContainer> getProductsByTaxon(@Query("id") int id, @Query("page") int page, @Query("token") String token);

    @GET("products")
    Call<ProductsContainer> getProducts(@Query("page") int page, @Query("token") String token);

    @GET("product/{id}")
    Call<Product> getProduct(@Path("id") int id, @Query("token") String token);

    @GET("taxonomies")
    Call<TaxonomiesContainer> getTaxonomies(@Query("token") String token);
}
