package com.jointem.plugin.request.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author JW.Lee
 * @Description: 获取配置文件中的配置信息
 * @date 2015-8-18 上午11:25:35
 */
public final class PropertiesUtil {
    private static Properties config = null;

    static {
        try {
            InputStream localInputStream = PropertiesUtil.class.getResourceAsStream("/assets/config.properties");
            config = new Properties();
            config.load(localInputStream);
            localInputStream.close();
        } catch (IOException localIOException) {
            Log.e("PropertiesUtil", "No config.properties defined error");
        }
    }

    public static String readValue(String paramString) {
        try {
            String str = config.getProperty(paramString);
            if (str != null)
                return str.trim();
        } catch (Exception localException) {
            Log.e("PropertiesUtil", "ConfigInfoError" + localException.toString());
        }
        return "";
    }
}