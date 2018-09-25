package com.joketng.base.request

import android.content.Context
import com.joketng.base.WYCApplication
import com.joketng.base.utils.MediaFile
import com.jointem.base.BaseActivity
import com.jointem.base.util.showToast
import com.jointem.plugin.request.GetInterfaceConfig
import com.jointem.plugin.request.RetrofitClient
import com.jointem.plugin.request.rx.RxHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import top.zibin.luban.Luban
import java.io.File
import java.util.*

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/4/13
 */
object UploadFileService {
    private val fileList = mutableListOf<File>()
    private val bodyMap = LinkedHashMap<String, RequestBody>()
    /**
     * @des 上传文件
     * @param context Activity上下文
     * @param list 文件的本地路径
     * @param callBack 上传成功或者失败的回调
     */
    fun uploadFileList(context: Context, list: List<String>, callBack: (isSuccess: Boolean, imgList: MutableList<String>, errorMessage: String) -> Unit) {
        fileList.clear()
        if (list.isEmpty()) {
            context.showToast("上传文件为空")
        } else {
            if (context is BaseActivity) {
                context.showRoundProcessDialog(true)
            }
            bodyMap.clear()
            Observable.fromIterable(list).buffer(list.size).map {
                it.forEachIndexed { index, s ->
                    if(MediaFile.isImageFileType(s)){
                        fileList.add(Luban.with(context).load(s).get(s))
                    } else {
                        fileList.add(File(s))
                    }
                }

                bodyMap["mobile"] = RequestBody.create(MediaType.parse("text/plain"), WYCApplication.getUserInfo().mobile)
                fileList.forEach {
                    bodyMap["file\";filename=\"fire_${it.name}"] =
                            RequestBody.create(MediaType.parse("application/otcet-stream"), it)
                }
                fileList
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        if (context is BaseActivity) {
                            context.dismissProcessDialog()
                        }
                    }.subscribe {

                        RetrofitClient.getInstance().create(APIStores.CommonService::class.java).upLoadFiles(GetInterfaceConfig.getBaseUrl() + APIS.MULTIPART_UPLOAD_SOURCES + "?mobile=" + WYCApplication.getUserInfo().mobile, bodyMap)
                                .compose(RxHelper.handleResult((context as BaseActivity).lifecycleSubject))
                                .subscribe(object : HandleSidInvalidBaseSubscriber<List<String>>(context, true) {
                                    override fun _onNext(t: List<String>?) {
                                        t?.let {
                                            callBack.invoke(true, it.toMutableList(), "上传成功")
                                        }
                                    }

                                    override fun _onError(code: String, message: String) {
                                        super._onError(code, message)
                                        callBack.invoke(false, mutableListOf(), message)
                                    }
                                })
                    }
        }


    }
}