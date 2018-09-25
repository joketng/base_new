package com.joketng.base.h5.h5module;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.joketng.base.R;
import com.joketng.base.h5.ant.JContext;
import com.joketng.base.h5.ant.JView;
import com.joketng.base.h5.ant.Param;
import com.jointem.base.util.IntentUtil;


/**
 * @author THC
 * @Title: convenient_life
 * @Package com.jointem.hgp.h5.h5module
 * @Description:
 * @date 2017/4/18 14:05
 */
public class convenient_life {
    public static void hideTitleRightView(@JView View contentView){
        View view = contentView.findViewById(R.id.rl_operation);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void showTitleRightView(@JView View contentView){
        View view = contentView.findViewById(R.id.rl_operation);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setTitleText(@JView View contentView, @Param("subTitle") String subTitle){
        TextView view = (TextView) contentView.findViewById(R.id.tv_sub_title);
        if (view != null) {
            view.setText(subTitle);
        }
    }

    public static void setTitleRightText(@JView View contentView, @Param("subTitle") String subTitle){
        TextView view = (TextView) contentView.findViewById(R.id.tv_operation);
        if (view != null) {
            view.setText(subTitle);
        }
    }

    public static void setTitleTextColor(@JView View contentView, @Param("titleColor") int titleColor){
        TextView view = (TextView) contentView.findViewById(R.id.tv_sub_title);
        if (view != null) {
            view.setTextColor(titleColor);
        }
    }

    public static void hideBackView(@JView View contentView){
        View view = contentView.findViewById(R.id.rl_from_other);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void showTitleView(@JView View contentView){
        View view = contentView.findViewById(R.id.rl_from_other);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void callNum(@JContext Context context, @Param("num") String number){
        IntentUtil.call(context, number);
    }
}
