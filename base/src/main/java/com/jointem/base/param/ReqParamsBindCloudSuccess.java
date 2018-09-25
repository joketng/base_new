package com.jointem.base.param;

/**
 * Description: 成功绑定百度云推送后，上送数据到服务端
 * Created by Kevin.Li on 2016/1/27.
 */
public class ReqParamsBindCloudSuccess extends ReqParams {
    private String appId;
    private String userId;
    private String channelId;//渠道id

    public ReqParamsBindCloudSuccess(String appId, String userId, String channelId) {
        super();
        this.appId = appId;
        this.userId = userId;
        this.channelId = channelId;
    }

    public ReqParamsBindCloudSuccess(String channelId) {
        this.channelId = channelId;
    }
}
