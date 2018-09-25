package com.jointem.base.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jointem.base.BaseApplication;
import com.jointem.base.R;
import com.jointem.base.bean.Event;
import com.jointem.base.util.CommonConstants;
import com.jointem.plugin.request.util.NetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tangchuntao on 16/9/19.
 * 网络和定位提示条
 */
public class TipsBar extends LinearLayout implements View.OnClickListener {

    public static final int NET = 0;
    public static final int LOCATION = 1;
    public static final int RELOCATION = 3;

    private Context context;
    private View vNetBar;
    private TextView tvLocationBar;
    private TextView tvRelocation;
    private ImageView ivRelocation;
    private LinearLayout llRelocationBar;
    private OnClickListener onClickListener;
    private TranslateAnimation mShowAnimation, mHideAmimation;
    private AnimationDrawable reLocationAnimaitonDrawable;
    private boolean IsMainActivity;

    public TipsBar(Context context) {
        super(context);
        this.context = context;
        inflate();
    }

    public TipsBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate();
    }

    public TipsBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate();
    }

    private void inflate() {
        LayoutInflater.from(context).inflate(R.layout.v_tips_net_and_location, this, true);
        onInflateFinished();

    }

    private void onInflateFinished() {
        vNetBar = this.findViewById(R.id.ll_net_off);
        tvLocationBar = (TextView) this.findViewById(R.id.tv_server_guide_switch_city);
        llRelocationBar = (LinearLayout) this.findViewById(R.id.ll_relocation);
        tvRelocation = (TextView) this.findViewById(R.id.tv_relocation);
        ivRelocation = (ImageView) this.findViewById(R.id.iv_relocation);

        vNetBar.setVisibility(GONE);
        vNetBar.setOnClickListener(this);
        tvLocationBar.setVisibility(GONE);
        tvLocationBar.setOnClickListener(this);
        llRelocationBar.setVisibility(GONE);
        llRelocationBar.setOnClickListener(this);

        ivRelocation.setImageResource(R.drawable.progressbar);
        reLocationAnimaitonDrawable = (AnimationDrawable) ivRelocation.getDrawable();


        mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
        );
        mShowAnimation.setDuration(800);

        mHideAmimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
        );
        mHideAmimation.setDuration(800);
        if (!NetUtil.isNetworkConnected(context)) {
            vNetBar.setVisibility(VISIBLE);
            tvLocationBar.setVisibility(GONE);
            llRelocationBar.setVisibility(GONE);
            BaseApplication.isNetWork = false;
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 设置显示
     *
     * @param which 0-显示网络，1-显示定位 ，2-都不显示,3- 定位失败，点击重新定位
     * @param text  当显示定位时，定位的城市名称
     */
    public void setVisibility(int which, String text) {
        switch (which) {
            case NET:
                break;
            case LOCATION:
                if (vNetBar.getVisibility() != VISIBLE) {
                    if (llRelocationBar.getVisibility() == VISIBLE) {
                        llRelocationBar.startAnimation(mHideAmimation);
                        llRelocationBar.setVisibility(GONE);
                    }
                    tvLocationBar.startAnimation(mShowAnimation);
                    tvLocationBar.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(text)) {
                        tvLocationBar.setText(String.format(context.getString(R.string.switch_city), text));
                    }
                }

                break;
            case 2:
                if (tvLocationBar.getVisibility() == VISIBLE) {
                    tvLocationBar.startAnimation(mHideAmimation);
                    tvLocationBar.setVisibility(GONE);
                } else if (llRelocationBar.getVisibility() == VISIBLE) {
                    llRelocationBar.startAnimation(mHideAmimation);
                    llRelocationBar.setVisibility(GONE);
                }
                break;
            case RELOCATION:
                if (vNetBar.getVisibility() != VISIBLE) {
                    if (tvLocationBar.getVisibility() == VISIBLE) {
                        tvLocationBar.startAnimation(mHideAmimation);
                        tvLocationBar.setVisibility(GONE);
                    }
                    llRelocationBar.startAnimation(mShowAnimation);
                    llRelocationBar.setVisibility(VISIBLE);
                    tvRelocation.setText("定位失败,点击重新定位");
                    reLocationAnimaitonDrawable.stop();
                    ivRelocation.setVisibility(INVISIBLE);
                }
                break;
        }
    }

    public interface OnClickListener {
        /**
         * 点击事件
         *
         * @param which 哪一个bar被点击，0-网络条，1-切换定位城市，3-点击重新定位
         */
        void onClick(int which);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event event) {
        if (event == null) {
            return;
        }
        switch (event.getEventFlag()) {
            case CommonConstants.EVENT_NET_WORK_ON:
                vNetBar.setVisibility(GONE);
                BaseApplication.isNetWork = true;
                break;
            case CommonConstants.EVENT_NET_WORK_OFF:
                vNetBar.setVisibility(VISIBLE);
                tvLocationBar.setVisibility(GONE);
                llRelocationBar.setVisibility(GONE);
                BaseApplication.isNetWork = false;
                break;
        }
    }

//    public <T> void setNetData(List<T> itemList) {
//        List items = itemList;
//    }

    /**
     * 判断选择城市与所在城市是否一样，不一样时提示跳转，一样时不显示横条
     *
     * @param locationCityName 定位到的城市
     * @param choseCityName    当前选择的城市
     */
    public void setSwitchLocationCity(String locationCityName, String choseCityName) {
        if (!TextUtils.isEmpty(locationCityName)) {
            if (TextUtils.equals(locationCityName, choseCityName) || TextUtils.isEmpty(choseCityName)) {
                setVisibility(2, "");
            } else {
                //是否切换到当前城市
                setVisibility(1, locationCityName);
            }
        }
    }

    /**
     * 设置条的点击事件
     *
     * @param onClickListener TipsBar 的点击事件 接口
     */
    public TipsBar setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public boolean setIsMainActivity(boolean b) {
        return IsMainActivity = b;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (IsMainActivity) {
            return;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (onClickListener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.ll_net_off) {
            onClickListener.onClick(NET);
        } else if (id == R.id.tv_server_guide_switch_city) {
            onClickListener.onClick(LOCATION);
            tvLocationBar.startAnimation(mHideAmimation);
            tvLocationBar.setVisibility(GONE);//定位条点击之后自动消失
        } else if (id == R.id.ll_relocation) {
            showRelocationAnim();
            onClickListener.onClick(RELOCATION);
        }
//        switch (rCaster.cast(v.getId())) {
//            case R2.id.ll_net_off://网络条
//                onClickListener.onClick(NET);
//                break;
//            case R2.id.tv_server_guide_switch_city://定位条
//                onClickListener.onClick(LOCATION);
//                tvLocationBar.startAnimation(mHideAmimation);
//                tvLocationBar.setVisibility(GONE);//定位条点击之后自动消失
//                break;
//            case R2.id.ll_relocation:
//                showRelocationAnim();
//                onClickListener.onClick(RELOCATION);
//        }
    }

    public void showRelocationAnim() {
        tvRelocation.setText("定位中");
        ivRelocation.setVisibility(VISIBLE);
        reLocationAnimaitonDrawable.start();
    }

    /**
     * 设置重新定位后定位失败的文本
     *
     * @param text 文本
     */
    public void setReLocationBarText(String text) {
        tvRelocation.setText(text);
    }
}
