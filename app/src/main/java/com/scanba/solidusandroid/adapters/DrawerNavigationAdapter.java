package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.listeners.DrawerItemClickListener;
import com.scanba.solidusandroid.models.taxonomy.Taxon;

import java.util.List;

public class DrawerNavigationAdapter extends ArrayAdapter<Taxon> {
    List<Taxon> taxons;
    Dao<Taxon, Integer> taxonDao;
    DrawerItemClickListener listener;

    public DrawerNavigationAdapter(@NonNull Context context, @NonNull List<Taxon> taxons, Dao<Taxon, Integer> taxonDao, DrawerItemClickListener listener) {
        super(context, R.layout.drawer_navigation_item, taxons);
        this.taxons = taxons;
        this.taxonDao = taxonDao;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.drawer_navigation_item, parent, false);
        final Taxon taxon = taxons.get(position);
        TextView categoryName = (TextView) view.findViewById(R.id.category_name);
        categoryName.setText(taxon.getName());
        if(taxon.getParentId() == null && taxon.hasChildren(taxonDao)) {
            TextView hasChildren = (TextView) view.findViewById(R.id.has_children);
            hasChildren.setVisibility(TextView.VISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaxonClicked(taxon);
            }
        });
        return view;
    }
}
