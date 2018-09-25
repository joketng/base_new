package com.joketng.base.service

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.RemoteViews
import com.blankj.utilcode.util.AppUtils
import com.jointem.base.BaseApplication
import com.jointem.dbhelper.DownInfo
import com.jointem.plugin.request.download.DownLoadInfo
import com.jointem.plugin.request.download.DownState
import com.jointem.plugin.request.download.HttpDownManager
import com.jointem.plugin.request.download.HttpDownOnNextListener
import com.jointem.plugin.request.util.DBRequestUtil
import com.joketng.base.R
import com.joketng.base.WYCApplication
import com.orhanobut.logger.Logger
import java.text.DecimalFormat

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/7/13
 */
class UpdateService : IntentService("com.ydwtx.download") {
    var downLoadInfo: DownLoadInfo? = null
    private var rate: Int = 0// 下载百分比
    private val df1 = DecimalFormat("0.00")

    private var oldLength: Long = 0
    override fun onHandleIntent(intent: Intent?) {
//        if (!WYCApplication.isDownloadingApk) {
//            createNotification()
//            var url = intent?.getStringExtra("url") ?: ""
//            if (!url.startsWith("http")) {
//                url = "http://$url"
//            }
////        val mission = Mission(url)
//            val mission = Mission(url, "ydwtx.apk", CommonConstants.PICTURE_PATH)
//            Logger.d("$url-------------------------$url")
//            RxDownload.create(mission, false)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        //                    Logger.d(it.chunkFlag)
////                    Logger.d(it.percent())
//                        Logger.d(Thread.currentThread().name)
//                        Logger.d("总的大小：${it.totalSize} 现在下载到：${it.downloadSize}")
//                        val currentProgress = (it.downloadSize * 100f / it.totalSize + 0.5f).toInt()
//                        Logger.d("现在的大小:${currentProgress}%")
////                    if(it.totalSize == it.downloadSize){
////                        sendBroadcast(currentProgress)
////                        remoteView?.setTextViewText(R.id.notificationPercent,  "$currentProgress%")
////                        remoteView?.setProgressBar(R.id.notificationProgress, 100, currentProgress, false)
////                        notificationManager?.cancel(NOTIFICATION_ID)
////                    } else
//                        WYCApplication.isDownloadingApk = true
//                        if (currentProgress % 3 == 0) {
//                            sendBroadcast(currentProgress)
//                            remoteView?.setTextViewText(R.id.notificationPercent, "$currentProgress%")
//                            remoteView?.setProgressBar(R.id.notificationProgress, 100, currentProgress, false)
//                            if (currentProgress == 100) {
//                                notificationManager?.cancel(NOTIFICATION_ID)
//                            } else {
//                                notificationManager?.notify(NOTIFICATION_ID, mNotifyBuilder.build())
//                            }
//                        } else if (it.totalSize > 0L && it.totalSize == it.downloadSize) {
//                            sendBroadcast(100)
//                            WYCApplication.isDownloadingApk = false
//                            remoteView?.setTextViewText(R.id.notificationPercent, "$currentProgress%")
//                            remoteView?.setProgressBar(R.id.notificationProgress, 100, currentProgress, false)
//                            notificationManager?.cancel(NOTIFICATION_ID)
//                            Logger.d("========start")
//                            SystemTool.installApk(this, CommonConstants.PICTURE_PATH + "/ydwtx.apk")
//                            Logger.d("========end")
//                            stopSelf()
//                        }
//
//
//                    }
//            RxDownload.start(mission).subscribe()
//        }

        var url = intent?.getStringExtra("url") ?: ""
            if (!url.startsWith("http")) {
                url = "http://$url"
            }
        val storePath = intent?.getStringExtra("storePath")?:""
        if(url.isNotEmpty() && storePath.isNotEmpty()){
            downloadNewestApk(url, storePath)
        }

    }

    companion object {
        val APK_DOWNLOAD_BROADCAST = "com.joketng.apk_download"
        val NOTIFICATION_ID = 0x99

    }

    private fun downloadNewestApk(url:String,storeFilePath:String){
        val downInfo = DownInfo()
                downInfo.stateInt = DownState.START
                downInfo.countLength = 0
                downInfo.readLength = 0
                downInfo.url = url
                downInfo.savePath = storeFilePath
                DBRequestUtil.insertOrReplaceDownInfo(baseContext, downInfo)


            downLoadInfo = DownLoadInfo(downInfo, object : HttpDownOnNextListener<Any>() {
                override fun onNext(o: Any) {
                    Logger.d("onNext")
                }

                override fun onStart() {
                    createNotification()
                }

                override fun onComplete() {}

                override fun updateProgress(readLength: Long, countLength: Long) {
                    WYCApplication.isDownloadingApk = readLength != countLength
                    if (readLength != countLength) {
                        WYCApplication.isDownloadingApk = true
                        if (readLength - oldLength > countLength * 0.04) {

                            val x_double = readLength * 1.0
                            val tempresult = x_double / countLength
                            // ##.00%百分比格式，后面不足2位的用0补齐
                            val result = df1.format(tempresult)
                            rate = (java.lang.Float.parseFloat(result) * 100).toInt()
                            sendBroadcast(rate)

                            remoteView?.setTextViewText(R.id.notificationPercent,  "$rate%")
                            remoteView?.setProgressBar(R.id.notificationProgress, 100, rate, false)
                            notificationManager?.notify(NOTIFICATION_ID, mNotifyBuilder.build())
                            Log.i("tag", "updateProgress()_$rate")
                            oldLength = readLength
                        }
                    } else {
//                        EventBus.getDefault().post(new Event(APPConstants.EventFlag.EVENT_DOWNLOAD_APK_COMPLETE, null));
                        remoteView?.setProgressBar(R.id.notificationProgress, 100, rate, false);
                        sendBroadcast(rate)
                        WYCApplication.isDownloadingApk = false
                        AppUtils.installApp(storeFilePath)
                        notificationManager?.cancel(NOTIFICATION_ID)
                    }
                }
            })
        HttpDownManager.getInstance(BaseApplication.getContextFromApplication()).startDown(downLoadInfo)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    private fun sendBroadcast(rate: Int) {
        val sendIntent = Intent()
        sendIntent.action = APK_DOWNLOAD_BROADCAST
        sendIntent.putExtra("RATE", rate)
        LocalBroadcastManager.getInstance(this@UpdateService).sendBroadcast(sendIntent)
    }

    override fun onDestroy() {
        if (downLoadInfo != null) {
            HttpDownManager.getInstance(BaseApplication.getContextFromApplication()).stopDown(downLoadInfo)
        }
        super.onDestroy()
    }


    private var notificationManager: NotificationManager? = null
    /***
     * 创建通知栏
     */
    private var remoteView: RemoteViews? = null
    private val mNotifyBuilder = NotificationCompat.Builder(this)
            .setContentTitle("云滴万通行")
            .setContentText("正在下载")
            .setChannelId("1")
            .setSmallIcon(R.mipmap.app_logo)

    fun createNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", "更新", NotificationManager.IMPORTANCE_HIGH)
            notificationManager!!.createNotificationChannel(channel)
        }
        remoteView = RemoteViews(packageName, R.layout.notification_item)
        mNotifyBuilder.setContent(remoteView)
        notificationManager!!.notify(NOTIFICATION_ID, mNotifyBuilder.build())
    }


}
