package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.order.OrderLineItem;
import com.scanba.solidusandroid.models.product.ProductVariant;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private Context mContext;
    private List<OrderLineItem> mOrderLineItems;
    private LayoutInflater layoutInflater;

    public CartItemsAdapter(Context context, List<OrderLineItem> orderLineItems) {
        mContext = context;
        mOrderLineItems = orderLineItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderLineItem item = mOrderLineItems.get(position);
        ProductVariant variant = item.getVariant();
        holder.mName.setText(variant.getName());
        holder.mVariantInfo.setText(variant.getOptionsText());
        holder.mDisplayPrice.setText(variant.getDisplayPrice());
    }

    @Override
    public int getItemCount() {
        return mOrderLineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mVariantInfo;
        private TextView mDisplayPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mVariantInfo = (TextView) itemView.findViewById(R.id.variant_info);
            mDisplayPrice = (TextView) itemView.findViewById(R.id.display_price);
        }
    }
}
