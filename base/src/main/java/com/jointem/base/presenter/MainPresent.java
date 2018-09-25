package com.jointem.base.presenter;

import android.content.Context;

import com.jointem.base.BaseActivity;
import com.jointem.base.iView.BaseApi;
import com.jointem.base.iView.IViewMain;
import com.jointem.base.param.ReqParamsBindCloudSuccess;
import com.jointem.plugin.request.RetrofitClient;
import com.jointem.plugin.request.rx.BaseSubscriber;
import com.jointem.plugin.request.rx.RxHelper;


/**
 * @author THC
 * @Title: MainPresent
 * @Package com.jointem.hgp.present
 * @Description:
 * @date 2017/4/19 10:31
 */
public class MainPresent extends BasePresenter<IViewMain> {
    public MainPresent(Context context) {
        super(context);
    }

    @Override
    public void initData() {

    }

    /**
     * 上送个推cid
     */
    public void sendCID(String CID) {
        final BaseActivity baseActivity = (BaseActivity) context;
        ReqParamsBindCloudSuccess reqParams = new ReqParamsBindCloudSuccess(CID);
        BaseApi.GTCidCloud pressMessage = RetrofitClient.getInstance().create(BaseApi.GTCidCloud.class);
        pressMessage.bindCloudCuccess(reqParams)
                .compose(RxHelper.<Object>handleResult(baseActivity.lifecycleSubject))
                .subscribe(new BaseSubscriber<Object>(context) {
                    @Override
                    protected void _onNext(Object obj) {
                    }
                });
    }
//
//    public void requestAreaCity() {
//        if (context instanceof BaseActivity) {
//            final BaseActivity baseActivity = (BaseActivity) context;
//            APIStores.APIAreaCity apiAreaCity = RetrofitClient.getInstance().create(APIStores.APIAreaCity.class);
//            apiAreaCity.getAreaCity()
//                    .compose(RxHelper.<CarrierAreaCity>handleResult(baseActivity.lifecycleSubject))
//                    .observeOn(Schedulers.io())
//                    .map(new Function<CarrierAreaCity, Integer>() {
//                        @Override
//                        public Integer apply(CarrierAreaCity carrierAreaCity) throws Exception {
//                            return new RequestAreaDataModel().insertAreaList(carrierAreaCity.getList());
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new BaseSubscriber<Integer>() {
//                        @Override
//                        protected void _onNext(Integer integer) {
//
//                        }
//
//                        @Override
//                        protected void _onError(String code, String message) {
//
//                        }
//                    });
//        }
////                    .subscribe(new BaseSubscriber<Integer>(baseActivity) {
////                        @Override
////                        protected void _onNext(Integer integer) {
////                            if (HgpApplication.sAddressCode.length() < 4) {
////                                LocationWrap.getInstance().setOnLocationFinishListener(new LocationWrap.OnLocationFinishListener() {
////                                    @Override
////                                    public void locationFinish(String addressCode) {
////
////                                    }
////                                });
////                                LocationWrap.getInstance().initLocation();
////                            }
////                        }
////
////                        @Override
////                        protected void _onError(String code, String message) {
////
////                        }
////                    });
////           .map(new Func1<CarrierAreaCity, Integer>() {
////            @Override
////            public Integer call(CarrierAreaCity carrierAreaCity) {
////                List<AreaCity> list = carrierAreaCity.getList();
////                AreaCityDao areaCityBeanDao = GreenDaoManager.getInstance(HgpApplication.getContextFromApplication()).getDaoSession().getAreaCityDao();
////                areaCityBeanDao.deleteAll();
////                areaCityBeanDao.insertOrReplaceInTx(list);
////                return 1;
////            }
////        }
//    }
}
