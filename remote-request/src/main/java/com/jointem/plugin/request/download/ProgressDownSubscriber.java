package com.jointem.plugin.request.download;

import android.content.Context;

import com.jointem.plugin.request.util.DBRequestUtil;

import java.lang.ref.SoftReference;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * @author THC
 * @Title: ProgressDownSubscriber
 * @Package com.jointem.hgp.request.download
 * @Description:
 * @date 2017/4/13 15:05
 */
public class ProgressDownSubscriber<T> extends ResourceSubscriber<T> implements DownloadProgressListener {
    //弱引用结果回调
    private SoftReference<HttpDownOnNextListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownLoadInfo downLoadInfo;

    private Context context;


    public ProgressDownSubscriber(Context context, DownLoadInfo downLoadInfo) {
        this.context = context;
        this.downLoadInfo = downLoadInfo;
        this.mSubscriberOnNextListener = new SoftReference<>(this.downLoadInfo.getHttpDownOnNextListener());
    }


    public void setDownLoadInfo(DownLoadInfo downLoadInfo) {
        this.downLoadInfo = downLoadInfo;
        this.mSubscriberOnNextListener = new SoftReference<>(downLoadInfo.getHttpDownOnNextListener());
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        request(1);
        downLoadInfo.getDownInfo().setStateInt(DownState.START);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
    }



    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        HttpDownManager.getInstance(context).remove(downLoadInfo);
        downLoadInfo.getDownInfo().setStateInt(DownState.FINISH);
        DBRequestUtil.updateDownInfo(context, downLoadInfo.getDownInfo());
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        HttpDownManager.getInstance(context).remove(downLoadInfo);
        downLoadInfo.getDownInfo().setStateInt(DownState.ERROR);
        DBRequestUtil.updateDownInfo(context, downLoadInfo.getDownInfo());
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        request(1);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    @Override
    public void update(long read, long count, boolean done) {
        if (downLoadInfo.getDownInfo().getCountLength() > count) {
            read = downLoadInfo.getDownInfo().getCountLength() - count + read;
        } else {
            downLoadInfo.getDownInfo().setCountLength(count);
        }
        downLoadInfo.getDownInfo().setReadLength(read);
        if (mSubscriberOnNextListener.get() != null) {
            /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
            Flowable.just(read).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                      /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
                            if (downLoadInfo.getDownInfo().getStateInt() == DownState.PAUSE || downLoadInfo.getDownInfo().getStateInt() == DownState.STOP)
                                return;
                            downLoadInfo.getDownInfo().setStateInt(DownState.DOWN);
                            mSubscriberOnNextListener.get().updateProgress(aLong, downLoadInfo.getDownInfo().getCountLength());
                        }
                    });
        }
    }


}
