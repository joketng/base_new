package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author THC
 * @Title: MonitorScrollView
 * @Package com.handmark.pulltorefresh.library
 * @Description:
 * @date 2016/6/7 11:12
 */
public class MonitorScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    public MonitorScrollView(Context context) {
        super(context);
    }

    public MonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }
    public interface OnScrollListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }
}
