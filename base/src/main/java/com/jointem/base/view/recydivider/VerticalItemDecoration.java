package com.jointem.base.view.recydivider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * @author THC
 * @Title: VerticalItemDecoration
 * @Package com.jointem.zyb.view.widget.recyclerview.divider
 * @Description:
 * @date 2016/4/1 13:57
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {android.R.attr.listDivider};

    private HashMap<Integer, Drawable> mDividerViewTypeMap;
    private Drawable mFirstDrawable;
    private Drawable mLastDrawable;
    private int offsetL, offsetT, offsetR, offsetB;

    private VerticalItemDecoration(HashMap<Integer, Drawable> dividerViewTypeMap,
                                   Drawable firstDrawable, Drawable lastDrawable, int offsetL, int offsetT, int offsetR, int offsetB) {
        mDividerViewTypeMap = dividerViewTypeMap;
        mFirstDrawable = firstDrawable;
        mLastDrawable = lastDrawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        // last position
        if (isLastPosition(view, parent)) {
            if (mLastDrawable != null) {
                outRect.bottom = mLastDrawable.getIntrinsicHeight();
            }
            return;
        }

        // specific view type
        int childType = parent.getLayoutManager().getItemViewType(view);
        Drawable drawable = mDividerViewTypeMap.get(childType);
        if (drawable != null) {
            outRect.bottom = drawable.getIntrinsicHeight();
        }

        // first position
        if (isFirstPosition(view, parent)) {
            if (mFirstDrawable != null) {
                outRect.top = mFirstDrawable.getIntrinsicHeight();
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i <= childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int childViewType = parent.getLayoutManager().getItemViewType(child);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // last position
            if (isLastPosition(child, parent)) {
                if (mLastDrawable != null) {
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mLastDrawable.getIntrinsicHeight();
                    // mLastDrawable.setBounds(left, top, right, bottom);
                    setDrawableBoundWithOffset(mLastDrawable, left, top, right, bottom);
                    mLastDrawable.draw(c);
                }
                return;
            }

            // specific view type
            Drawable drawable = mDividerViewTypeMap.get(childViewType);
            if (drawable != null) {
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + drawable.getIntrinsicHeight();
                // drawable.setBounds(left, top, right, bottom);
                setDrawableBoundWithOffset(drawable, left, top, right, bottom);
                drawable.draw(c);
            }

            // first position
            if (isFirstPosition(child, parent)) {
                if (mFirstDrawable != null) {
                    int bottom = child.getTop() - params.topMargin;
                    int top = bottom - mFirstDrawable.getIntrinsicHeight();
                    //   mFirstDrawable.setBounds(left, top, right, bottom);
                    setDrawableBoundWithOffset(mFirstDrawable, left, top, right, bottom);
                    mFirstDrawable.draw(c);
                }
            }
        }
    }

    private void setDrawableBoundWithOffset(Drawable drawable, int l, int t, int r, int b) {
        drawable.setBounds(l - offsetL, t - offsetT, r - offsetR, b - offsetB);
    }

    private boolean isFirstPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == 0;
    }

    private boolean isLastPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1;
    }

    public static class Builder {

        private Context mContext;
        private HashMap<Integer, Drawable> mDividerViewTypeMap = new HashMap<>();
        private Drawable mFirstDrawable;
        private Drawable mLastDrawable;
        private int offsetL, offsetT, offsetR, offsetB;

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

        public Builder offset(int l, int t, int r, int b) {
            offsetB = b;
            offsetL = l;
            offsetR = r;
            offsetT = t;
            return this;
        }

        public Builder first(@DrawableRes int drawableResId) {
            first(ContextCompat.getDrawable(mContext, drawableResId));
            return this;
        }

        public Builder first(Drawable drawable) {
            mFirstDrawable = drawable;
            return this;
        }

        public Builder last(@DrawableRes int drawableResId) {
            last(ContextCompat.getDrawable(mContext, drawableResId));
            return this;
        }

        public Builder last(Drawable drawable) {
            mLastDrawable = drawable;
            return this;
        }

        public VerticalItemDecoration create() {
            return new VerticalItemDecoration(mDividerViewTypeMap, mFirstDrawable, mLastDrawable,
                    offsetL, offsetT, offsetR, offsetB);
        }

    }
}
