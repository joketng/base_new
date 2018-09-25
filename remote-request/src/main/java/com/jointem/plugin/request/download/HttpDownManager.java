package com.jointem.plugin.request.download;

import android.content.Context;

import com.jointem.dbhelper.DownInfo;
import com.jointem.plugin.request.APIRequest;
import com.jointem.plugin.request.GetInterfaceConfig;
import com.jointem.plugin.request.rx.RetryWhenNetworkException;
import com.jointem.plugin.request.util.DBRequestUtil;
import com.jointem.plugin.request.util.FileRequestUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author THC
 * @Title: HttpDownManager
 * @Package com.jointem.plugin.download
 * @Description:
 * @date 2017/4/14 9:34
 */
public class HttpDownManager {
    /*记录下载数据*/
    private Set<DownLoadInfo> downLoadInfos;
    /*回调sub队列*/
    private HashMap<String, ProgressDownSubscriber> subMap;
    /*单利对象*/
    private volatile static HttpDownManager INSTANCE;

    private Retrofit retrofit;

    private static Context sContext;


    private HttpDownManager() {
        downLoadInfos = new HashSet<>();
        subMap = new HashMap<>();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static HttpDownManager getInstance(Context context) {
        if (INSTANCE == null) {
            sContext = context;
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 开始下载
     */
    public void startDown(final DownLoadInfo downLoadInfo) {
        final DownInfo info = downLoadInfo.getDownInfo();
        /*正在下载不处理*/
        if (info == null || subMap.get(info.getUrl()) != null) {
            subMap.get(info.getUrl()).setDownLoadInfo(downLoadInfo);
            return;
        }
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(sContext, downLoadInfo);
        /*记录回调sub*/
        subMap.put(info.getUrl(), subscriber);
        /*获取service，多次请求公用一个sercie*/
        APIRequest.DownloadService httpService;
        if (!downLoadInfos.contains(downLoadInfo)) {
            downLoadInfos.add(downLoadInfo);
        }
        if (retrofit == null) {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //手动创建一个OkHttpClient并设置超时时间
            builder.connectTimeout(info.getConnectionTime(), TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);

            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(GetInterfaceConfig.getBaseUrl())
                    .build();
        }
        httpService = retrofit.create(APIRequest.DownloadService.class);

        /*得到rx对象-上一次下載的位置開始下載*/
        httpService.downLoadFile("bytes=" + info.getReadLength() + "-", info.getUrl())
//                /*指定线程*/
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(new Function<ResponseBody, Object>() {
                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {
                        try {
                            FileRequestUtil.body2File(responseBody, new File(info.getSavePath()), info);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return info;
                    }

//                    @Override
//                    public DownInfo call(ResponseBody responseBody) {
//                        try {
//                            FileRequestUtil.body2File(responseBody, new File(info.getSavePath()), info);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return info;
//                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);

    }


    /**
     * 停止下载
     */
    public void stopDown(DownLoadInfo info) {
        if (info == null) return;
        info.getDownInfo().setStateInt(DownState.STOP);
        info.getHttpDownOnNextListener().onStop();
        if (subMap.containsKey(info.getDownInfo().getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getDownInfo().getUrl());
            subscriber.dispose();
            subMap.remove(info.getDownInfo().getUrl());
        }
        /*保存数据库信息和本地文件*/
        DBRequestUtil.insertOrReplaceDownInfo(sContext, info.getDownInfo());
    }


    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    public void pause(DownLoadInfo downLoadInfo) {
        if (downLoadInfo == null) return;
        downLoadInfo.getDownInfo().setStateInt(DownState.PAUSE);
        downLoadInfo.getHttpDownOnNextListener().onPause();
        if (subMap.containsKey(downLoadInfo.getDownInfo().getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(downLoadInfo.getDownInfo().getUrl());
            subscriber.dispose();
            subMap.remove(downLoadInfo.getDownInfo().getUrl());
        }

        DBRequestUtil.insertOrReplaceDownInfo(sContext, downLoadInfo.getDownInfo());
    }

    /**
     * 停止全部下载
     */
    public void stopAllDown() {
        for (DownLoadInfo downLoadInfo : downLoadInfos) {
            stopDown(downLoadInfo);
        }
        subMap.clear();
        downLoadInfos.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (DownLoadInfo downLoadInfo : downLoadInfos) {
            pause(downLoadInfo);
        }
        subMap.clear();
        downLoadInfos.clear();
    }


    /**
     * 返回全部正在下载的数据
     *
     * @return
     */
    public Set<DownLoadInfo> getDownLoadInfos() {
        return downLoadInfos;
    }

    /**
     * 移除下载数据
     *
     * @param downLoadInfo
     */
    public void remove(DownLoadInfo downLoadInfo) {
        subMap.remove(downLoadInfo.getDownInfo().getUrl());
        downLoadInfos.remove(downLoadInfo);
    }
}
