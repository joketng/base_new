package com.joketng.base.model;

import android.content.Context;
import android.os.AsyncTask;

import com.jointem.base.util.SharedPreferencesHelper;
import com.jointem.dbhelper.GreenDaoManager;
import com.jointem.dbhelper.UserInfo;
import com.jointem.dbhelper.UserInfoDao;

import java.util.ArrayList;
import java.util.List;



public class UserInfoModelImpl implements UserInfoModel {

    private Context mContext;
    private List<String> historyAccount = new ArrayList<>();
    private final UserInfoDao helper;

    public UserInfoModelImpl(Context context) {
        mContext = context;
        helper = GreenDaoManager.getInstance(context).getDaoSession().getUserInfoDao();
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
//        HgpApplication.setUserInfo(userInfo);
        new InsertAsyncTask().execute(userInfo);
    }

    @Override
    public UserInfo queryUserInfos() {
        List<UserInfo> userInfoList = helper.queryBuilder().list();
        if (userInfoList != null && userInfoList.size() > 0)
            return userInfoList.get(0);
        return null;
    }

    @Override
    public void deleteUserInfo(String id) {
        List<UserInfo> userInfos = helper.queryBuilder().where(UserInfoDao.Properties.UserId.eq(id)).list();
        helper.deleteInTx(userInfos);
    }

    @Override
    public void cleanUserInfo() {
        helper.deleteAll();
    }

    /**
     * 开启线程在后台执行插入数据库的耗时任务
     */
    private class InsertAsyncTask extends AsyncTask<UserInfo, Integer, Void> {

        @Override
        protected Void doInBackground(UserInfo... params) {
            UserInfo userInfo = params[0];
            String userName = userInfo.getUserName();
            //数据库中没有数据则添加用户信息，有则修改用户信息
            helper.insertOrReplace(userInfo);

            List<String> accounts = (List<String>) SharedPreferencesHelper.readObject(mContext, SharedPreferencesHelper.KEY_HISTORY_ACCOUNT_INFO);
            if (accounts != null) {
                historyAccount.addAll(accounts);
            }
            if (!(historyAccount.contains(userName) && historyAccount.indexOf(userName) == 0)) {
                historyAccount.remove(userName);
                //在第一个位置插入账号，然后保存到sp文件
                historyAccount.add(0, userName);
            }
            for (int i = 5; i < historyAccount.size(); i++) {
                historyAccount.remove(i);
            }
            SharedPreferencesHelper.saveObject(mContext, SharedPreferencesHelper.KEY_HISTORY_ACCOUNT_INFO, historyAccount);
            return null;
        }
    }
}
