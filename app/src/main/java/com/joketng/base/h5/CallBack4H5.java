package com.joketng.base.h5;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
public class CallBack4H5 {
    private String success;
    private String failed;
    private WebView webView;
    protected StringBuffer sb = new StringBuffer();

    public CallBack4H5(String success, String failed, WebView webView) {
        this.success = success;
        this.failed = failed;
        this.webView = webView;
    }

    public void onSuccess(String jsonStr) {
        if (TextUtils.isEmpty(success)) {
            return;
        }
        if (webView != null) {
            sb.setLength(0);
            if (!TextUtils.isEmpty(jsonStr)) {
                String successStr = sb.append("javascript:")
                        .append(success)
                        .append("('")
                        .append(jsonStr)
                        .append("')")
                        .toString();
                Logger.d(successStr);
                webView.loadUrl(successStr);
            } else {
                webView.loadUrl(sb.append("javascript:")
                        .append(success)
                        .append("('")
                        .append("')")
                        .toString()
                );
            }
        }
    }

    public void onFailed(String jsonStr) {
        if(TextUtils.isEmpty(failed)){
            return;
        }
        if (webView != null) {
            sb.setLength(0);
            if (!TextUtils.isEmpty(jsonStr)) {
                webView.loadUrl(sb.append("javascript:")
                        .append(failed)
                        .append("('")
                        .append(jsonStr)
                        .append("')")
                        .toString()
                );
            } else {
                webView.loadUrl(sb.append("javascript:")
                        .append(failed)
                        .append("('")
                        .append("')")
                        .toString()
                );
            }
        }
    }

    public View getWebView() {
        return webView;
    }

}
