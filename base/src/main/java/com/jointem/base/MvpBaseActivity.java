package com.jointem.base;

import android.support.annotation.CallSuper;
import android.view.View;

import com.jointem.base.iView.IView;
import com.jointem.base.presenter.BasePresenter;

import java.util.Calendar;

/**
 * Description:
 * Created by Kevin.Li on 2017/4/11.
 */
public class MvpBaseActivity<V extends IView, T extends BasePresenter<V>> extends BaseActivity implements View.OnClickListener {
    protected T mPresenter;  // Presenter对象
    protected V iView;
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private View clickedView;
    private long lastClickTime = 0;

    @Override
    @CallSuper
    public void initData() {
        super.initData();
        iView = (V) this;
        iView.setPresenter();
        mPresenter.attachView(iView);
        mPresenter.initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void setRootView() {
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (clickedView != null && clickedView == v && currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            return;
        }
        lastClickTime = currentTime;
        clickedView = v;
    }
}
