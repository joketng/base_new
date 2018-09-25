package com.handmark.pulltorefresh.library.divider;

import android.content.Context;

/**
 * @author THC
 * @Title: ItemDecorations
 * @Package com.jointem.zyb.view.widget.recyclerview.divider
 * @Description:
 * @date 2016/4/1 14:00
 */
public class ItemDecorations {

    public static VerticalItemDecoration.Builder vertical(Context context) {
        return new VerticalItemDecoration.Builder(context);
    }

    public static HorizontalItemDecoration.Builder horizontal(Context context) {
        return new HorizontalItemDecoration.Builder(context);
    }

    public static GridItemDecoration.Builder grid(Context context){
        return new GridItemDecoration.Builder(context);
    }

}
