package com.joketng.base.IView;

import com.jointem.base.iView.IView;


public interface IViewRegister extends IView {

    void registerSuccess();

    void registerFailure(String errorCode, String msg);

    /**
     * 改变验证码按钮的状态
     */
    void identifyCodeButton(String errorCode);

}
