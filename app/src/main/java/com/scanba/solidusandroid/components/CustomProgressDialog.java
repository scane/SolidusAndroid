package com.scanba.solidusandroid.components;


import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context, String message) {
        super(context);
        setIndeterminate(true);
        setCancelable(false);
        setMessage(message);
    }
}
