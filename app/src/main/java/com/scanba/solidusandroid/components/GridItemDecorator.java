package com.scanba.solidusandroid.components;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecorator extends RecyclerView.ItemDecoration {
    private int space;

    public GridItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0 || position == 1)
            outRect.top = space;
        if(position % 2 != 0)
            outRect.right = space;
        outRect.bottom = space;
        outRect.left = space;

    }
}
