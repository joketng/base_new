package com.joketng.base.request;

import com.jointem.base.param.ReqParams;
import com.jointem.dbhelper.UserInfo;
import com.jointem.plugin.request.rx.BaseResponse;
import com.joketng.base.carrier.CarrierUpdate;
import com.joketng.base.param.ReqParamsBindPush;
import com.joketng.base.param.ReqParamsLogin;
import com.joketng.base.param.ReqParamsToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by thc on 2017/10/12.
 */

public class APIStores {
    public static final String WYC_PROJECT = "ydwtxf";
    public static final String WYC_PROJECT_WX = "ydwtxf/public/request";
    private static final String USER_PROJECT_TOKEN = WYC_PROJECT + "token/";

    public interface UserInfoService{
        @POST(WYC_PROJECT + com.joketng.base.request.APIS.LOGIN)
        Flowable<BaseResponse<UserInfo>> login(@Body ReqParamsLogin reqParamsLogin);

        @POST(USER_PROJECT_TOKEN + com.joketng.base.request.APIS.LOGOUT)
        Flowable<BaseResponse<Object>> logOut(@Body ReqParamsToken reqParamsToken);


    }

    public interface RegisterService {
        @POST
        Flowable<BaseResponse<Object>> bindPush(@Url String url, @Body ReqParamsBindPush reqParams);

        @POST
        Flowable<BaseResponse<CarrierUpdate>> updateVersion(@Url String url, @Body ReqParams reqParams);
    }

    public interface CommonService {
        @Multipart
        @Streaming
        @POST
        Flowable<BaseResponse<List<String>>> upLoadFiles(@Url String url, @PartMap Map<String, RequestBody> map);
    }


    }
