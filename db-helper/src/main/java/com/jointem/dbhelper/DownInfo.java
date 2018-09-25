package com.jointem.dbhelper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author THC
 * @Title: DownInfo
 * @Package com.jointem.hgp.request.download
 * @Description:
 * @date 2017/4/13 15:07
 */
@Entity
public class DownInfo {
    @Id(autoincrement = true)
    private long id;
    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;
    /*超时设置*/
    private int connectionTime = 6;
    /*state状态数据库保存*/
    private int stateInt;
    /*url*/
    private String url;
    @Generated(hash = 1106272748)
    public DownInfo(long id, String savePath, long countLength, long readLength,
            int connectionTime, int stateInt, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectionTime = connectionTime;
        this.stateInt = stateInt;
        this.url = url;
    }
    @Generated(hash = 928324469)
    public DownInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSavePath() {
        return this.savePath;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    public long getCountLength() {
        return this.countLength;
    }
    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }
    public long getReadLength() {
        return this.readLength;
    }
    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }
    public int getConnectionTime() {
        return this.connectionTime;
    }
    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }
    public int getStateInt() {
        return this.stateInt;
    }
    public void setStateInt(int stateInt) {
        this.stateInt = stateInt;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
