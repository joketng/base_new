package com.jointem.base.view.recydivider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.HashMap;

/**
 * @author THC
 * @Title: GridItemDecoration
 * @Package com.yanzhenjie.recyclerview.swipe.divider
 * @Description:
 * @date 2016/9/28 9:37
 */
class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private HashMap<Integer, Drawable> mDividerViewTypeMap;

    private GridItemDecoration(HashMap<Integer, Drawable> dividerViewTypeMap) {
        this.mDividerViewTypeMap = dividerViewTypeMap;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数

        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int childViewType = parent.getLayoutManager().getItemViewType(child);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            Drawable drawable = mDividerViewTypeMap.get(0);
            if (drawable != null) {
                final int left = child.getRight() + params.rightMargin;
                final int right = left + drawable.getIntrinsicWidth();
                final int top = child.getTop() + params.topMargin;
                final int bottom = top + child.getHeight() - params.bottomMargin;
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int childViewType = parent.getLayoutManager().getItemViewType(child);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            Drawable drawable = mDividerViewTypeMap.get(0);
//            if(isLastRaw(parent, ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition(), getSpanCount(parent), childCount)){
//                return;
//            }
            if (drawable != null) {
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + drawable.getIntrinsicHeight();
                final int left = child.getLeft() + params.leftMargin;
                final int right = left + child.getWidth() - params.rightMargin;
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            }
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int childType = parent.getLayoutManager().getItemViewType(view);
        Drawable drawable = mDividerViewTypeMap.get(0);
        if (drawable != null) {
            if (isLastRaw(parent, ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), spanCount, childCount))// 如果是最后一行，则不需要绘制底部
            {
                outRect.right = drawable.getIntrinsicWidth();
                outRect.bottom = 0;
//                outRect.set(0, 0, drawable.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), spanCount, childCount))// 如果是最后一列，则不需要绘制右边
            {
                outRect.right = 0;
                outRect.bottom = drawable.getIntrinsicHeight();

//                outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
            } else {
                outRect.right = drawable.getIntrinsicWidth();
                outRect.bottom = drawable.getIntrinsicHeight();
//                outRect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            outRect.top = 0;
            outRect.left = 0;
        }
    }

    public static class Builder {
        private Context mContext;
        private HashMap<Integer, Drawable> mDividerViewTypeMap = new HashMap<>();

        Builder(Context context) {
            mContext = context;
        }

        public Builder type(int viewType) {
            final TypedArray a = mContext.obtainStyledAttributes(ATTRS);
            Drawable divider = a.getDrawable(0);
            type(viewType, divider);
            a.recycle();
            return this;
        }

        public Builder type(int viewType, @DrawableRes int drawableResId) {
            mDividerViewTypeMap.put(viewType, ContextCompat.getDrawable(mContext, drawableResId));
            return this;
        }

        public Builder type(int viewType, Drawable drawable) {
            mDividerViewTypeMap.put(viewType, drawable);
            return this;
        }

        public GridItemDecoration create() {
            return new GridItemDecoration(mDividerViewTypeMap);
        }
    }
}
