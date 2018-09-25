package com.jointem.plugin.request;

import android.content.Context;
import android.text.TextUtils;

import com.jointem.plugin.request.util.NetUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author THC
 * @Title: RetrofitClient
 * @Package com.jointem.plugin.request
 * @Description:
 * @date 2017/4/12 10:07
 */
public class RetrofitClient {
    private static Retrofit retrofit;
    private static Context sContext;
    private static boolean sLog;
    private static String accessToken = "";

    private RetrofitClient() {
    }

    private static class SingleHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingleHolder.INSTANCE;
    }

    public static void initContext(Context context, boolean isLogable){
        sContext = context;
        sLog = isLogable;
    }

    public static void initToken(String token){
        accessToken = token;
    }


    private Retrofit retrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存，代码略
             */

//            Cache cache = new Cache(sContext.getCacheDir(), 10 * 1024 * 1024);


            /**
             *  公共参数，代码略
             */
//            Interceptor addQueryParameterInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request request;
////                    String method = originalRequest.method();
////                    Headers headers = originalRequest.headers();
//                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                            .addQueryParameter("system", "01")
//                            .addQueryParameter("imei", getPhoneIMEI(sContext))
//                            .addQueryParameter("currentVersion", getAppVersionName(sContext))
//                            .addQueryParameter("systemVersion", getSystemVersion())
//                            .addQueryParameter("com.jointem.hgp.userinfo.model", Build.MODEL)
//                            .build();
//                    request = originalRequest.newBuilder().url(modifiedUrl).build();
//                    return chain.proceed(request);
//                }
//            };
//            builder.addInterceptor(addQueryParameterInterceptor);

            /**
             * 设置头，代码略
             */
            Interceptor headerInterceptor = new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    if(TextUtils.isEmpty(accessToken)){
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .method(originalRequest.method(), originalRequest.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    } else {
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .header("Authorization","Bearer "+accessToken)
                                .method(originalRequest.method(), originalRequest.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }

                }
            };
            builder.addInterceptor(headerInterceptor);
//            builder.addNetworkInterceptor(getCacheInterceptor()).cache(cache).addInterceptor(getCacheInterceptor());
            /**
             * Log信息拦截器，代码略
             */
            if (sLog) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            /**
             * 设置cookie，代码略
             */

            /**
             * 设置超时和重连，代码略
             */
            builder.retryOnConnectionFailure(true).connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS);
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(GetInterfaceConfig.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public <T> T create(Class<T> tClass) {
        return retrofit().create(tClass);
    }

    public static Interceptor getCacheInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetUtil.isNetworkConnected(sContext)){
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if(NetUtil.isNetworkConnected(sContext)){
                    String cacheControl = request.cacheControl().toString();
                    return response.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
                } else {
                    return response.newBuilder().header("Cache-Control", "public,only-if-cached,max-stale=360000")
                            .removeHeader("Pragma")
                            .build();
                }

            }
        };
    }
}
