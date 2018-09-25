package com.jointem.base.view.recyclerview.animation;/**
 * Created by jointem on 2016/3/26.
 */

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author THC
 * @Title: AnimateViewHolder
 * @Package com.jointem.zyb.animation
 * @Description:
 * @date 2016/3/26 9:43
 */
public abstract class AnimateViewHolder extends RecyclerView.ViewHolder {

    public AnimateViewHolder(View itemView) {
        super(itemView);
    }

    public void preAnimateAddImpl() {
    }

    public void preAnimateRemoveImpl() {
    }

    public abstract void animateAddImpl(ViewPropertyAnimatorListener listener);

    public abstract void animateRemoveImpl(ViewPropertyAnimatorListener listener);
}
