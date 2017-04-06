package com.scanba.solidusandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.checkout.EmailStepActivity;
import com.scanba.solidusandroid.adapters.CartItemsAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.components.ListItemDecorator;
import com.scanba.solidusandroid.models.CartItem;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.containers.ProductsContainer;
import com.scanba.solidusandroid.models.product.ProductVariant;
import com.scanba.solidusandroid.sqlite.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mCartEmptyMessage;
    private FrameLayout mCheckout;
    private List<CartItem> mCartItems;
    Dao<CartItem, Integer> cartItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();
        fetchProducts();
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_items);
        mCheckout = (FrameLayout) findViewById(R.id.checkout);
        mCartEmptyMessage = (TextView) findViewById(R.id.cart_empty_message);
    }

    private void fetchProducts() {
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        try {
            cartItemDao = databaseHelper.getCartItemDao();
            mCartItems = cartItemDao.queryForAll();
            if(mCartItems.size() > 0) {
                String ids = "";
                for(CartItem cartItem : mCartItems) {
                    ids += cartItem.productId + ",";
                }
                final CustomProgressDialog dialog = new CustomProgressDialog(this, "Loading...");
                dialog.show();
                SolidusInterface apiService = ApiClient.getClient().create(SolidusInterface.class);
                Call<ProductsContainer> container = apiService.getProducts(1, ids, ApiClient.API_KEY);
                container.enqueue(new Callback<ProductsContainer>() {
                    @Override
                    public void onResponse(Call<ProductsContainer> call, Response<ProductsContainer> response) {
                        List<Product> products = response.body().getProducts();
                        setupCart(products);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProductsContainer> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
            else {
                mCartEmptyMessage.setVisibility(TextView.VISIBLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setupCart(List<Product> products) {
        if(products.isEmpty())
            cartIsEmpty();
        else {
            ListIterator<CartItem> itr = mCartItems.listIterator();
            CartItem cartItem;
            int productsCount, variantsCount;
            while(itr.hasNext()) {
                productsCount = 0;
                cartItem = itr.next();
                for(Product product : products) {
                    if(product.getId() == cartItem.productId) {
                        cartItem.name = product.getName();
                        cartItem.displayPrice = product.getDisplayPrice();
                        if(product.isHasVariants()) {
                            variantsCount = 0;
                            for(ProductVariant variant : product.getVariants()) {
                                if(variant.getId() == cartItem.variantId) {
                                    cartItem.displayPrice = variant.getDisplayPrice();
                                    cartItem.variantInfo = variant.toString();
                                    break;
                                }
                                variantsCount++;
                            }
                            if(product.getVariants().size() == variantsCount) {
                                cartItem.destroy(cartItemDao);
                                itr.remove();
                            }
                        }
                        break;
                    }
                    productsCount++;
                }
                if(productsCount == products.size()) {
                    cartItem.destroy(cartItemDao);
                    itr.remove();
                }
            }
            if(mCartItems.size() > 0) {
                cartIsNotEmpty();
                setupCartItems();
            }
            else
                cartIsEmpty();
        }
    }

    private void cartIsEmpty() {
        mCartEmptyMessage.setVisibility(TextView.VISIBLE);
    }

    private void cartIsNotEmpty() {
        mCheckout.setVisibility(FrameLayout.VISIBLE);
    }

    private void setupCartItems() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartItemsAdapter adapter = new CartItemsAdapter(this, mCartItems);
        mRecyclerView.setAdapter(adapter);
        ListItemDecorator decorator = new ListItemDecorator(5);
        mRecyclerView.addItemDecoration(decorator);
    }

    public void checkout(View view) {
        Intent intent = new Intent(this, EmailStepActivity.class);
        startActivity(intent);
    }
}
