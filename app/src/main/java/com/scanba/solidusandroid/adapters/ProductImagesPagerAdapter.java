package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.product.ProductImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductImagesPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ProductImage> mProductImages;
    LayoutInflater mLayoutInflater;

    public ProductImagesPagerAdapter(Context context, List<ProductImage> productImages) {
        mContext = context;
        mProductImages = productImages;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mProductImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ProductImage productImage = mProductImages.get(position);
        View view = mLayoutInflater.inflate(R.layout.product_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.product_image);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.product_image_loader);
        Picasso.with(mContext).load(productImage.getLargeURL()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(ProgressBar.GONE);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }
}
