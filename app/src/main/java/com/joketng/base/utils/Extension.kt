package com.joketng.base.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.text.format.DateFormat
import com.jointem.base.util.CommonConstants
import com.jointem.base.util.showToast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.utils.MediaStoreCompat
import org.jetbrains.anko.dip
import java.io.File
import java.util.*

/**
 * Created by thc on 2017/9/26.
 */

fun setStrictMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}

fun Activity.updatePhotos(imageFile:File){
    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(imageFile)))
}

fun Activity.takePhoto(rxPermissions: RxPermissions = RxPermissions(this), imageFile: File = File((Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DCIM + File.separator),
        "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg")): File {
    setStrictMode()
    rxPermissions.request(Manifest.permission.CAMERA).subscribe {
        if(it){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile))
            startActivityForResult(intent, CommonConstants.TAKE_PHOTO_CODE)
        } else {
            showToast("需要照相机的权限")
        }
    }
    return imageFile
}

/**
 * 图片选择
 */
fun Activity.selectImage(rxPermissions: RxPermissions = RxPermissions(this), max: Int = 1, requestCode: Int = CommonConstants.SELECT_PHOTO_CODE) {
    setStrictMode()
    rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
        if (it){
            Matisse.from(this)
                    .choose(MimeType.ofImage(), false)// 选择类型
                    .showSingleMediaType(true)
                    .countable(max != 1)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, "com.heran.ydwtx.activity.fileprovider"))
                    .maxSelectable(max)// 图片选择的最多数量
                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(dip(120))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)// 缩略图的比例
                    .imageEngine(GlideEngine())// 使用的图片加载引擎
                    .forResult(requestCode)
        } else {
            showToast("需要读写存储空间的权限")
        }
    }

}

/**
 * 拍照
 */
fun Activity.capture(requestCode: Int, mediaStoreCompat: MediaStoreCompat) {
    setStrictMode()
    Matisse.from(this)
            .choose()// 选择类型
            .captureStrategy(CaptureStrategy(true, "com.jointem.yddc.fileprovider"))
//            .capture(true)
//            .maxSelectable(1)// 图片选择的最多数量
//            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//            .gridExpectedSize(this.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
//            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//            .thumbnailScale(0.85f)// 缩略图的比例
//            .imageEngine(PicassoEngine())// 使用的图片加载引擎
            .setOnlyCapture(true)
            .setMediaStoreCompat(mediaStoreCompat)
            .forResult(requestCode)
}







