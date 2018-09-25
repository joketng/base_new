package com.jointem.base.view;

import android.text.TextWatcher;

/**
 * @author Kevin.Li
 * @ClassName: BaseTextWatcher
 * @Description: TextWatcher基类
 * @date 2015-10-19 下午10:42:12
 */
public abstract class BaseTextWatcher implements TextWatcher {
    protected CustomEditText mCustomEditText;

    public BaseTextWatcher() {
        super();
    }

    public BaseTextWatcher(CustomEditText customEditText) {
        super();
        this.mCustomEditText = customEditText;
    }

    public CustomEditText getCustomEditText() {
        return mCustomEditText;
    }

    public void setCustomEditText(CustomEditText customEditText) {
        this.mCustomEditText = customEditText;
    }

}
