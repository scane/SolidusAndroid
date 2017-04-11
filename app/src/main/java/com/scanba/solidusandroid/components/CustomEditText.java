package com.scanba.solidusandroid.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scanba.solidusandroid.R;

public class CustomEditText extends LinearLayout {

    private TextView mTextView;
    private EditText mEditText;
    private int normalColor, highlightColor;

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(context, R.layout.custom_edit_text, this);

        mTextView = (TextView) view.findViewById(R.id.label);
        mEditText = (EditText) view.findViewById(R.id.edit_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyleAttr, 0);
        mTextView.setText(a.getString(R.styleable.CustomEditText_label));
        a.recycle();

        normalColor = ContextCompat.getColor(context, R.color.customEditTextLabelColor);
        highlightColor = ContextCompat.getColor(context, R.color.customEditTextLabelHighlightColor);

        mEditText.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    mTextView.setTextColor(highlightColor);
                else
                    mTextView.setTextColor(normalColor);
            }
        });

    }

    public void init(String label) {
        mTextView.setText(label);
    }

    public void setEditText(String value) {
        mEditText.setText(value);
    }

    public String getEditText() {
        return mEditText.getText().toString();
    }
}
