package com.jointem.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;


/**
 * 自定义ListView，可以自定义ListView显示的高度
 *
 * @author JW.Lee
 * @ClassName: CustomListView
 * @Description: 自定义ListView
 * @date 2015-8-4 上午9:25:18
 */
public class CustomListView extends ListView {

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        // MeasureSpec.AT_MOST);
        // super.onMeasure(widthMeasureSpec, expandSpec);
        if (maxHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println(getChildAt(0));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview停住不能滚动
                setParentScrollAble(false);
            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:


                setParentScrollAble(true);// 当手指松开时，让父ScrollView重新拿到onTouch权限

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private ScrollView parentScrollView;

    /**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     */
    private void setParentScrollAble(boolean flag) {
        parentScrollView.requestDisallowInterceptTouchEvent(!flag);// 这里的parentScrollView就是listview外面的那个scrollview
    }

    public void setScrollView(ScrollView scrollView) {
        parentScrollView = scrollView;
    }

    private int maxHeight;

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
