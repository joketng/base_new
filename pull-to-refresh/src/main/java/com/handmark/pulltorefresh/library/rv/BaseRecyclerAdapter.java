package com.handmark.pulltorefresh.library.rv;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;


public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener, View.OnLongClickListener {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> itemList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerView mRecyclerView;


    public BaseRecyclerAdapter(Context context, List<T> itemList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerViewItemClickHelper itemClickHelper = new RecyclerViewItemClickHelper(recyclerView) {
//
//            @Override
//            protected void onItemClick(RecyclerView.ViewHolder viewHolder) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.OnItemClick(viewHolder);
//                }
//            }
//
//            @Override
//            protected void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
//                if (mOnItemLongClickListener != null) {
//                    mOnItemLongClickListener.OnItemLongClick(viewHolder);
//                }
//            }
//        };
        mRecyclerView = recyclerView;
//        recyclerView.addOnItemTouchListener(itemClickHelper);
    }
    @CallSuper
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(RecyclerView.ViewHolder holder);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(RecyclerView.ViewHolder holder);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.OnItemClick(mRecyclerView.getChildViewHolder(v));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.OnItemLongClick(mRecyclerView.getChildViewHolder(v));
            return true;
        }
        return false;
    }
}
