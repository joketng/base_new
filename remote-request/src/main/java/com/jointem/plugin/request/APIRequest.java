package com.jointem.plugin.request;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * @author THC
 * @Title: APIRequest
 * @Package com.jointem.plugin.request
 * @Description:
 * @date 2017/4/13 16:59
 */
public class APIRequest {
    public interface DownloadService {
        @Streaming
        @GET
        Flowable<ResponseBody> downLoadCitySql(@Url String url);

        @Streaming
        @GET
        Flowable<ResponseBody> downLoadFile(@Header("Range") String start, @Url String url);
    }
}
