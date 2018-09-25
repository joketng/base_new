package com.jointem.base.view.recyclerview.animation;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * @author THC
 * @Title: ViewHelper
 * @Package com.jointem.zyb.view.widget.recyclerview.animation
 * @Description:
 * @date 2016/3/26 9:47
 */
public final class ViewHelper {

    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null);
    }
}
