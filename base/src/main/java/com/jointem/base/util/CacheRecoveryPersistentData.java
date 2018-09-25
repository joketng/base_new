package com.jointem.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;


/**
 * Description: 用于缓存及恢复数据
 * Created by Kevin.Li on 2015/12/11.
 */
public class CacheRecoveryPersistentData {
    /**
     * 持久化用户信息的文件名
     */
    public static final String PREF_CACHE_FILE_NAME = "cache_file_name";
    /**
     * KEY-缓存定位城市
     */
    public static final String PREF_KEY_LOCATE_CITY = "key_locate_city";
    /**
     * KEY-第一次启动标识
     */
    private static final String PREF_KEY_IS_FIRST_START = "is_first_start";

    private Context context;
    private volatile static CacheRecoveryPersistentData cacheRecoveryPersistentData;

    private CacheRecoveryPersistentData(Context context) {
        this.context = context;
    }

    public static CacheRecoveryPersistentData getCacheRecoveryPersistentData(Context context) {
        if (cacheRecoveryPersistentData == null) {
            synchronized (CacheRecoveryPersistentData.class) {
                if (cacheRecoveryPersistentData == null) {
                    cacheRecoveryPersistentData = new CacheRecoveryPersistentData(context);
                }
            }
        }
        return cacheRecoveryPersistentData;
    }

    public void saveObject(Context context, String key, Object object) {
        String strJson = new Gson().toJson(object);
        PreferenceHelper.write(context, CacheRecoveryPersistentData.PREF_CACHE_FILE_NAME, key, strJson);
    }

    public void removeObject(Context context, String key){
        PreferenceHelper.remove(context, CacheRecoveryPersistentData.PREF_CACHE_FILE_NAME, key);
    }


    public String readData(Context context, String key) {
        return PreferenceHelper.readString(context, CacheRecoveryPersistentData.PREF_CACHE_FILE_NAME, key);
    }

    public void setBind(Context context, boolean flag, String appid, String userId, String channelId) {
        String flagStr = "not";
        if (flag) {
            flagStr = "ok";
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bind_flag", flagStr);
        editor.putString("bind_appid", appid);
        editor.putString("bind_userId", userId);
        editor.putString("bind_channelId", channelId);
        editor.apply();
    }

    /**
     * 是否是第一次启动应用
     * <p>
     * author: Kevin.Li
     * created at 2016/1/7 15:38
     */
    public boolean isFirstStart() {
        boolean isFirstStart = PreferenceHelper.readBoolean(context, PREF_CACHE_FILE_NAME, PREF_KEY_IS_FIRST_START, false);
        return !isFirstStart;
    }


    /**
     * 设置第一次启动的标识为true,已启动过
     * <p>
     * author: Kevin.Li
     * created at 2016/1/7 15:42
     */
    public void setFirstStart() {
        PreferenceHelper.write(context, PREF_CACHE_FILE_NAME, PREF_KEY_IS_FIRST_START, true);
    }
}
