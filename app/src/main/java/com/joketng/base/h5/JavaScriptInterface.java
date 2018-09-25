package com.joketng.base.h5;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
public abstract class JavaScriptInterface {
    protected Context mContext;
    protected WebView webView;
    public static String JAVASCRIPT= "javascript:";

    protected JavaScriptInterface(Context context, WebView webView) {
        this.mContext = context;
        this.webView = webView;
    }

    /**
     * 原生调用的方法
     *
     * @param js js字符串
     *           <p>
     *           调用js的test(param)方法  eg. webView.loadUrl("javascript:test(1)");
     */

    @JavascriptInterface
    public void doJavaScript(String js) {
        if (webView != null) {
            // java给js发送消息
            webView.loadUrl(js);
        }
    }

    /**
     * 提供给js调用的方法
     *
     * @param jsonStr H5InfoBean 对应的json字符串
     *                <p>
     *                js调用java方法   eg. window.ZybJSInterface.doNative('hello java');
     */
    @JavascriptInterface
    public abstract void doNative(String jsonStr);

//    @JavascriptInterface
//    public abstract void doNative(String calssName,String keyName, String paramsJsonStr,Object success,Object failed);
}
