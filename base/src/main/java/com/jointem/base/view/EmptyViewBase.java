package com.jointem.base.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jointem.base.R;
import com.jointem.base.bean.Event;
import com.jointem.base.util.CommonConstants;
import com.jointem.plugin.request.util.NetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author THC
 * @Title: EmptyView
 * @Package com.jointem.zyb.view
 * @Description:
 * @date 2016/4/20 17:25
 */
public class EmptyViewBase extends FrameLayout {
    private Context context;
    private TextView tvEmptyView;
    private ImageView imgEmptyView;
    private View emptyContentView;
    private View netView;
    private LinearLayout llRefreshNet;
    private ImageView imgProgress;
    private boolean isHomeCase = false;
    private TextView tvOtherCity;

    public EmptyViewBase(Context context) {
        super(context);
        initView(context);
    }

    public EmptyViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
        this.emptyContentView = View.inflate(context, R.layout.v_list_data_empty, null);
        tvOtherCity = (TextView) emptyContentView.findViewById(R.id.tv_to_model_city);
        tvEmptyView = (TextView) this.emptyContentView.findViewById(R.id.tv_list_data_empty);
        imgEmptyView = (ImageView) this.emptyContentView.findViewById(R.id.img_list_data_empty);
        this.netView = View.inflate(context, R.layout.v_list_data_empty_net, null);
        llRefreshNet = (LinearLayout) netView.findViewById(R.id.ll_refresh_net);
        addView(this.netView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(this.emptyContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (!NetUtil.isNetworkConnected(context)) {
            this.setNoNet();
        } else {
            this.setHaveNet();
        }

    }

    public void setOtherCityClickListener(OnClickListener onClickListener) {
        tvOtherCity.setOnClickListener(onClickListener);
    }

    public void setOtherCityVisibility(int visibility) {
        tvOtherCity.setVisibility(visibility);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event event) {
        if (event == null) {
            return;
        }
        switch (event.getEventFlag()) {
            case CommonConstants.EVENT_NET_WORK_ON:
                emptyContentView.setVisibility(View.VISIBLE);
                netView.setVisibility(View.GONE);
                break;
            case CommonConstants.EVENT_NET_WORK_OFF:
                emptyContentView.setVisibility(View.GONE);
                netView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setNoNet() {
        emptyContentView.setVisibility(View.GONE);
        netView.setVisibility(View.VISIBLE);
    }

    public void setOnClickListener(@NonNull OnClickListener onClickListener) {
        emptyContentView.setOnClickListener(onClickListener);
        llRefreshNet.setOnClickListener(onClickListener);
    }

    public void setHaveNet() {
        emptyContentView.setVisibility(View.VISIBLE);
        netView.setVisibility(View.GONE);
    }

    public void setEmptyText(String text) {
        tvEmptyView.setText(text);
    }

    public void setEmptyImageResource(@DrawableRes int resource) {
        imgEmptyView.setImageResource(resource);
    }

    public void setProgressVisible(int visible) {
        imgProgress.setVisibility(visible);
    }

    public void setIsHomeCase(boolean b) {
        isHomeCase = b;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isHomeCase) {//在 MainActivity 里面设置 isHomeCase 为 true，否则 FragmentTabHost 工作不正常
            return;
        }
        EventBus.getDefault().unregister(this);
    }

}
