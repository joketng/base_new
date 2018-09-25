package com.jointem.plugin.request.download;


import com.jointem.dbhelper.DownInfo;

/**
 * @author THC
 * @Title: DownLoadInfo
 * @Package com.jointem.plugin.download
 * @Description:
 * @date 2017/4/14 11:07
 */
public class DownLoadInfo {
    private DownInfo downInfo;
    private HttpDownOnNextListener httpDownOnNextListener;

    public DownLoadInfo(DownInfo downInfo, HttpDownOnNextListener httpDownOnNextListener) {
        this.downInfo = downInfo;
        this.httpDownOnNextListener = httpDownOnNextListener;
    }

    public DownInfo getDownInfo() {
        return downInfo;
    }

    public void setDownInfo(DownInfo downInfo) {
        this.downInfo = downInfo;
    }

    public HttpDownOnNextListener getHttpDownOnNextListener() {
        return httpDownOnNextListener;
    }

    public void setHttpDownOnNextListener(HttpDownOnNextListener httpDownOnNextListener) {
        this.httpDownOnNextListener = httpDownOnNextListener;
    }
}
