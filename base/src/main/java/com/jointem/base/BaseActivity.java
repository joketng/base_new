package com.jointem.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.jointem.base.iView.I_initializer;
import com.jointem.base.util.SystemTool;
import com.jointem.base.view.swiplayoutview.SwipeBackActivity;
import com.orhanobut.logger.Logger;

import io.reactivex.processors.BehaviorProcessor;


public class BaseActivity extends SwipeBackActivity implements I_initializer, View.OnClickListener {
    public Context context;
    public Activity activity;
    public final static Integer ACTIVITY_EVENT = 998;
    public final BehaviorProcessor<Integer> lifecycleSubject = BehaviorProcessor.create();
    protected TextView tvTitle;
    protected ViewGroup rlBack;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        // 隐藏标题栏
        // // 隐藏状态栏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        ImmersionBar.with(this)
                .statusBarColor(R.color.c_main_white_bg)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true, R.color.c_main_white_bg)
                .init();
        super.setContentView(layoutResID);


    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(view, params);
    }

    @Override
    public void setContentView(View view) {
        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        activity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityHelper.getInstance().pushActivity(this);
        setRootView();
        super.onCreate(savedInstanceState);
        initialize();
        Logger.d(SystemTool.getRunningActivityName(this));

    }

    private void initTitleView() {
//        if (findViewById(R.id.imvBack) != null)
//            imvBack = (ImageView) findViewById(R.id.imv_back);
        if (findViewById(R.id.tv_sub_title) != null)
            tvTitle = (TextView) findViewById(R.id.tv_sub_title);
        if(findViewById(R.id.rl_back) != null){
            rlBack = (ViewGroup) findViewById(R.id.rl_back);
        }
        if (rlBack != null) {
            rlBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ACTIVITY_EVENT);
//        ImmersionBar.with(this).destroy();
        super.onDestroy();
//        ActivityHelper.getInstance().popActivity();
        context = null;
        activity = null;
    }

    protected void initialize() {
        initData();
        initWidget();
        initListener();
    }

    protected void initListener() {

    }

    @Override
    public void setRootView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initWidget() {

        initTitleView();
    }

    protected void widgetClick(View v) {
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ImmerseHelper.setSystemBarTransparent(this);
//        }
        setSwipeBackEnable(false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setSwipeBackEnable(false);
        }
    }

    private Dialog progressDialog;

    public void showRoundProcessDialog(boolean isCancel) {
        if (activity == null) {
            return;
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return;
            }
        }
        progressDialog = new AlertDialog.Builder(activity, com.jointem.plugin.request.R.style.dialog).create();
//        progressDialog = new AlertDialog.Builder(getParent(), R.style.dialog).create();
        progressDialog.setCancelable(isCancel);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage() + "progressDialog", Toast.LENGTH_SHORT).show();
        }
        // 注意此处要放在show之后 否则会报异常
        progressDialog.setContentView(com.jointem.plugin.request.R.layout.loading_process_dialog_icon);
    }

    public void dismissProcessDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
