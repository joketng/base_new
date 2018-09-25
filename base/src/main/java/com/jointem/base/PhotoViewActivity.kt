package com.jointem.base

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bm.library.PhotoView
import com.bumptech.glide.Glide
import com.jointem.base.util.GlideHelper
import com.jointem.plugin.request.GetInterfaceConfig
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_photo_view.*

class PhotoViewActivity : BaseActivity() {

    val urlList = mutableListOf<String>()
    var isLocalFile = false
    var currentItem = 0

    companion object {
        val URL_LIST = "url_list"
        val LOCAL_FILE = "local_file"
        val CURRENT_ITEM = "current_item"
    }

    override fun setRootView() {
        super.setRootView()
        setContentView(R.layout.activity_photo_view)
    }

    override fun initData() {
        super.initData()
        intent.extras?.let {
            urlList.clear()
            it.getStringArrayList(URL_LIST)?.let {
                urlList.addAll(it)
            }
            isLocalFile = it.getBoolean(LOCAL_FILE,false)
            currentItem = it.getInt(CURRENT_ITEM, 0)
        }
    }

    override fun initWidget() {
        super.initWidget()
//        tvSubTitle.text = "图片"
        tv_count.text = "${currentItem + 1}/${urlList.size}"
        vp_pager.pageMargin = (resources.displayMetrics.density * 15).toInt()
        vp_pager.adapter = object : PagerAdapter(){
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun getCount(): Int {
                return urlList.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val photoview = PhotoView(activity)
                photoview.enable()
                photoview.scaleType = ImageView.ScaleType.FIT_CENTER
                photoview.setOnClickListener {
                    activity.finish()
                }
                var u = urlList[position]
                val url = urlList[position]
                if(!isLocalFile){
                    if (!url.startsWith("http")) {
                        u = if (url.startsWith("|")) {
                            GetInterfaceConfig.URL_IMAGE_SERVER + url.substring(1)
                        } else {
                            GetInterfaceConfig.URL_IMAGE_SERVER + url
                        }
                    }
                }
                Logger.d(u)
                Glide.with(context).load(url).apply(GlideHelper.requestOptions).into(photoview)
                container.addView(photoview)
                return photoview
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        vp_pager.setCurrentItem(currentItem, true)
        vp_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tv_count.text = "${position + 1}/${urlList.size}"
            }

        })
    }
}
