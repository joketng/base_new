package com.joketng.base.h5;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.joketng.base.bean.H5InterAction;
import com.joketng.base.bean.UserInfoForH5;
import com.jointem.dbhelper.UserInfo;
import com.jointem.plugin.request.util.GsonUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
public class JSInterAction extends JavaScriptInterface {
    private View contentView;
    private String classNamePrefix = "com.jointem.fire.h5.h5module.";
    private H5InterAction h5InterAction;

    public JSInterAction(Context context, WebView webView, View contentView) {
        super(context, webView);
        this.contentView = contentView;
    }

    @Override
    @JavascriptInterface
    public void doNative(final String jsonStr) {
        contentView.post(new Runnable() {//避免在子线程修改UI
            @Override
            public void run() {
                try {
                    Logger.d(jsonStr);
                    JSONObject h5JsonObj = new JSONObject(jsonStr);
                    h5InterAction = GsonUtils.getInstance().changeGsonToBean(jsonStr, H5InterAction.class);
//                    JSBridgeManager.register(h5JsonObj.optString("className"), Class.forName(classNamePrefix + h5JsonObj.optString("className")));
                    JSBridgeManager.register(h5InterAction.getClassName(), Class.forName(classNamePrefix + h5InterAction.getClassName()));
                    if (!JSBridgeManager.doNative(mContext, contentView, webView, h5JsonObj)) {
                        doJavaScript(buildJsonStr("000001", "调用的方法不存在", h5JsonObj));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String buildJsonStr(String code, String message, JSONObject jsonObject) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        if (!TextUtils.isEmpty(code)) {
            stringHashMap.put("code", code);
        }
        if (!TextUtils.isEmpty(message)) {
            stringHashMap.put("message", message);
        }
        String failed = jsonObject.optString("failed");
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        return sb.append(JAVASCRIPT)
                .append(failed)
                .append("('")
                .append(GsonUtils.getInstance().toJsonString(stringHashMap))
                .append("')")
                .toString();
    }

    public String codeMessageJsonStr(String code, String message, boolean isFailed) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        if (!TextUtils.isEmpty(code)) {
            stringHashMap.put("code", code);
        }
        if (!TextUtils.isEmpty(message)) {
            stringHashMap.put("message", message);
        }
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        return sb.append(JAVASCRIPT)
                .append(isFailed ? h5InterAction.getFailed() : h5InterAction.getSuccess())
                .append("('")
                .append(GsonUtils.getInstance().toJsonString(stringHashMap))
                .append("')")
                .toString();
    }

    public H5InterAction getH5InterAction() {
        return h5InterAction;
    }

    public void getUserInfo() {
        StringBuilder sb = new StringBuilder();
        // TODO: 2017/10/12
//        UserInfo userInfo = UserInfoHelper.getInstance().getUserInfo(mContext);
        UserInfo userInfo = null;
        if (userInfo == null) return;
        UserInfoForH5 userInfoForH5 = new UserInfoForH5();
        userInfoForH5.setId(userInfo.getUserId());
        userInfoForH5.setAccessToken(userInfo.getToken());
        userInfoForH5.setHeadImg(userInfo.getHeadImg());
        userInfoForH5.setUserName(userInfo.getUserName());
        userInfoForH5.setNickName(userInfo.getNickName());
        String userInfoJsonStr = GsonUtils.getInstance().toJsonString(userInfoForH5);
//      "javascript:"+js方法的名字＋方法的参数值拼接成一个字符串
//       doJavaScript("javascript: " + h5InfoBean.getSuccess() + "('" + s + "')");
        sb.setLength(0);
        String jsStr = sb.append(JAVASCRIPT)
                .append(h5InterAction.getSuccess())
                .append("('")
                .append(userInfoJsonStr)
                .append("')")
                .toString();
        doJavaScript(jsStr);
    }

}
