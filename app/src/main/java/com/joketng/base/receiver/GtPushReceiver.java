package com.joketng.base.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.joketng.base.R;
import com.joketng.base.activity.WebviewActivity2;
import com.igexin.sdk.PushConsts;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */

public class GtPushReceiver extends BroadcastReceiver{
    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static int id = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Logger.e("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
               /* // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));*/

                byte[] payload = bundle.getByteArray("payload");
                if (payload == null)
                    return;
                String data = new String(payload);
                Logger.e("push_info:", "push_info:" + data);
                String title = "";
                String message = "";
                String url = "";
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    title = jsonObject.getString("title");
                    message = jsonObject.getString("message");
                    if(TextUtils.isEmpty(message)){
                        message = jsonObject.getString("content");
                    }
                    url = jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!TextUtils.isEmpty(title)) {
                    if (TextUtils.isEmpty(message)) {
                        message = title;
                    }
                    resultActivityBackApp(context, title, message, url);
                }
                break;

            case PushConsts.GET_CLIENTID:
               /* // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");*/
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    /**
     * 点击通知进入一个Activity，点击返回时进入指定页面。
     * <p>
     * author: Kevin.Li
     * created at 2016/1/6 18:31
     */
    public void resultActivityBackApp(Context context, String title, String message, String url) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setTicker(title);
        mBuilder.setSmallIcon(R.mipmap.app_logo);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
//		long[] vibrate = {0, 100, 200, 300};
//		mBuilder.setVibrate(vibrate);

        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        mBuilder.setAutoCancel(true);

        //点击通知之后的操作
        Intent webIntent = new Intent(context, WebviewActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        bundle.putString("TITLE", title);
        boolean isDynamic = url.contains("dynamic.html");
        bundle.putBoolean("ISDYNAMIC", isDynamic);
        bundle.putBoolean("PUSH", true);
        bundle.putString("SHARE_DES", message);
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webIntent.putExtras(bundle);

//        if (isDynamic) {//如果是动态的推送，则把sp文件中的值改为显示小红点
//            //记录小红点上一次显示的状态
//            boolean lastIsShow = PreferenceHelper.readBoolean(context, APPConstants.SPConstants.DYNAMIC_FILE_NAME, APPConstants.SPConstants.DYNAMIC_IS_SHOW);
//            PreferenceHelper.write(context, APPConstants.SPConstants.DYNAMIC_FILE_NAME, APPConstants.SPConstants.DYNAMIC_LAST_IS_SHOW, lastIsShow);
//            //设置小红点为显示状态，并发通知来刷新UI
//            PreferenceHelper.write(context, APPConstants.SPConstants.DYNAMIC_FILE_NAME, APPConstants.SPConstants.DYNAMIC_IS_SHOW, true);
//            EventBus.getDefault().post(new Event(APPConstants.EventFlag.EVENT_REFRESH_RED_DOT, null));
//        }

        if (id > 99)
            id = 0;
        id++;
        Logger.e("id = " + id);
        PendingIntent pIntent = PendingIntent.getActivity(context, id, webIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        nm.notify(id, mBuilder.build());
    }

}
