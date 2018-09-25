package com.jointem.base.iView;

/**
 * Description:activity初始化
 * Created by Kevin.Li on 2017/4/13.
 */
public interface I_initializer {
    /**
     * 设置root界面
     */
    void setRootView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化控件
     */
    void initWidget();
}
