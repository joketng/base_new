package com.joketng.base.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import com.joketng.base.R
import com.joketng.base.receiver.NotificationReceiver
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import com.orhanobut.logger.Logger
import org.json.JSONException
import org.json.JSONObject
/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
open class IntentService : GTIntentService() {
    override fun onNotificationMessageArrived(p0: Context?, p1: GTNotificationMessage?) {

    }

    override fun onNotificationMessageClicked(p0: Context?, p1: GTNotificationMessage?) {

    }

    override fun onReceiveServicePid(context: Context, pid: Int) {}

    override fun onReceiveMessageData(context: Context, msg: GTTransmitMessage) {
        val data = String(msg.payload)
        var title = ""
        var id = ""
        var type = ""
        var message = ""
        var url = ""
        var bId = ""
        val messageType = ""
        try {
            val jsonObject = JSONObject(data)
            title = jsonObject.getString("title")
            message = jsonObject.getString("message")
            if (TextUtils.isEmpty(message)) {
                message = jsonObject.getString("content")
            }
            id = jsonObject.getString("id")
            type = jsonObject.getString("type")
            bId = jsonObject.getString("bId")
            url = jsonObject.getString("url")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Logger.d(title)

        if (!TextUtils.isEmpty(title)) {
//            if (TextUtils.isEmpty(id)) {
//                id = title
//            }
            resultActivityBackApp(context, title, message, type, id)
        }
    }

    override fun onReceiveClientId(context: Context, clientid: String) {}

    override fun onReceiveOnlineState(context: Context, online: Boolean) {}

    override fun onReceiveCommandResult(context: Context, cmdMessage: GTCmdMessage) {}

    var mid = 0//为了不重复 notify

    /**
     * 点击通知进入一个Activity，点击返回时进入指定页面。
     *
     *
     */
    fun resultActivityBackApp(context: Context, title: String, message: String, type: String, id: String) {
        var message = message
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("2", "默认", NotificationManager.IMPORTANCE_HIGH)
            nm.createNotificationChannel(channel)
        }

        val mBuilder = Notification.Builder(context)
        mBuilder.setTicker(title)
        mBuilder.setSmallIcon(R.mipmap.app_logo)
        mBuilder.setContentTitle(title)
        if (message.startsWith("http")) {
            message = ""
        }
        mBuilder.setContentText(message)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId("2")
        }
        mBuilder.setDefaults(Notification.DEFAULT_ALL)

        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        mBuilder.setAutoCancel(true)

        //点击通知之后的操作
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)
        broadcastIntent.putExtra("title", title)
        broadcastIntent.putExtra("message", message)
        broadcastIntent.putExtra("type", type)
        broadcastIntent.putExtra("id", id)
        broadcastIntent.action = "com.aheading.news.zsyuxi.notification"
        if (mid > 99)
            mid = 0
        mid++
        val pIntent = PendingIntent.getBroadcast(context, mid, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pIntent)
        nm.notify(mid, mBuilder.build())
    }

}
