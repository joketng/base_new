package com.jointem.base.view.recyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;



/**
 * @author THC
 * @Title: SimpleItemTouchHelperCallback
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/10/16 9:37
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    public static final float ALPHA_FULL = 1.0f;

    private boolean isLongPressDragEnable = true;
    private boolean isSwipeDismissEnable = false;
    private OnMoveAndSwipeListener onMoveAndSwipeListener;
    private OnItemTouchViewHoldListener onItemTouchViewHoldListener;

    public void setOnMoveAndSwipeListener(OnMoveAndSwipeListener onMoveAndSwipeListener) {
        this.onMoveAndSwipeListener = onMoveAndSwipeListener;
    }

    public void setOnItemTouchViewHoldListener(OnItemTouchViewHoldListener onItemTouchViewHoldListener) {
        this.onItemTouchViewHoldListener = onItemTouchViewHoldListener;
    }

    public void setLongPressDragEnable(boolean enable) {
        isLongPressDragEnable = enable;
    }

    public void setSwipeDismissEnable(boolean enable) {
        isSwipeDismissEnable = enable;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeDismissEnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return onMoveAndSwipeListener != null && source.getItemViewType() == target.getItemViewType() && onMoveAndSwipeListener.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        if (onMoveAndSwipeListener != null) {
            onMoveAndSwipeListener.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (onItemTouchViewHoldListener != null) {
                onItemTouchViewHoldListener.onItemSelected(viewHolder, actionState);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (onItemTouchViewHoldListener != null) {
            onItemTouchViewHoldListener.onItemClear(recyclerView, viewHolder);
        }
    }
}
