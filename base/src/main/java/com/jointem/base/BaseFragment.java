package com.jointem.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:
 * Created by Kevin.Li on 2017/4/8.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected Context activity;
    protected boolean isInitView = false;
    protected View rootView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!isInitView){
            rootView = LayoutInflater.from(context).inflate(initRootViewResource(), container, false);
            initData();
            initWidget();
            initListener();
            isInitView = true;
        }
        return rootView;
    }

    public abstract int initRootViewResource();

    protected void initWidget(){}

    protected void initData(){}

    protected void initListener(){}
}
