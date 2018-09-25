package com.jointem.plugin.request.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author THC
 * @Title: DownloadInterceptor
 * @Package com.jointem.hgp.request.download
 * @Description:
 * @date 2017/4/13 14:45
 */
public class DownloadInterceptor implements Interceptor{
    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}
