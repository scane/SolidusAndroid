package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.product.ProductImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductThumbnailImagesAdapter extends RecyclerView.Adapter<ProductThumbnailImagesAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<ProductImage> mProductImages;

    public ProductThumbnailImagesAdapter(Context context, List<ProductImage> productImages) {
        mContext = context;
        mProductImages = productImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.product_thumbnail_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductImage image = mProductImages.get(position);
        Picasso.with(mContext).load(image.getSmallURL()).into(holder.productThumbnailImage);
    }

    @Override
    public int getItemCount() {
        return mProductImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productThumbnailImage;
        public ViewHolder(View itemView) {
            super(itemView);
            productThumbnailImage = (ImageView) itemView;
        }
    }
}
