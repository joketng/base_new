package com.jointem.plugin.request.rx;


import android.text.TextUtils;

import com.jointem.plugin.request.NetConstants;

/**
 * @author THC
 * @Title: ApiException
 * @Package com.jointem.hgp.request
 * @Description:
 * @date 2017/1/17 15:07
 */
public class ApiException extends RuntimeException {
    private static String message;
    private static String code;

    public ApiException(String resultCode, String resultMessage) {
        super(getApiExceptionMessage(resultCode, resultMessage));
        code = resultCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(String code, String msg) {
        if (TextUtils.isEmpty(msg)) {
            switch (code) {
                case NetConstants.REQUEST_FAILURE:
                    message = "请求失败";
                    break;
                case NetConstants.AUTH_ERROR:
                    message = "鉴权失败";
                    break;
                case NetConstants.TOKEN_EXPIRE:
                case NetConstants.TOKEN_INVALID:
                    message = "您的账号在另一终端登录,如果不是本人操作，建议尽快修改密码,以免产生损失!";
                    break;
                default:
                    message = "未知错误";
            }
        } else {
            message = msg;
        }
        return msg;
    }
}
