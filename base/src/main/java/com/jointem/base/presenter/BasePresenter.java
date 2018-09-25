package com.jointem.base.presenter;

import android.content.Context;

import com.jointem.base.iView.IView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description:Presenter基类，依赖View的抽象
 * Created by Kevin.Li on 2017/4/10.
 */
public abstract class BasePresenter<V extends IView> {
    protected Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    private Reference<V> mViewRef; // View接口类型的弱引用

    /**
     * 建立关联
     * <p/>
     * author: Kevin.Li
     * created at 2016/3/7 22:51
     */
    @SuppressWarnings("unchecked")
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * 获取View
     * <p/>
     * author: Kevin.Li
     * created at 2016/3/7 22:51
     */
    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断是否与View建立关联
     * <p/>
     * author: Kevin.Li
     * created at 2016/3/7 22:52
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联
     * <p/>
     * author: Kevin.Li
     * created at 2016/3/7 22:52
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public abstract void initData();
}
