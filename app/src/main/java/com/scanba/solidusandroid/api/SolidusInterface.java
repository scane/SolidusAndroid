package com.scanba.solidusandroid.api;

import com.scanba.solidusandroid.models.Order;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.containers.ProductsContainer;
import com.scanba.solidusandroid.models.containers.TaxonomiesContainer;
import com.scanba.solidusandroid.models.order.OrderLineItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SolidusInterface {

    @GET("taxons/products")
    Call<ProductsContainer> getProductsByTaxon(@Query("id") int id, @Query("page") int page, @Query("token") String token);

    @GET("products")
    Call<ProductsContainer> getProducts(@Query("page") int page, @Query("ids") String ids, @Query("token") String token);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id, @Query("token") String token);

    @GET("taxonomies")
    Call<TaxonomiesContainer> getTaxonomies(@Query("token") String token);

    @POST("orders")
    Call<Order> createNewOrder(@Query("token") String token);

    @GET("orders/{number}")
    Call<Order> getOrder(@Path("number") String number, @Query("token") String token);

    @POST("orders/{number}/line_items")
    Call<OrderLineItem> addItemToOrder(@Path("number") String number,
                                       @Query("line_item[variant_id]") int variantId,
                                       @Query("line_item[quantity]") int quantity,
                                       @Query("token") String token);

    @PUT("orders/{number}")
    Call<Order> updateOrderEmail(@Path("number") String number,
                                 @Query("order[email]") String email,
                                 @Query("token") String token);

    @PUT("checkouts/{number}/next")
    Call<Order> advanceOrderToNextStep(@Path("number") String number, @Query("token") String token);
}
