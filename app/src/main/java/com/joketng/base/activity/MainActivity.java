package com.joketng.base.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jointem.base.BaseActivity;
import com.joketng.base.R;
import com.joketng.base.fragment.MessageFragment;
import com.joketng.base.fragment.MineFragment;
import com.joketng.base.fragment.WorkbenchFragment;
import com.joketng.base.utils.ViewExtensionKt;

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/5/20
 * @email: joketng@163.com
 */
public class MainActivity extends BaseActivity {
    FragmentTabHost fragmentTabHost;

    // Tab选项卡的文字
    private String[] tabText;
    // Tab选项卡的图片
    private int[] tabImg;
    // Tab选项卡的图片（选中）
    private int[] tabImgSelected;
    // Tab选项卡对应的Fragment
    private Class[] tabFragment;


    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.aty_main);
        initTab();

    }

    private void initTab() {
        tabText = context.getResources().getStringArray(R.array.main_tab_gov);
        tabImg = new int[]{R.mipmap.ic_home_n, R.mipmap.ic_convenient_life_n, R.mipmap.ic_life_n};
        tabImgSelected = new int[]{R.mipmap.ic_home_f, R.mipmap.ic_convenient_life_f, R.mipmap.ic_life_f};
        tabFragment = new Class[]{WorkbenchFragment.class, MessageFragment.class, MineFragment.class};
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.setBackgroundColor(ContextCompat.getColor(this, R.color.outbody_bottom_color));
        fragmentTabHost.getTabWidget().setDividerDrawable(null);// 隐藏分割线

        RelativeLayout tabBottom;
        TabHost.TabSpec tabSpec;
        int count = tabText.length;
        for (int i = 0; i < count; i++) {
            tabBottom = (RelativeLayout) getLayoutInflater().inflate(R.layout.v_main_bottom, null);
            ViewExtensionKt.ripple(tabBottom);
            // 为每一个Tab按钮设置图标、文字和内容
            setTabBottom(tabBottom, tabText[i], tabImg[i], ContextCompat.getColor(this, R.color.bottom_normal_color));
            tabSpec = fragmentTabHost.newTabSpec(tabText[i]).setIndicator(tabBottom);
            // 将Tab按钮添加进Tab选项卡中
            fragmentTabHost.addTab(tabSpec, tabFragment[i], null);
        }

        updateTab(fragmentTabHost);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                fragmentTabHost.setCurrentTabByTag(tabId);
                updateTab(fragmentTabHost);
            }
        });

    }

    // 设置底部选项卡
    private void setTabBottom(RelativeLayout rl, String text, int icRes, int textColor) {
        ImageView imv = (ImageView) rl.getChildAt(0);
        imv.setBackgroundResource(icRes);
        TextView tv = (TextView) rl.getChildAt(1);
        tv.setText(text);
        tv.setTextColor(textColor);
    }

    // 更新标签
    private void updateTab(FragmentTabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            RelativeLayout tabBottom = (RelativeLayout) tabHost.getTabWidget().getChildAt(i);
            if (tabHost.getCurrentTab() == i) {
                setTabBottom(tabBottom, tabText[i], tabImgSelected[i], ContextCompat.getColor(this, R.color.c_main_blue));
            } else {
                setTabBottom(tabBottom, tabText[i], tabImg[i], ContextCompat.getColor(this, R.color.bottom_normal_color));
            }
        }
    }


}
