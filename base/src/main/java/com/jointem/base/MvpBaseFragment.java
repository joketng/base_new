package com.jointem.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jointem.base.iView.IView;
import com.jointem.base.presenter.BasePresenter;

public abstract class MvpBaseFragment<V extends IView, T extends BasePresenter<V>> extends BaseFragment {
    protected T mPresenter;  // Presenter对象
    protected V iView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iView = (V) this;
        iView.setPresenter();
        mPresenter.attachView(iView);
        mPresenter.initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
