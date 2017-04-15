package com.scanba.solidusandroid.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.locale.State;

import java.util.List;

public class CustomSpinner extends LinearLayout {

    private TextView mTextView;
    private Spinner mSpinner;

    public CustomSpinner(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(context, R.layout.custom_spinner, this);

        mTextView = (TextView) view.findViewById(R.id.label);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyleAttr, 0);
        mTextView.setText(a.getString(R.styleable.CustomEditText_label));
        a.recycle();
    }

    public void init(List<String> list, int selectedPosition) {
        list.add(0, "Select " + mTextView.getText());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0) //disable first item. First item will be used for hint
                    return false;
                else
                    return true;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(selectedPosition + 1);

    }

    public int getSelectedItemPosition() {
        return mSpinner.getSelectedItemPosition() - 1;
    }
}
