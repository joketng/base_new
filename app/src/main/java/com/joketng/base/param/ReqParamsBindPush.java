package com.joketng.base.param;

import com.jointem.base.param.ReqParams;

/**
 * @Description:
 * @Author: joketng
 * @Email: joketng@163.com
 * @Time: 2018/7/12
 */
public class ReqParamsBindPush extends ReqParams{
    private String type;
    private String mobile;
    private String clientId;

    public ReqParamsBindPush(String type, String mobile, String clientId) {
        this.type = type;
        this.mobile = mobile;
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
