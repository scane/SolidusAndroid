package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.CartItem;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private Context mContext;
    private List<CartItem> mCartItems;
    private LayoutInflater layoutInflater;

    public CartItemsAdapter(Context context, List<CartItem> cartItems) {
        mContext = context;
        mCartItems = cartItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
        holder.mName.setText(cartItem.name);
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
