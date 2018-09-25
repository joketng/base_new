package com.joketng.base.receiver;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;

import java.util.List;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        String title = i.getStringExtra("title");
        String message = i.getStringExtra("message");
        String id = i.getStringExtra("id");
        String type = i.getStringExtra("type");
        //  处理点击之后的跳转
        Intent intent = null;
        ActivityManager mactivitymanager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = mactivitymanager.getRunningTasks(1).get(0).topActivity;

        if (checkAppIsTop(context, cn)) { // 处理用户正在使用应用的情况

            if (!TextUtils.isEmpty(id)) {
                if(TextUtils.equals(type, "0")){
//                    intent = new Intent(context, MessageActivity.class);
                } else /*if(TextUtils.equals(type, "1"))*/{
//                   intent = new Intent(context, OrderDetailActivity.class);
                }
//                intent = new Intent(context, FireExamActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("fromWhere", "push");
                jumpActivity(context, intent);
            }
        } else {
            if (!hasMainActivity(context)) {// 应用已退出
//                startApp(context, title, type, id);
                AppUtils.launchApp(context.getPackageName());
            } else {// 在后台运行
//                intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("URL", url);
//                intent.putExtra("TITLE", title);
//                jumpActivity(context, intent);
                if (!TextUtils.isEmpty(id)) {
//                    intent = new Intent(context, FireExamActivity.class);
                    if(TextUtils.equals(type, "0")){
//                        intent = new Intent(context, MessageActivity.class);
                        intent.putExtra("id", id);
                    } else {
//                        intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("id", id);
                    }
                    jumpActivity(context, intent);
                } else {
//                    intent = new Intent(context, RegisterActivity2.class);
//                    jumpActivity(context, intent);
                    AppUtils.launchApp(context.getPackageName());
                }
            }
        }
    }

    /**
     * 启动应用
     *
     * @param context
     */
    private void startApp(Context context, String title, String type, String id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFromPush", true);// 保存是否是来自点击通知的启动
        editor.putString("title", title);// 保存启动应用之后应该跳转的界面
        editor.putString("type", type);// 保存启动应用之后应该跳转的界面
        editor.putString("id", id);// 保存启动应用之后应该跳转的界面
        editor.commit();

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 跳转界面
     *
     * @param context
     * @param intent
     */
    private void jumpActivity(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    /**
     * 检测用户是否正在使用运用
     *
     * @param componentName
     */
    private boolean checkAppIsTop(Context context, ComponentName componentName) {
        if (componentName != null)
            return componentName.getPackageName().equals(context.getPackageName());
        return false;
    }

    /**
     * 获取处于活动状态的activity名称
     *
     * @param componentName
     */
    private String getActivityName2Resume(ComponentName componentName) {
        if (componentName != null)
            return componentName.getClassName();
        return "";
    }

    /**
     * 检测APP运行状态
     *
     * @param context
     */
    private boolean checkAppState(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测"com.jointem.wsj.activity.MainActivity"是否还存在
     *
     * @param context
     */
    private boolean hasMainActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {//
            if (info.baseActivity.getClassName().equals(context.getPackageName() + ".activity.MainActivity")) {
                return true;
            }
        }
        return false;
    }
}
