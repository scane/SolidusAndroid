package com.scanba.solidusandroid.components;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.adapters.ProductImagesPagerAdapter;
import com.scanba.solidusandroid.adapters.ProductThumbnailImagesAdapter;
import com.scanba.solidusandroid.models.product.ProductImage;

import java.util.List;

public class ProductImagesComponent extends FrameLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;


    public ProductImagesComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductImagesComponent(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_images, null);
        mViewPager = (ViewPager) view.findViewById(R.id.product_images);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.product_thumbnail_images);
        addView(view);
    }

    public void init(List<ProductImage> productImages) {
        ProductImagesPagerAdapter adapter = new ProductImagesPagerAdapter(mContext, productImages);
        mViewPager.setAdapter(adapter);
        if(productImages.size() > 1) {
            mRecyclerView.setVisibility(RecyclerView.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            ProductThumbnailImagesAdapter thumbnailImagesAdapter = new ProductThumbnailImagesAdapter(mContext, productImages);
            mRecyclerView.setAdapter(thumbnailImagesAdapter);
        }
    }
}
