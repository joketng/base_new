package com.jointem.plugin.request.download;

/**
 * @author THC
 * @Title: DownloadProgressListener
 * @Package com.jointem.hgp.request
 * @Description:
 * @date 2017/4/13 13:42
 */
public interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
