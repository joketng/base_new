package com.joketng.base.h5;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
public class WebLinkShareListener /*implements UMShareListener*/ {
   /* private String shareType = "0";
    private Context context;
    private CallBack4H5 callBack4H5;

    public WebLinkShareListener(Context context, String shareType, CallBack4H5 callBack4H5) {
        this.context = context;
        this.shareType = shareType;
        this.callBack4H5 = callBack4H5;
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        if (callBack4H5 != null) {
            callBack4H5.onSuccess(buildJsonStr());
        }
        UiUtil.showToast(context, context.getString(R.string.share_success));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        if (callBack4H5 != null) {
            callBack4H5.onFailed(buildJsonStr());
        }
        UiUtil.showToast(context, context.getString(R.string.share_failed));
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        if (callBack4H5 != null) {
            callBack4H5.onFailed(buildJsonStr());
        }
        UiUtil.showToast(context, context.getString(R.string.share_cancel));
    }

    private String buildJsonStr() {
        Map<String, String> map = new HashMap<>();
        map.put("shareType", shareType);
        return GsonUtil.getInstance().toJsonString(map);
    }
*/
}
