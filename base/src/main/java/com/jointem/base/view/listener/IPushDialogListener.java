package com.jointem.base.view.listener;

/**
 * 我的关注界面，编辑时弹框操作的回调监听器
 * <p/>
 * author: Kevin.Li
 * created at 2017/5/11 16:07
 */
public interface IPushDialogListener {
    void onPushSwitchClicked(String tag);

    void onFollowSwitchClicked(String tag);

    void onCloseButtonClicked(String tag);
}
