package com.jointem.plugin.request.util;


import android.content.Context;

import com.jointem.dbhelper.DownInfo;
import com.jointem.dbhelper.GreenDaoManager;
import com.jointem.dbhelper.UserInfo2;

/**
 * @author THC
 * @Title: DBRequestUtil
 * @Package com.jointem.plugin.util
 * @Description:
 * @date 2017/4/14 14:46
 */
public class DBRequestUtil {
    public static void updateDownInfo(Context context, DownInfo downInfo){
        GreenDaoManager.getInstance(context).getNewSession().getDownInfoDao().update(downInfo);
    }

    public static void updateUserInfo2(Context context, UserInfo2 userInfo2){
        GreenDaoManager.getInstance(context).getNewSession().getUserInfo2Dao().update(userInfo2);
    }

    public static void insertOrReplaceDownInfo(Context context, DownInfo downInfo){
        GreenDaoManager.getInstance(context).getNewSession().getDownInfoDao().insertOrReplace(downInfo);
    }

    public static void insertDownInfo(Context context, DownInfo downInfo){
        GreenDaoManager.getInstance(context).getNewSession().getDownInfoDao().insert(downInfo);
    }

    public static void saveDownInfo(Context context, DownInfo downInfo){
        GreenDaoManager.getInstance(context).getNewSession().getDownInfoDao().save(downInfo);
    }

    public static void deleteDownInfo(Context context, DownInfo downInfo){
        GreenDaoManager.getInstance(context).getNewSession().getDownInfoDao().delete(downInfo);
    }

    public static void insertOrReplaceUserInfo2(Context context, UserInfo2 userInfo2){
        GreenDaoManager.getInstance(context).getNewSession().getUserInfo2Dao().insertOrReplace(userInfo2);
    }

    public static UserInfo2 getUserInfo2(Context context, String mobile){
        return GreenDaoManager.getInstance(context).getNewSession().getUserInfo2Dao().load(mobile);
    }

    public static void deleteUserInfo2(Context context){
         GreenDaoManager.getInstance(context).getNewSession().getUserInfo2Dao().deleteAll();
    }
}
