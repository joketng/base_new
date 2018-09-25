package com.joketng.base.request;

import android.app.Activity;
import android.content.Context;

import com.jointem.base.util.AlertDialogHelper;
import com.jointem.base.util.BaseExtensionKt;
import com.jointem.base.util.IDialogCallBack;
import com.jointem.plugin.request.NetConstants;
import com.jointem.plugin.request.rx.ApiException;
import com.jointem.plugin.request.rx.BaseSubscriber;
import com.joketng.base.WYCApplication;


/**
 * Description:处理sessionid失效
 * Created by Kevin.Li on 2017-11-13.
 */
public abstract class HandleSidInvalidBaseSubscriber<T> extends BaseSubscriber<T> {
    private Context context;

    public HandleSidInvalidBaseSubscriber(Context context) {
        super(context);
        this.context = context;
    }

    public HandleSidInvalidBaseSubscriber(Context context, boolean isShowProgressDialog) {
        super(context, isShowProgressDialog);
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (NetConstants.SESSION_ID_INVALID.equals(apiException.getCode())) {
                if (isShowProgressDialog) {
                    dismissProgress();
                }
                handleSessionIdInvalid();
            } else {
                super.onError(e);
            }
        } else {
            super.onError(e);
        }
    }

    @Override
    protected void _onError(String code, String message) {
        super._onError(code, message);
        BaseExtensionKt.showToast(context, message);

    }

    //会话失效，请重新登录！
    protected void handleSessionIdInvalid() {
        AlertDialogHelper.getInstance().buildSimpleDialog(context, "会话失效，请重新登录！", new IDialogCallBack() {
            @Override
            public void onCancel(String tag) {
            }

            @Override
            public void onSure(String tag) {
                if (context instanceof Activity) {
//                    UserInfoHelper.getInstance().cleanLocalUserInfo(context);
                    WYCApplication.deleteUserInfo();
//                    ((Activity) context).startActivityForResult(new Intent(context, RegisterActivity2.class), WYCApplication.REQUEST_RE_LOGIN);
                }
            }
        });
    }
}
