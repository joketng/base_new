package com.jointem.base.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.jointem.base.R;
import com.jointem.base.view.listener.IPushDialogListener;
import com.jointem.base.view.tooglebutton.ToggleButton;

/**
 * @author heyn
 * @Title: AlertDialogHelper
 * @Package com.jointem.zyb.view
 * @Description:
 * @date 2016/1/19 18:58
 */
public class AlertDialogHelper {
    private static int DEFAULT_WIDTH = 260;

    private AlertDialog dialog;
    private IPushDialogListener pushListener;

    //普通对话框
    private TextView tvOnlySure;
    private TextView tvSure;
    private TextView tvCancel;
    private LinearLayout llSure;
    private TextView tvTitle;

    //“我的”对话框
    private ToggleButton tbPushSwitch;
    private TextView tvFollowSwitch;
    private TextView tvClose;

    private static Dialog progressDialog;
    private TextView tvContent;

    private AlertDialogHelper() {
    }

    private static class SingletonHolder {
        private static final AlertDialogHelper INSTANCE = new AlertDialogHelper();
    }

    public static AlertDialogHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化普通对话框
     *
     * @param content
     * @return
     */
    private AlertDialog initDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "alertDialog", Toast.LENGTH_SHORT).show();
        }
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view.setLayoutParams(lp);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(SizeUtils.dp2px(DEFAULT_WIDTH),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(context.getResources().
                getDrawable(R.drawable.alert_window_stroke));
        tvTitle = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        this.tvContent = (TextView) view.findViewById(R.id.tv_alert_dialog_content);
        this.tvContent.setText(content);
        tvOnlySure = (TextView) view.findViewById(R.id.tv_alert_dialog_only_sure);
        llSure = (LinearLayout) view.findViewById(R.id.ll_alert_dialog);
        tvSure = (TextView) view.findViewById(R.id.tv_alert_dialog_sure);
        tvCancel = (TextView) view.findViewById(R.id.tv_alert_dialog_cancel);
        tvSure.setText("确定");
        tvOnlySure.setText("确定");
        tvCancel.setText("取消");
        return dialog;
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setCancelOutSide(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
    }

    /**
     * 创建一个确认对话框（对应onCancelled事件）
     *
     * @param content
     */
    public AlertDialogHelper buildConfirmDialog(Context context, String content) {
        this.buildConfirmDialog(context, content, "", null);
        return this;
    }

    /**
     * 创建一个确认对话框（对应onCancelled事件）
     *
     * @param content
     */
    public AlertDialogHelper buildConfirmDialog(Context context, String content, final String tag, final IDialogSureCallBack iDialogSureCallBack) {
        dismissDialog();
        initDialog(context, content);
        tvOnlySure.setVisibility(View.VISIBLE);
        llSure.setVisibility(View.GONE);
        tvOnlySure.setTag(tag);
        tvOnlySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDialogSureCallBack == null) {
                    dismissDialog();
                } else {
                    dismissDialog();
                    iDialogSureCallBack.onSure(tag);
                }
            }
        });
        return this;
    }

    /**
     * 创建一个操作提示对话框（对应onPositiveClicked和onNegativeClicked事件）
     *
     * @param content
     * @param tag     按钮事件的Tag
     */
    public AlertDialogHelper buildSimpleDialog(Context context, String content, final String tag, final IDialogCallBack dialogCallBack) {
        dismissDialog();
        initDialog(context, content);
        tvOnlySure.setVisibility(View.GONE);
        llSure.setVisibility(View.VISIBLE);
        tvSure.setTag(tag);
        tvCancel.setTag(tag);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                dialogCallBack.onSure(tag);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                dialogCallBack.onCancel(tag);
            }
        });
        return this;
    }

    /**
     * 创建一个操作提示对话框（对应onPositiveClicked和onNegativeClicked事件）
     *
     * @param context
     * @param content
     * @param dialogCallBack 按需重写 onCancel
     */
    public AlertDialogHelper buildSimpleDialog(Context context, String content, final IDialogCallBack dialogCallBack) {
        this.buildSimpleDialog(context, content, "", dialogCallBack);
        return this;
    }

    /**
     * @param context  上下文
     * @param isCancel 是否取消
     */
    public Dialog buildRoundProcessDialog(Context context, boolean isCancel) {
        if (((Activity) context).isFinishing()) {
            return null;
        }
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return null;
            }
        }
        progressDialog = new AlertDialog.Builder(context, R.style.dialog).create();
        progressDialog.setCancelable(isCancel);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "progressDialog", Toast.LENGTH_SHORT).show();
        }
        // 注意此处要放在show之后 否则会报异常
        progressDialog.setContentView(R.layout.loading_process_dialog_icon);
        return progressDialog;
    }

    public void dismissRoundProcessDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    AlertDialog verticalDialog;

    /**
     * 初始化“我的”对话框
     *
     * @return
     */
    private AlertDialog initVerticalDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        verticalDialog = builder.create();
        verticalDialog.show();
        verticalDialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_vertical, null, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(SizeUtils.dp2px(DEFAULT_WIDTH), LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view.setLayoutParams(lp);
        verticalDialog.setContentView(view);
        verticalDialog.getWindow().setLayout(SizeUtils.dp2px(DEFAULT_WIDTH), LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.alert_window_stroke));
        tbPushSwitch = (ToggleButton) view.findViewById(R.id.tb_dialog_push_switch);
        tvFollowSwitch = (TextView) view.findViewById(R.id.tv_dialog_follow_switch);
        tvClose = (TextView) view.findViewById(R.id.tv_dialog_close);

        return verticalDialog;
    }

    public void dismissVerticalDialog() {
        if (verticalDialog != null) {
            verticalDialog.dismiss();
        }
    }

    /**
     * 创建一个“我的”对话框
     *
     * @param tag       按钮事件的Tag
     * @param isChecked 是否开启关注站点消息推送
     */
    public void buildMineDialog(Context context, String tag, boolean isChecked, final IPushDialogListener pushListener) {
        dismissVerticalDialog();
        initVerticalDialog(context);
        setPushSwitchIsChecked(isChecked);
        tvFollowSwitch.setTag(tag);
        tbPushSwitch.setTag(tag);
        tvClose.setTag(tag);
        this.pushListener = pushListener;
        MyClickListener myClickListener = new MyClickListener();
        tvClose.setOnClickListener(myClickListener);
        tbPushSwitch.setOnClickListener(myClickListener);
        tvFollowSwitch.setOnClickListener(myClickListener);
    }

    private void setPushSwitchIsChecked(boolean isChecked) {
        if (isChecked) {
            tbPushSwitch.setToggleOn();
        } else {
            tbPushSwitch.setToggleOff();
        }
    }

    public AlertDialogHelper setSureText(String textSure) {
        if (tvOnlySure == null || tvSure == null)
            throw new RuntimeException("Please call 'build' method before!");
        if (tvOnlySure.getVisibility() == View.VISIBLE) {
            tvOnlySure.setText(textSure);
        } else {
            tvSure.setText(textSure);
        }
        return this;
    }

    public AlertDialogHelper setCancelText(String textSure) {
        if (tvCancel == null)
            throw new RuntimeException("Please call 'build' method before!");
        tvCancel.setText(textSure);
        return this;
    }

    public AlertDialogHelper setSureBackGroundColor(int color){
        if (tvOnlySure == null || tvSure == null)
            throw new RuntimeException("Please call 'build' method before!");
        if (tvOnlySure.getVisibility() == View.VISIBLE) {
            tvOnlySure.setBackgroundColor(color);
        } else {
            tvSure.setBackgroundColor(color);
        }
        return this;
    }

    public AlertDialogHelper setCancelBackgroundColor(int color){
        if (tvCancel == null)
            throw new RuntimeException("Please call 'build' method before!");
        tvCancel.setBackgroundColor(color);
        return this;
    }

    public AlertDialogHelper setSureTextColor(int color){
        if (tvOnlySure == null || tvSure == null)
            throw new RuntimeException("Please call 'build' method before!");
        if (tvOnlySure.getVisibility() == View.VISIBLE) {
            tvOnlySure.setTextColor(color);
        } else {
            tvSure.setTextColor(color);
        }
        return this;
    }

    public AlertDialogHelper setCancelTextColor(int color){
        if (tvCancel == null)
            throw new RuntimeException("Please call 'build' method before!");
        tvCancel.setTextColor(color);
        return this;
    }

    public AlertDialogHelper setContentGravity(int gravity){
        if (tvContent == null)
            throw new RuntimeException("Please call 'build' method before!");
        tvContent.setGravity(gravity);
        return this;
    }

    public AlertDialogHelper setTitleText(String text){
        if(tvTitle != null){
            tvTitle.setText(text);
        }
        return this;
    }

    public AlertDialogHelper setTitleVisibility(int visibility){
        if(tvTitle != null){
            tvTitle.setVisibility(visibility);
        }
        return this;
    }

    public AlertDialogHelper setTitleGravity(int gravity){
        if(tvTitle != null){
            tvTitle.setGravity(gravity);
        }
        return this;
    }

    public AlertDialogHelper setContentClickListener(View.OnClickListener onClickListener){
        if(tvContent != null){
            tvContent.setOnClickListener(onClickListener);
        }
        return this;
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dismissVerticalDialog();
            if (v.getId() == R.id.tb_dialog_push_switch) {
                pushListener.onPushSwitchClicked((String) tbPushSwitch.getTag());
            } else if (v.getId() == R.id.tv_dialog_follow_switch) {
                pushListener.onFollowSwitchClicked((String) tvFollowSwitch.getTag());
            } else if (v.getId() == R.id.tv_dialog_close) {
                pushListener.onCloseButtonClicked((String) tvClose.getTag());
            }
        }
    }
}
