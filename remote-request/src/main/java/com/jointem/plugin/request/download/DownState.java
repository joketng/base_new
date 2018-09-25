package com.jointem.plugin.request.download;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author THC
 * @Title: DownState
 * @Package com.jointem.hgp.request.download
 * @Description:
 * @date 2017/4/13 14:56
 */
@Retention(RetentionPolicy.SOURCE)
public @interface DownState {
    int START = 0;
    int DOWN = 1;
    int PAUSE = 2;
    int STOP = 3;
    int ERROR = 4;
    int FINISH = 5;
}
