package com.jointem.base.util;

import android.os.Environment;

/**
 * @author Kevin.Li
 * @Description: 公共常量池
 * @date 2015-10-20 下午12:27:18
 */
public abstract class CommonConstants {

    public final static int TAKE_PHOTO_CODE = 100;
    public final static int SELECT_PHOTO_CODE = 101;
    public final static int SCAN_CODE = 102;
    public final static int FACE_IDENTIFICATION = 103;
    public final static String TIME_START = " 00:00:00";
    public final static String TIME_END = " 23:59:59";

    public final static String BASE_URL_WEB = "http://www.ydwtx.com/ydwx/#/informationDetail/";


    /* *EventBus事件标识**/
    /**e
     * 网络正常
     */
    public static final String EVENT_NET_WORK_ON = "event_net_work_on";
    /**
     * 网络不可用
     */
    public static final String EVENT_NET_WORK_OFF = "event_net_work_off";
    /**
     * 仅wifi
     */
    public static final String ONLY_WIFI = "only_wifi";
    /**
     * 设置
     */
    public static final String SETTING_FILE = "setting_preference";
    /**
     * 好公仆图片拍照图片保存地址
     */
    public static final String PICTURE_PATH = Environment.getExternalStorageDirectory().getPath() + "/wyc_cache";

    public static final String EVENT_FOLLOW_SITE_SUCCESS = "event_follow_site_success";
    /**
     * 位置信息权限
     */
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";

    public static abstract class string {
        //标识
        public static final String JAVASCRIPT_INTERFACE = "ZybJSInterface";
        public static final String POP_NEWS_TAB_FLAG = "popNewsTabs";
        public static final String POP_NEWS_SEARCH_FLAG = "popNewsSearch";
        public static final String SERVER_GUIDE_SEARCH_FLAG = "server_guide_search_flag";
        public static final String SEARCH_FLAG = "searchFlag";
        public static final String KEY = "key";
        public static final String CODE = "code";
        //权限
        public static final String TAG_PERMISSION_LOCATION = "tag_location_permission";
        public static final String TAG_PERMISSION_READ_PHONE = "tag_read_phone_permission";
        public static final String TAG_PERMISSION_WRITE_CONTACT = "tag_white_contact_permission";
        public static final String TAG_PERMISSION_READ_CONTACT = "tag_read_contact_permission";
        public static final String TAG_PERMISSION_CAMERA = "tag_camera_permission";
        public static final String TAG_PERMISSION_READ_STORAGE = "tag_read_storage_permission";
        public static final String TAG_PERMISSION_WRITE_STORAGE = "tag_write_storage_permission";
    }
}
