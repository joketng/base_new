package com.jointem.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * @author heyn
 */
public final class SharedPreferencesHelper {

    public static final String PREF_CACHE_FILE_NAME = "cache_file_name";
    public static final String FILENAME = "sp_file_name";
    public static final String KEY_USER_ID = "user_id";// 用户id
    public static final String BUS_TICKET_FILE = "bus_ticket_file";

    public static final String KEY = "sp_object_key";
    public static final String KEY_ONLINE = "sp_object_online_key";
    public static final String KEY_CATEGORY = "sp_object_category_key";
    public static final String KEY_ONLINE_SAVE_ID = "sp_object_online_save_id";
    public static final String KEY_ONLINE_SAVE_REGION_NAME = "sp_object_online_save_name";
    public static final String KEY_POP_NEWS_SHOW_TAGS = "key_pop_news_show_tags";
    public static final String KEY_POP_NEWS_TAGS_VERSION = "key_pop_news_tags_version";
    public static final String KEY_POP_NEWS_HIDE_TAGS = "key_pop_news_hide_tags";
    public static final String KEY_POP_NEWS_CITY_CHOSE_BEAN = "key_pop_news_city_chose_bean";
    public static final String KEY_STRATEGY_CITY_CHOSE_BEAN = "key_strategy_city_chose_bean";
    public static final String KEY_CONVENIENT_LIFE_CHOSE_BEAN = "key_convenient_life_city_chose_bean";
    public static final String KEY_POP_NEWS_CITY_ADDRESS_CODE = "key_pop_news_city_address_code";
    public static final String KEY_POP_NEWS_CITY_NAME = "key_pop_news_city_name";
    public static final String KEY_CONVENIENT_LIFE_CITY_ADDRESS_CODE = "key_convenient_life_city_address_code";
    public static final String KEY_CONVENIENT_LIFE_CITY_NAME = "key_convenient_life_city_name";
    public static final String KEY_HOME_HOT_NEWS_List = "key_home_hot_news_list";
    public static final String KEY_PHONE_INFO = "key_phone_info";
    public static final String KEY_HISTORY_ACCOUNT_INFO = "key_history_account_info";
    public static final String KEY_DYNAMIC_ALREADY_GUIDE = "key_dynamic_already_guide";
    public static final String KEY_YELLOW_PAGE_SEARCH_ALREADY_GUIDE = "key_yellow_page_search_already_guide";
    public static final String KEY_AREA_DATA_BASE_VERSION = "key_area_data_base_version";
    public static final String FILE_NAME_AREA_DATA_BASE = "file_name_area_data_base";
    public static final String KEY_APK_DOWNLOAD_PROGRESS = "apk_download_progress";// apk下载进度
    public static final String KEY_PASSENGER = "key_passenger";// 乘车人
    public static final String KEY_HISTORY_RECHARGE_NUM = "key_history_recharge_num";// 历史充值号码
    public static final String KEY_APP_VERSION_NAME = "key_app_version_name";// APP版本号

    /**
     * desc:保存对象
     *
     * @param context
     * @param key
     * @param obj     要保存的对象，只能保存实现了serializable的对象
     *                modified:
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, 0).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("", "保存obj失败");
        }
    }

    /**
     * desc:保存对象
     *
     * @param context
     * @param key
     * @param obj     要保存的对象{如果要保存的对象不是boolean/int/String类型的，则需要先实现了Serializable}
     */
    public static void saveObject(Context context, String fileName, String key, Object obj) {
        if (obj == null)
            return;
        if (obj instanceof Boolean) {
            PreferenceHelper.write(context, fileName, key, (boolean) obj);
        } else if (obj instanceof Integer) {
            PreferenceHelper.write(context, fileName, key, (int) obj);
        } else if (obj instanceof String) {
            PreferenceHelper.write(context, fileName, key, (String) obj);
        } else {
            try {
                // 保存对象
                SharedPreferences.Editor sharedata = context.getSharedPreferences(fileName, 0).edit();
                //先将序列化结果写到byte缓存中，其实就分配一个内存空间
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                //将对象序列化写入byte缓存
                os.writeObject(obj);
                //将序列化的数据转为16进制保存
                String bytesToHexString = bytesToHexString(bos.toByteArray());
                //保存该16进制数组
                sharedata.putString(key, bytesToHexString);
                sharedata.commit();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("", "save fail");
            }
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 清楚数据
     *
     * @param context
     */
    public static void clearObject(Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences(FILENAME, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(KEY);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清楚数据
     *
     * @param context
     */
    public static void clearObject(Context context, String fileName, String key) {
        try {
            SharedPreferences sp = context.getSharedPreferences(fileName, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int readInteger(Context context, String fileName, String key, int defV) {
        return PreferenceHelper.readInt(context, fileName, key, defV);
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object readObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(FILENAME, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    /**
     * 从指定文件夹读取
     */
    public static Object readObject(Context context, String fileName, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(fileName, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //所有异常返回null
        return null;
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
