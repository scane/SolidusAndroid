package com.scanba.solidusandroid.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.api.ApiClient;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.product.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    List<Product> products;
    LayoutInflater layoutInflater;
    Context context;

    public ProductsAdapter(Context context) {
        this.context = context;
        products = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.products_list_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        List<Image> images = product.getMasterVariant().getImages();
        if(images.size() > 0)
            Picasso.with(context).load(ApiClient.BASE_URL + images.get(0).getMiniURL()).into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productStock.setText(product.getQuantityInStock() + " in stock");
        holder.productPrice.setText(product.getDisplayPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productStock;
        private TextView productPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.products_list_view_item_img);
            productName = (TextView) itemView.findViewById(R.id.products_list_view_item_name);
            productStock = (TextView) itemView.findViewById(R.id.products_list_view_item_stock);
            productPrice = (TextView) itemView.findViewById(R.id.products_list_view_item_price);
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
