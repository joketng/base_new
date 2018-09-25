package com.joketng.base.presenter

import android.content.Context
import com.jointem.base.BaseActivity
import com.jointem.base.presenter.BasePresenter
import com.jointem.dbhelper.UserInfo
import com.jointem.plugin.request.RetrofitClient
import com.jointem.plugin.request.rx.BaseSubscriber
import com.jointem.plugin.request.rx.RxHelper
import com.joketng.base.IView.IViewLogin
import com.joketng.base.model.UserInfoModel
import com.joketng.base.model.UserInfoModelImpl
import com.joketng.base.param.ReqParamsLogin
import com.joketng.base.request.APIStores

class LoginPresenter(context: Context) : BasePresenter<IViewLogin>(context) {
    override fun initData() {
    }

    fun login(paramsLogin: ReqParamsLogin){
        RetrofitClient.getInstance().create(APIStores.UserInfoService::class.java).login(paramsLogin)
                .compose(RxHelper.handleResult<UserInfo>((context as BaseActivity).lifecycleSubject))
                .subscribe(object : BaseSubscriber<UserInfo>(context, true) {
                    override fun _onNext(userInfo: UserInfo?) {
                        userInfo?.let {
                            val userInfoModel: UserInfoModel = UserInfoModelImpl(context)
                            userInfoModel.saveUserInfo(userInfo)
                            view.loginSuccess()
                        }?:let {
                            view.loginFailure("404", "登录失败")
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        super._onError(code, message)
                        view.loginFailure(code, message)
                    }
                })
    }

}