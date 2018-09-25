package com.joketng.base.activity

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jointem.base.BaseActivity
import com.joketng.base.R
import com.joketng.base.utils.WebUtil
import com.joketng.base.utils.visible
import kotlinx.android.synthetic.main.activity_webview_2.*
import kotlinx.android.synthetic.main.v_web_progress_bar.*

/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/7/15
 */
class WebviewActivity2 : BaseActivity(){
    var mUrl = ""
    var mTitle = ""
    companion object {
        val URL = "url"
        val TITLE = "title"
    }

    override fun setRootView() {
        super.setRootView()
        setContentView(R.layout.activity_webview_2)
    }

    override fun initData() {
        super.initData()
        intent.extras?.let {
            mUrl = it.getString(URL)
            mTitle = it.getString(TITLE)
        }
    }

    override fun initWidget() {
        super.initWidget()
        tvTitle.text = mTitle
        WebUtil.initWebSettings(context, web_view.settings)
        val progressAnimator = ValueAnimator.ofInt(0, 90)
        progressAnimator.duration = 1000
        progressAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            web_progress_bar.progress = value * 10
        }
        web_view.webViewClient = CustomWebViewClient(progressAnimator, activity as BaseActivity)
        web_view.loadUrl(mUrl)
    }

    override fun initListener() {
        super.initListener()


    }

    override fun onPause() {
        super.onPause()
        web_view.onPause()
    }

    override fun onResume() {
        super.onResume()
        web_view.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            web_view.clearHistory();
//            (web_view.getParent()).removeView(web_view);
            web_view.loadUrl("about:blank");
            web_view.stopLoading();
            web_view.setWebChromeClient(null);
            web_view.setWebViewClient(null);
            web_view.destroy();

        }
    }

    class CustomWebViewClient(val valueAnimator: ValueAnimator, val activity: BaseActivity) :WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
//            activity.showRoundProcessDialog(true)
            activity.web_progress_bar.visible(true)
            valueAnimator.start()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
//            activity.dismissProcessDialog()
            activity.web_progress_bar.visible(false)
        }
    }

    class CustomWebviewChromeClient : WebChromeClient(){
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }

    }
}