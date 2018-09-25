package com.joketng.base.param;

import android.support.annotation.StringDef;

import com.jointem.base.param.ReqParams;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



public class ReqParamsLogin extends ReqParams {
    //客户端ID——用于好公仆登录，退出等
    private static final String CLIENT_ID = "HGP";

    private String clientId;   //客户端ID
    private String userName;   //用户名
    private String password;   //密码
    private String type;       //用户名类型(0-手机号;1-普通用户名)

    public ReqParamsLogin(String userName, String password, @UserType String type) {
        super();
        this.clientId = CLIENT_ID;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({UserType.TYPE_MOBILE, UserType.TYPE_NORMAL})
    public @interface UserType {
        String TYPE_MOBILE = "0";
        String TYPE_NORMAL = "1";
    }
}
