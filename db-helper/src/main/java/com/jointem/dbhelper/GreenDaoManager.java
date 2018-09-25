package com.jointem.dbhelper;

import android.content.Context;

/**
 * @author THC
 * @Title: GreenDaoManager
 * @Package com.jointem.plugin.citylist.db
 * @Description:
 * @date 2017/2/16 9:35
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance; //单例
    private DaoMaster mDaoMaster; //以一定的模式管理Dao类的数据库对象
    private DaoSession mDaoSession; //管理制定模式下的所有可用Dao对象
    private final static String dbName = "ydwtx_base.db";

    private GreenDaoManager(Context context) {
        if (mInstance == null) {
            MySQLiteOpenHelper devOpenHelper = new
                    MySQLiteOpenHelper(context, dbName, null);
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }
    public static GreenDaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager(context);
                }
            }
        }
        return mInstance;
    }
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
