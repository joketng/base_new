package com.handmark.pulltorefresh.library.rv;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * author THC
 * Description: SavedStateRecyclerView
 * date 2016/9/18 8:58
 */
public class RecyclerView1 extends RecyclerView {
    static final String SAVED_STATED = "saved_stated";
    static final String SUPER_ALL = "super_all";
    private Bundle bundleData;

    public RecyclerView1(Context context) {
        super(context);
    }

    public RecyclerView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView1(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_ALL, bundleData);
        bundle.putParcelable(SAVED_STATED, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(SAVED_STATED));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    void setBundleData(Bundle data){
        bundleData = data;
    }
}
