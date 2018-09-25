package com.jointem.base;

import android.content.Context;

import com.jointem.base.iView.IPtrView;
import com.jointem.base.util.BaseExtensionKt;

/**
 * @author THC
 * @Title: TErrors
 * @Package com.jointem.base
 * @Description:
 * @date 2017/4/20 9:32
 */
public class TErrors {

    public static void handler(Context context, String code, String message){
        handler(context, null, code, message);
    }

    public static void handler(Context context, IPtrView iPtrView, String code, String message){
        switch (code){
            case "999999":
                break;
            default:
                BaseExtensionKt.showToast(context,message);
                break;
        }
        if(null != iPtrView){
            iPtrView.onFinishRequest();
        }
    }
}
