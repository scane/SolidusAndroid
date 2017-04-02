package com.scanba.solidusandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.adapters.ProductsAdapter;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.components.CustomProgressDialog;
import com.scanba.solidusandroid.components.GridItemDecorator;
import com.scanba.solidusandroid.components.ListItemDecorator;
import com.scanba.solidusandroid.enums.ViewMode;
import com.scanba.solidusandroid.fragments.DrawerFragment;
import com.scanba.solidusandroid.listeners.EndlessRecyclerViewScrollListener;
import com.scanba.solidusandroid.listeners.HostActivityDrawerInterface;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.containers.ProductsContainer;
import com.scanba.solidusandroid.models.taxonomy.Taxon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements HostActivityDrawerInterface {
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private GridItemDecorator gridItemDecorator;
    private ListItemDecorator listItemDecorator;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CustomProgressDialog progressDialog;
    private ProgressBar moreProductsLoader;
    private SolidusInterface apiService;
    private Taxon currentTaxon;
    private ViewMode currentViewMode;
    private RelativeLayout productsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        apiService = ApiClient.getClient().create(SolidusInterface.class);
        productsContainer = (RelativeLayout) findViewById(R.id.products_container);

        initUI();
        setupRecyclerView();
        setupActionBar();
        setupNavigationDrawer();
        fetchProducts(null);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_mode:
                switch (currentViewMode) {
                    case LIST:
                        item.setIcon(R.drawable.list_view_icon);
                        setupGridView();
                        break;
                    case GRID:
                        item.setIcon(R.drawable.grid_view_icon);
                        setupListView();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        moreProductsLoader = (ProgressBar) findViewById(R.id.more_products_loader);
        progressDialog = new CustomProgressDialog(this, "Loading...");
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.product_list);
        productsAdapter = new ProductsAdapter(this);
        recyclerView.setAdapter(productsAdapter);
        gridItemDecorator = new GridItemDecorator(5);
        listItemDecorator = new ListItemDecorator(5);
        setupListView();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreProducts(page);
            }
        });
    }

    private void setupListView() {
        currentViewMode = ViewMode.LIST;
        productsAdapter.setViewMode(currentViewMode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.removeItemDecoration(gridItemDecorator);
        recyclerView.addItemDecoration(listItemDecorator);
        productsAdapter.notifyDataSetChanged();
    }

    private void setupGridView() {
        currentViewMode = ViewMode.GRID;
        productsAdapter.setViewMode(currentViewMode);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.removeItemDecoration(listItemDecorator);
        recyclerView.addItemDecoration(gridItemDecorator);
        productsAdapter.notifyDataSetChanged();
    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);
    }

    private void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerFragment drawerFragment = new DrawerFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.drawer, drawerFragment);
        ft.commit();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void fetchProducts(Taxon taxon) {
        currentTaxon = taxon;
        productsAdapter.setProducts(new ArrayList<Product>());
        Call<ProductsContainer> call;
        if(taxon == null) {
            toolbar.setTitle("Products");
            call = apiService.getProducts(1, null, ApiClient.API_KEY);
        }
        else {
            toolbar.setTitle(taxon.getPrettyName());
            call = apiService.getProductsByTaxon(taxon.getId(), 1, ApiClient.API_KEY);
        }
        progressDialog.show();
        call.enqueue(new Callback<ProductsContainer>() {
            @Override
            public void onResponse(Call<ProductsContainer> call, Response<ProductsContainer> response) {
                List<Product> products = response.body().getProducts();
                productsAdapter.setProducts(products);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductsContainer> call, Throwable t) {
                handleError();
            }
        });
    }

    private void handleError() {
        progressDialog.dismiss();
        Snackbar.make(productsContainer, "Failed to load products", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchProducts(currentTaxon);
                    }
                }).setActionTextColor(Color.RED).show();
    }

    private void loadMoreProducts(int page) {
        Call<ProductsContainer> call;
        if(currentTaxon == null) {
            call = apiService.getProducts(page, null, ApiClient.API_KEY);
        }
        else {
            call = apiService.getProductsByTaxon(currentTaxon.getId(), page, ApiClient.API_KEY);
        }
        moreProductsLoader.setVisibility(ProgressBar.VISIBLE);
        call.enqueue(new Callback<ProductsContainer>() {
            @Override
            public void onResponse(Call<ProductsContainer> call, Response<ProductsContainer> response) {
                List<Product> products = response.body().getProducts();
                if(products.size() > 0)
                    productsAdapter.addProducts(products);
                moreProductsLoader.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onFailure(Call<ProductsContainer> call, Throwable t) {
                moreProductsLoader.setVisibility(ProgressBar.GONE);
            }
        });
    }

    @Override
    public void onDrawerItemClicked(Taxon taxon) {
        drawerLayout.closeDrawers();
        fetchProducts(taxon);
    }
}
