package com.jointem.plugin.request;

import android.text.TextUtils;

import com.jointem.plugin.request.util.PropertiesUtil;

/**
 * @author THC
 * @Title: GetInterfaceConfig
 * @Package com.jointem.plugin
 * @Description:
 * @date 2017/4/12 10:21
 */
public class GetInterfaceConfig {

    private final static String URL1 = getMicroWebsiteIp("http", "server_ip", "server_port");
    public final static String USER_INFO_URL = getMicroWebsiteIp("http", "user_info_ip", "server_port");
    // 图片服务器地址前缀
    public final static String URL_IMAGE_SERVER = getMicroWebsiteIp("http", "img_ip", "");

    public static String getMicroWebsiteIp(String protocol, String paramString, String portName) {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol).append("://");
        if (paramString != null && !paramString.equals("")) {
            sb.append(PropertiesUtil.readValue(paramString));
        }
        if (portName != null && !portName.equals("")) {
            sb.append(getPort(portName));
        }
        sb.append("/");
        return sb.toString();
    }

    /**
     * 获取端口
     */
    private static String getPort(String portName) {
        String port = PropertiesUtil.readValue(portName);
        return TextUtils.isEmpty(port) ? "" : ":" + port;
    }

    private static boolean isReleaseEnvironment(){
        String isReleaseEnvironment = getEnvironment("isReleaseEnvironment");
        return TextUtils.equals(isReleaseEnvironment, "true");
    }

    private static String getEnvironment(String property){
        String readValue = PropertiesUtil.readValue(property);
        return TextUtils.isEmpty(readValue) ? "" : readValue;
    }

    public static boolean isReleaseEnvironment = isReleaseEnvironment();

    public static String getBaseUrl() {
        return URL1;
    }

    public static String getUserInfoUrl() {
        return USER_INFO_URL;
    }

}
