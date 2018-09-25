package com.handmark.pulltorefresh.library.rv;

import android.content.Context;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by wuht on 2017/5/23.
 */

public class RefreshRvUtil {
    public static void setRefreshLayout(Context context, ILoadingLayout loadingLayout) {
        loadingLayout.setPullLabel(context.getString(R.string.is_pull_down_refresh));
        loadingLayout.setReleaseLabel(context.getString(R.string.is_refresh_start));
        loadingLayout.setRefreshingLabel(context.getString(R.string.is_refreshing));
        loadingLayout.setLoadingDrawable(context.getResources().getDrawable(R.drawable.iconfont_downgrey));
    }

    public static void setLoadingLayout(Context context, ILoadingLayout loadingLayout) {
        loadingLayout.setPullLabel(context.getString(R.string.is_pull_down_load));
        loadingLayout.setReleaseLabel(context.getString(R.string.is_pull_load_more));
        loadingLayout.setRefreshingLabel(context.getString(R.string.is_loading));
        loadingLayout.setLoadingDrawable(context.getResources().getDrawable(R.drawable.iconfont_downgrey));
    }

    public static void setFinishLayout(Context context, ILoadingLayout loadingLayout) {
        loadingLayout.setReleaseLabel(context.getString(R.string.is_loaded_all));
        loadingLayout.setPullLabel(context.getString(R.string.is_loaded_all));
        loadingLayout.setRefreshingLabel(context.getString(R.string.is_loaded_all));
        loadingLayout.setLoadingDrawable(null);
    }

    public static void setFinishLayout(Context context, PullToRefreshBase recyclerView) {
        ILoadingLayout loadingLayoutProxy = recyclerView.getLoadingLayoutProxy(false, true);
        setFinishLayout(context, loadingLayoutProxy);
    }

    public static void setCustomLayout(Context context, ILoadingLayout loadingLayout, int img, String... data) {
        loadingLayout.setReleaseLabel(data[0]);
        loadingLayout.setPullLabel(data[1]);
        loadingLayout.setRefreshingLabel(data[2]);
        loadingLayout.setLoadingDrawable(context.getResources().getDrawable(img));
    }

    public static void initPullToRefreshText(Context context, PullToRefreshRecyclerView recyclerView) {
        // 设置下拉刷新的文字
        ILoadingLayout refreshLayout = recyclerView.getLoadingLayoutProxy(true, false);
        setRefreshLayout(context, refreshLayout);
        // 设置上拉加载的文字
        ILoadingLayout loadingLayout = recyclerView.getLoadingLayoutProxy(false, true);
        setLoadingLayout(context, loadingLayout);
    }

    public static void setLoadingMoreLayout(Context context, PullToRefreshRecyclerView recyclerView) {
        ILoadingLayout loadingLayoutProxy = recyclerView.getLoadingLayoutProxy(false, true);
        setLoadingLayout(context, loadingLayoutProxy);
    }

    public static void setFinishLayout(Context context, PullToRefreshRecyclerView recyclerView) {
        ILoadingLayout loadingLayoutProxy = recyclerView.getLoadingLayoutProxy(false, true);
        setFinishLayout(context, loadingLayoutProxy);
    }
}
