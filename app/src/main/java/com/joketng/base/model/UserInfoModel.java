package com.joketng.base.model;

import com.jointem.dbhelper.UserInfo;



public interface UserInfoModel {
    /**
     * 保存或者更新账户信息
     */
    void saveUserInfo(UserInfo userInfo);

    /**
     * 查询账户信息
     */
    UserInfo queryUserInfos();

    /**
     * 删除账户信息
     */
    void deleteUserInfo(String id);

    /**
     * 清空所有用户的信息
     */
    void cleanUserInfo();
}
