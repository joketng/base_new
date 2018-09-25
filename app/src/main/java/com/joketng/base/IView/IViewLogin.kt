package com.joketng.base.IView

import com.jointem.base.iView.IView


interface IViewLogin: IView{

    fun loginSuccess()

    fun loginFailure(errorCode: String?, message: String?)
}