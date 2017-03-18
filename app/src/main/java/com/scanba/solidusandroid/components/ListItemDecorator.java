package com.scanba.solidusandroid.components;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ListItemDecorator extends RecyclerView.ItemDecoration {
    private int space;

    public ListItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == 0)
            outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;

    }
}
