package com.jointem.dbhelper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by thc on 2017/10/12.
 */

@Entity
public class UserInfo {
    @Id
    private String userId;        //用户id
    private String headImg;   //头像
    private String nickName;  //用户昵称
    private String token;//令牌
    @Unique
    @NotNull
    private String userName;  //用户名
    @Generated(hash = 1572682961)
    public UserInfo(String userId, String headImg, String nickName, String token,
            @NotNull String userName) {
        this.userId = userId;
        this.headImg = headImg;
        this.nickName = nickName;
        this.token = token;
        this.userName = userName;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getHeadImg() {
        return this.headImg;
    }
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
