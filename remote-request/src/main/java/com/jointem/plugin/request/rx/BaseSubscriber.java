package com.jointem.plugin.request.rx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.jointem.plugin.request.NetConstants;
import com.jointem.plugin.request.ProgressListener;
import com.jointem.plugin.request.R;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * @author THC
 * @Title: BaseSubscriber
 * @Package com.jointem.hgp.request
 * @Description:
 * @date 2017/2/4 9:34
 */
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> implements ProgressListener {
    protected WeakReference<Context> reference;
    private Dialog progressDialog;
    protected boolean isShowProgressDialog;

    public BaseSubscriber(Context context) {
        reference = new WeakReference<>(context);
    }

    public BaseSubscriber(Context context, boolean isShowProgressDialog) {
        reference = new WeakReference<>(context);
        this.isShowProgressDialog = isShowProgressDialog;
    }

    public BaseSubscriber() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowProgressDialog) {
            showProgress();
        }
    }

    @Override
    public void onComplete() {
        if (isShowProgressDialog) {
            dismissProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (isShowProgressDialog) {
            dismissProgress();
        }
        if (e instanceof ConnectException || e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            _onError(NetConstants.NO_CONNECTION_ERROR, "网络连接失败,请检查您的网络");
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            _onError(apiException.getCode(), apiException.getMessage());
        } else if (e instanceof JsonSyntaxException) {
            e.printStackTrace();
            _onError(NetConstants.OTHER_EXCEPTION, "数据解析失败");
        } else {
            e.printStackTrace();
            if(TextUtils.isEmpty(e.getMessage())){
                _onError(NetConstants.OTHER_EXCEPTION, "请求失败，请稍后再试...");
            } else {
            _onError(NetConstants.OTHER_EXCEPTION, e.getMessage());
            }
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);

    protected void _onError(String code, String message) {
//        Toast.makeText(reference.get(), message, Toast.LENGTH_LONG).show();
    }

    protected boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void showRoundProcessDialog(boolean isCancel) {
        if (reference.get() == null) {
            return;
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return;
            }
        }
        progressDialog = new AlertDialog.Builder(reference.get(),R.style.dialog).create();
//        progressDialog = new AlertDialog.Builder(getParent(), R.style.dialog).create();
        progressDialog.setCancelable(isCancel);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
            Toast.makeText(reference.get(), e.getMessage() + "progressDialog", Toast.LENGTH_SHORT).show();
        }
        // 注意此处要放在show之后 否则会报异常
        progressDialog.setContentView(R.layout.loading_process_dialog_icon);
    }

    private void dismissProcessDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showProgress() {
        if (null != reference.get()) {
            showRoundProcessDialog(true);
        }
    }

    @Override
    public void dismissProgress() {
        dismissProcessDialog();
    }

    /**
     * 显示网络请求等待对话框
     * <p/>
     * author: Kevin.Li
     * created at 2017/5/25 16:46
     */
    public BaseSubscriber<T> showProgressDialog(Context context) {
        if (reference == null) {
            reference = new WeakReference<>(context);
        }
        isShowProgressDialog = true;
        return this;
    }
}
