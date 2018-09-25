package com.joketng.base.h5.h5module;


/**
 * @author THC
 * @Title: payment_module
 * @Package com.jointem.hgp.h5.h5module
 * @Description:
 * @date 2017/5/8 16:12
 */
public class payment_module {
   /* public static void aliPay(@JContext Context context, @Param("aliPayOrderInfo") String aliPayOrderInfo, @JCallBack final CallBack4H5 callBack4H5){
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            PayProxy payProxy = new PayProxy(activity, PayProxy.PayType.ALI_PAY);
            payProxy.setOnPayListener(new PayHelper.PayListener() {
                @Override
                public void onSuccess(String jsonParams) {
                    Logger.d(jsonParams);
                    callBack4H5.onSuccess(jsonParams);
                }

                @Override
                public void onError(String jsonParams) {
                    callBack4H5.onFailed(jsonParams);
                }
            });
            payProxy.pay(aliPayOrderInfo, null);
        }
    }

    public static void wxPay(@JContext Context context, @Param("wxPayOrderInfo") Object object, @JCallBack final CallBack4H5 callBack4H5){
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            PayProxy payProxy = new PayProxy(activity, PayProxy.PayType.WX_PAY);
            payProxy.setOnPayListener(new PayHelper.PayListener() {
                @Override
                public void onSuccess(String jsonParams) {
                    Logger.d(jsonParams);
                    callBack4H5.onSuccess(jsonParams);
                }

                @Override
                public void onError(String jsonParams) {
                    callBack4H5.onFailed(jsonParams);
                }
            });
            WxPayOrderInfo wxPayOrderInfo = GsonUtil.getInstance().changeGsonToBean(object.toString(), WxPayOrderInfo.class);
            payProxy.pay(null, wxPayOrderInfo);
        }
    }*/

}
