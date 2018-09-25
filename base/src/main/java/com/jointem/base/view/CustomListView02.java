package com.jointem.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView，解决ScrollView中嵌套ListView显示不正常的问题（1行半）
 * Description:
 * Created by Kevin.Li on 2015/12/9.
 */
public class CustomListView02 extends ListView {
    public CustomListView02(Context context) {
        super(context);
    }

    public CustomListView02(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView02(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
