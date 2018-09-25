package com.jointem.plugin.request.rx;


import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.schedulers.Schedulers;


/**
 * @author THC
 * @Title: RxHelper
 * @Package com.jointem.hgp.request
 * @Description:
 * @date 2017/1/17 15:04
 */
public class RxHelper {
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult(BehaviorProcessor<Integer> lifecycleSubject) {
//        if (lifecycleSubject != null) {
//            final Flowable<Boolean> map = lifecycleSubject.map(new Function<Integer, Boolean>() {
//                @Override
//                public Boolean apply(Integer integer) throws Exception {
//                    return integer == 998;
//                }
//            });
//        }

        final Flowable<Boolean> map = lifecycleSubject != null ? lifecycleSubject.map(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer integer) throws Exception {
                return integer == 998;
            }
        }) : null;
        if(map != null){
            return new FlowableTransformer<BaseResponse<T>, T>() {

                @Override
                public Publisher<T> apply(Flowable<BaseResponse<T>> upstream) {
                    return upstream.flatMap(new Function<BaseResponse<T>, Publisher<T>>() {
                        @Override
                        public Publisher<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                            if (tBaseResponse.isOk()) {
                                return createData(tBaseResponse.getData());
                            } else {
                                return Flowable.error(new ApiException(tBaseResponse.getCode(), tBaseResponse.getMessage()));
                            }
                        }
                    })
                            .takeUntil(map)
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        } else {
            return new FlowableTransformer<BaseResponse<T>, T>() {

                @Override
                public Publisher<T> apply(Flowable<BaseResponse<T>> upstream) {
                    return upstream.flatMap(new Function<BaseResponse<T>, Publisher<T>>() {
                        @Override
                        public Publisher<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                            if (tBaseResponse.isOk()) {
                                return createData(tBaseResponse.getData());
                            } else {
                                return Flowable.error(new ApiException(tBaseResponse.getCode(), tBaseResponse.getMessage()));
                            }
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }


//        return new Observable.Transformer<BaseResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
//                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(BaseResponse<T> result) {
//                        if (result.isOk()) {
//                            return createData(result.getData());
//                        } else {
//                            return Observable.error(new ApiException(result.getCode()));
//                        }
//                    }
//                }).takeUntil(compareLifecycleObservable)
//                        .subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T data) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                if (data != null) {
                    e.onNext(data);
                }
                e.onComplete();
            }
        }, BackpressureStrategy.DROP);


//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                try {
//                    subscriber.onNext(data);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
    }
}
