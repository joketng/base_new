package com.handmark.pulltorefresh.library.divider;/**
 * Created by jointem on 2016/4/1.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * @author THC
 * @Title: HorizontalItemDecoration
 * @Package com.jointem.zyb.view.widget.recyclerview.divider
 * @Description:
 * @date 2016/4/1 13:58
 */
class HorizontalItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = {android.R.attr.listDivider};

    private HashMap<Integer, Drawable> mDividerViewTypeMap;
    private Drawable mFirstDrawable;
    private Drawable mLastDrawable;

    private HorizontalItemDecoration(HashMap<Integer, Drawable> dividerViewTypeMap,
                                     Drawable firstDrawable, Drawable lastDrawable) {
        mDividerViewTypeMap = dividerViewTypeMap;
        mFirstDrawable = firstDrawable;
        mLastDrawable = lastDrawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        boolean isReverse = ((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout();

        // last position
        if (isLastPosition(view, parent)) {
            if (mLastDrawable != null) {
                if (isReverse) {
                    outRect.left = mLastDrawable.getIntrinsicWidth();
                } else {
                    outRect.right = mLastDrawable.getIntrinsicWidth();
                }
            }
            return;
        }

        // specific view type
        int childType = parent.getLayoutManager().getItemViewType(view);
        Drawable drawable = mDividerViewTypeMap.get(childType);
        if (drawable != null) {
            if (isReverse) {
                outRect.left = drawable.getIntrinsicWidth();
            } else {
                outRect.right = drawable.getIntrinsicWidth();
            }
        }

        // first position
        if (isFirstPosition(view, parent)) {
            if (mFirstDrawable != null) {
                if (isReverse) {
                    outRect.right = mFirstDrawable.getIntrinsicWidth();
                } else {
                    outRect.left = mFirstDrawable.getIntrinsicWidth();
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        boolean isReverse = ((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout();

        int childCount = parent.getChildCount();
        for (int i = 0; i <= childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int childViewType = parent.getLayoutManager().getItemViewType(child);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // last position
            if (isLastPosition(child, parent)) {
                if (mLastDrawable != null) {
                    int left, right;
                    if (isReverse) {
                        right = child.getLeft() - params.leftMargin;
                        left = right - mLastDrawable.getIntrinsicWidth();
                    } else {
                        left = child.getRight() + params.rightMargin;
                        right = left + mLastDrawable.getIntrinsicWidth();
                    }
                    mLastDrawable.setBounds(left, top, right, bottom);
                    mLastDrawable.draw(c);
                }
                return;
            }

            // specific view type
            Drawable drawable = mDividerViewTypeMap.get(childViewType);
            if (drawable != null) {
                int left, right;
                if (isReverse) {
                    right = child.getLeft() - params.leftMargin;
                    left = right - drawable.getIntrinsicWidth();
                } else {
                    left = child.getRight() + params.rightMargin;
                    right = left + drawable.getIntrinsicWidth();
                }
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            }

            // first position
            if (isFirstPosition(child, parent)) {
                if (mFirstDrawable != null) {
                    int left, right;
                    if (isReverse) {
                        left = child.getRight() + params.rightMargin;
                        right = left + mFirstDrawable.getIntrinsicWidth();
                    } else {
                        right = child.getLeft() - params.leftMargin;
                        left = right - mFirstDrawable.getIntrinsicWidth();
                    }
                    mFirstDrawable.setBounds(left, top, right, bottom);
                    mFirstDrawable.draw(c);
                }
            }
        }
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

        public HorizontalItemDecoration create() {
            return new HorizontalItemDecoration(mDividerViewTypeMap, mFirstDrawable,
                    mLastDrawable);
        }
    }
}
