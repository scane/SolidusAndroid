package com.scanba.solidusandroid.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.api.SolidusInterface;
import com.scanba.solidusandroid.models.Taxonomy;
import com.scanba.solidusandroid.models.containers.StatesContainer;
import com.scanba.solidusandroid.models.containers.TaxonomiesContainer;
import com.scanba.solidusandroid.models.locale.State;
import com.scanba.solidusandroid.models.taxonomy.Taxon;
import com.scanba.solidusandroid.sqlite.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    Dao<Taxon, Integer> taxonDao;
    Dao<State, Integer> stateDao;
    CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initAPI();
        initDatabase();
        try {
            taxonDao = databaseHelper.getTaxonDao();
            stateDao = databaseHelper.getStateDao();
            container = (CoordinatorLayout) findViewById(R.id.activity_splash_container);
            getTaxonomies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getTaxonomies() {
        Call<TaxonomiesContainer> call = apiService.getTaxonomies(ApiClient.API_KEY);
        call.enqueue(new Callback<TaxonomiesContainer>() {
            @Override
            public void onResponse(Call<TaxonomiesContainer> call, Response<TaxonomiesContainer> response) {
                if(Taxon.deleteAll(taxonDao)) {
                    List<Taxonomy> taxonomies = response.body().getTaxonomies();
                    for(Taxonomy taxonomy : taxonomies) {
                        Taxon root = taxonomy.getRoot();
                        try {
                            taxonDao.create(root);
                            List<Taxon> taxons = root.getTaxons();
                            taxonDao.create(taxons);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    getStates();
                }
                else
                    handleError();
            }

            @Override
            public void onFailure(Call<TaxonomiesContainer> call, Throwable t) {
                handleError();
            }
        });

    }

    private void getStates() {
        try {
            if(stateDao.countOf() == 0) {
                Call<StatesContainer> call = apiService.getStates(ApiClient.COUNTRY_ID, ApiClient.API_KEY);
                call.enqueue(new Callback<StatesContainer>() {
                    @Override
                    public void onResponse(Call<StatesContainer> call, Response<StatesContainer> response) {
                        List<State> states = response.body().getStates();
                        try {
                            stateDao.create(states);
                            goToHome();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatesContainer> call, Throwable t) {

                    }
                });
            }
            else
                goToHome();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleError() {
        Snackbar snackbar = Snackbar.make(container, "The app failed to open.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTaxonomies();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private void goToHome() {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }
}
