package com.scanba.solidusandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.listeners.ProductOptionValueChangeListener;
import com.scanba.solidusandroid.models.Product;
import com.scanba.solidusandroid.models.product.ProductOptionType;
import com.scanba.solidusandroid.models.product.ProductVariant;

import java.util.ArrayList;
import java.util.List;

public class ProductOptionTypesAdapter extends RecyclerView.Adapter<ProductOptionTypesAdapter.ViewHolder> {

    private Context mContext;
    private boolean mHasVariants;
    private List<ProductOptionType> mProductOptionTypes;
    private List<ProductVariant> mProductVariants;
    private LayoutInflater layoutInflater;
    private ProductOptionValueChangeListener mOptionValueChangeListener;

    public ProductOptionTypesAdapter(Context context, Product product, ProductOptionValueChangeListener optionValueChangeListener) {
        mContext = context;
        mHasVariants = product.isHasVariants();
        mProductOptionTypes = product.getOptionTypes();
        mProductVariants = product.getVariants();
        layoutInflater = LayoutInflater.from(mContext);
        mOptionValueChangeListener = optionValueChangeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_option_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductOptionType optionType = mProductOptionTypes.get(position);
        holder.optionTypeName.setText(optionType.getPresentation());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getOptionValues(optionType)) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0) //disable first item. First item will be used for hint
                    return false;
                else
                    return true;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.optionTypeValues.setAdapter(adapter);
    }

    private ArrayList<String> getOptionValues(ProductOptionType optionType) {
        ArrayList<String> optionValues = new ArrayList<>();
        for(ProductVariant productVariant : mProductVariants) {
            for(ProductVariant.OptionValue optionValue : productVariant.getOptionValues()) {
                if(optionValue.getOptionTypeId() == optionType.getId())
                    if(!optionValues.contains(optionValue.getPresentation()))
                        optionValues.add(optionValue.getPresentation());
            }
        }
        optionValues.add(0, "Select " + optionType.getPresentation());
        return optionValues;
    }

    @Override
    public int getItemCount() {
        if(mHasVariants)
            return mProductOptionTypes.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        private TextView optionTypeName;
        private Spinner optionTypeValues;
        public ViewHolder(View itemView) {
            super(itemView);
            optionTypeName = (TextView) itemView.findViewById(R.id.option_type_name);
            optionTypeValues = (Spinner) itemView.findViewById(R.id.option_type_values);
            optionTypeValues.setOnItemSelectedListener(this);

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position > 0) {
                ProductOptionType optionType = mProductOptionTypes.get(getAdapterPosition());
                mOptionValueChangeListener.onOptionValueChange(optionType.getId(), parent.getItemAtPosition(position).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
