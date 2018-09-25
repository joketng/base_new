package com.joketng.base.present;

import android.content.Context;

import com.jointem.base.iView.IView;
import com.jointem.base.presenter.BasePresenter;


public class WebViewPresenter extends BasePresenter<IView> {

    public WebViewPresenter(Context context) {
        super(context);
    }

    @Override
    public void initData() {

    }

    public void doVisitSite(String siteId) {
  /*      BaseActivity baseActivity = (BaseActivity) this.context;
        APIStores.APIVisitorStatistics apiVisitorStatistics = RetrofitClient.getInstance().create(APIStores.APIVisitorStatistics.class);
        apiVisitorStatistics.doVisitSite(new ReqParamsDoVisit(siteId))
                .compose(RxHelper.<Object>handleResult(baseActivity.lifecycleSubject))
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    protected void _onNext(Object obj) {

                    }
                });*/
    }

   /*   public void followSite(String siteId, String siteName, final Site site) {
      BaseActivity baseActivity = (BaseActivity) this.context;
        APIStores.FollowSite followSite = RetrofitClient.getInstance().create(APIStores.FollowSite.class);
        followSite.followSite(new ReqParamsFollowSite(this.context, siteId, siteName))
                .compose(RxHelper.<CarrierFollowSite>handleResult(baseActivity.lifecycleSubject))
                .subscribe(new BaseSubscriber<CarrierFollowSite>(context) {
                    @Override
                    protected void _onNext(CarrierFollowSite carrierFollowSite) {
                        UiUtil.showToast(context, context.getString(R.string.follow_success));
                        site.setFollow("0");
                        Event event = new Event(CommonConstants.EVENT_FOLLOW_SITE_SUCCESS, site);
                        EventBus.getDefault().post(event);
                    }
                }.showProgressDialog(context));
    }*/
}
