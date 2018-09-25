package com.jointem.base.view.recyclerview;

/**
 * @author THC
 * @Title: OnMoveAndSwipeListener
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/3/16 16:28
 */
public interface OnMoveAndSwipeListener<T> {
    boolean onItemMove(int fromPosition, int toPosition);

    T onItemDismiss(int position);
}
