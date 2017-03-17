package com.scanba.solidusandroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.ProductsActivity;
import com.scanba.solidusandroid.adapters.DrawerNavigationAdapter;
import com.scanba.solidusandroid.listeners.DrawerItemClickListener;
import com.scanba.solidusandroid.listeners.HostActivityDrawerInterface;
import com.scanba.solidusandroid.models.taxonomy.Taxon;
import com.scanba.solidusandroid.sqlite.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements DrawerItemClickListener {

    ViewFlipper viewFlipper;
    ListView listViewCategories;
    ListView listViewSubCategories;
    Dao<Taxon, Integer> taxonDao;
    HostActivityDrawerInterface listener;


    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        try {
            taxonDao = databaseHelper.getTaxonDao();
            listener = (HostActivityDrawerInterface) getActivity();
            initUI(view);
            setupDrawerNavigation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initUI(View view) {
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        listViewCategories = (ListView) view.findViewById(R.id.list_view_categories);
        listViewSubCategories = (ListView) view.findViewById(R.id.list_view_sub_categories);
    }

    private void setupDrawerNavigation() {
        List<Taxon> categories = Taxon.getTaxonsByParentID(null, taxonDao);
        DrawerNavigationAdapter adapter = new DrawerNavigationAdapter(getContext(), categories, taxonDao, this);
        listViewCategories.setAdapter(adapter);
    }

    @Override
    public void onTaxonClicked(Taxon taxon) {
        if(taxon.getDbId() == 0) //back button clicked
            viewFlipper.setDisplayedChild(0);
        else if(taxon.getParentId() == null && taxon.hasChildren(taxonDao)) { //parent category clicked
            List<Taxon> categories = Taxon.getTaxonsByParentID(taxon.getId(), taxonDao);
            Taxon backButton = new Taxon();
            backButton.setName("<");
            categories.add(0, backButton);
            DrawerNavigationAdapter adapter = new DrawerNavigationAdapter(getContext(), categories, taxonDao, this);
            listViewSubCategories.setAdapter(adapter);
            viewFlipper.setDisplayedChild(1);
        }
        else { // sub category clicked
            listener.onDrawerItemClicked(taxon);
        }
    }
}
