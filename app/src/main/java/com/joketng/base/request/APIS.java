package com.joketng.base.request;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by thc on 2017/10/12.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface APIS {
    /**
     * 登录
     */
    String LOGIN = "/public/loginValidateCaptcha";
    /**
     * 退出登录
     */
    String LOGOUT = "/driver/loginOut";
    /**
     * 注册
     */
    String REGISTER = "/public/regValidateCaptcha";

    /**
     * 获取短信验证码
     */
    String GAIN_IDENTIFY_CODE = "/getSmsCode";
    /**
     * 找回密码/忘记密码
     */
    String FIND_PASSWORD = "/resetPassword";

    /**
     * 获取验证码
     */
    String GET_SMS_CODE = "/public/sendCaptcha";
    /**
     * 检查手机是否已经注册
     */

    String IS_REGISTERED = "/public/checkMobile";

    /**
     * 文件上传
     */
    String MULTIPART_UPLOAD_SOURCES = "ydwtxf/public/batch/upload";

    /**
     * 常见问题
     */
    String COMMON_QUESTION = "/ydpublic/info/infoFaq";
    /**
     * 司机故事
     */
    String DRIVER_STORY = "/ydpublic/info/infoCommon";
    /**
     * 车辆类型
     */
    String VEHICLE_TYPE = "/driver/vehicleInfo/getAllVehicleType";

    String DRIVER_INFO_SAVE = "/driver/saveDetailsInfo";

    String OPEN_AREA = "/ydpublic/region/list";

    String APPROVAL_CHECK = "/driver/approveResult";

    String VEHICLE_FLIGHT = "/vehicleFlight/intercityCarIndex";

    String PERSONAL_CENTER = "/personal/details";

    String FLIGHT_SALES_DETAIL = "/vehicleFlight/details";

    String SUBMIT_APPROVAL = "/driver/submitApprove";

    String DEPART_THE_CAR = "/vehicleFlight/updateStatus";

    String UNCOMPLETE_ORDER = "/personal/request";

    String UNCOMPLETE_ORDER2 = "/ydpublic/ferry/index";

    String NEARBY_ORDER = "/ydpublic/ferry/orderList";

    String MY_JOURNEY2 = "/ydpublic/ferry/myOrderList";

    String ORDER_RECEIVING = "/ydpublic/ferry/receipt";

    String ORDER_START_END_CANCEL = "/ydpublic/ferry/confirm";

    String MY_WALLET = "/ydpublic/ferry/myWallet";

    String REFRESH_TOKEN = "/authorization/refreshToken";

    String UPLOAD_COORDINATE = "/vehicleFlightWarn/uploadCoordinates";

    String BIND_PUSH = "/push/binding";

    String UPDATE_VERSION = "/public/version/getUpdateInfo";

    String MESSAGE_LIST = "/driverMessage/list";

    String MESSAGE_DETAIL = "/driverMessage/details";

    String DRIVER_DAY_FLOW = "/ydpublic/ferry/myJournal";

    String DELETE_MESSAGE = "/driverMessage/delete";

    String ORDER_DETAIL = "/ydpublic/ferry/getOrderById";

    String NOTIFY_DISTANCE_DEADLINE = "/ydpublic/ferry/pushRemind";

    String MODIFY_PORTRAIT = "/personal/modifyIcon";

    String STANDAR_PROTOCOL = "/ydpublic/info/infoRule";
}
