package com.jointem.base.view.recydivider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author THC
 * @Title: SpacesItemDecoration
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/3/17 8:44
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int left;
    private int numColumns;

    public SpacesItemDecoration(int space, int left, int numColumns) {
        this.space = space;
        this.left = left;
        this.numColumns = numColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.left = left;
        outRect.right = left;
        outRect.bottom = space;
    }

}
