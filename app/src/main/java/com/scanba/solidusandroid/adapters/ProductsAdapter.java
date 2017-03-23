package com.scanba.solidusandroid.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.ProductDetailsActivity;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.enums.ViewMode;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.product.ProductImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewMode viewMode;

    public ProductsAdapter(Context context) {
        this.context = context;
        products = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewMode viewMode = ViewMode.values()[viewType];
        switch (viewMode) {
            case LIST:
                view = layoutInflater.inflate(R.layout.products_list_view_item, parent, false);
                break;
            default:
                view = layoutInflater.inflate(R.layout.products_grid_view_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        List<ProductImage> images = product.getMasterVariant().getImages();
        if(images.size() > 0)
            Picasso.with(context).load(images.get(0).getSmallURL())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productStock.setText(product.getQuantityInStock() + " in stock");
        holder.productPrice.setText(product.getDisplayPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewMode.ordinal();
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void addProducts(List<Product> products) {
        int oldCount = getItemCount();
        int newProductsCount = products.size();
        this.products.addAll(products);
        notifyItemRangeInserted(oldCount, newProductsCount);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView productImage;
        private TextView productName;
        private TextView productStock;
        private TextView productPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.products_item_img);
            productName = (TextView) itemView.findViewById(R.id.products_item_name);
            productStock = (TextView) itemView.findViewById(R.id.products_item_stock);
            productPrice = (TextView) itemView.findViewById(R.id.products_item_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product product = products.get(getAdapterPosition());
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
