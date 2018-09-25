package com.jointem.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import com.jointem.base.R;


/**
 * @author Kevin.Li
 * @ClassName: CustomEditText
 * @Description: 自定义有删除按钮的输入框，得结合{@link TextWatcher}及一个图标使用
 * @date 2015-10-20 下午3:23:42
 */
public class CustomEditText extends AppCompatEditText implements OnFocusChangeListener {
    private Drawable mRightDrawable;
    private Rect mIconBounds;
    private Paint mPaint;
    private float mult = 1.5f;
    private float add = 2.0f;

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    // 设置显示的图片资源
    @Override
    public void setCompoundDrawables(Drawable paramDrawable1,
                                     Drawable paramDrawable2, Drawable paramDrawable3,
                                     Drawable paramDrawable4) {
        if (paramDrawable3 != null) {
            this.mRightDrawable = paramDrawable3;
            this.mIconBounds = paramDrawable3.getBounds();
        } else {
            this.mRightDrawable = getResources().getDrawable(
                    R.mipmap.ic_clear);
        }
        super.setCompoundDrawables(paramDrawable1, paramDrawable2,
                paramDrawable3, paramDrawable4);
    }

    // 添加触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (mRightDrawable != null
                && paramMotionEvent.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) paramMotionEvent.getX();
            int width = mIconBounds.width();
            int editWidth = getWidth();
            if (x >= (editWidth - 2 * width) && x <= editWidth) {
                setText("");
                paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(paramMotionEvent);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
        Class<?> clazz = watcher.getClass();
        if (BaseTextWatcher.class.isAssignableFrom(clazz)) {
            BaseTextWatcher baseTextWatcher = (BaseTextWatcher) watcher;
            CustomEditText customEditText = baseTextWatcher.getCustomEditText();
            if (customEditText == null || !customEditText.equals(this)) {
                baseTextWatcher.setCustomEditText(this);
            }
        }
    }

    // 初始化edittext 控件
    private void init() {
        setEditTextDrawable();
        paintUnderLine(Color.GRAY);
    }

    // 控制图片的显示
    public void setEditTextDrawable() {
        if (length() == 0) {
            setCompoundDrawables(null, null, null, null);
        } else {
            setCompoundDrawables(null, null, this.mRightDrawable, null);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.mRightDrawable = null;
        this.mIconBounds = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFucus) {
        if (hasFucus) {
            paintUnderLine(Color.BLUE);
            setClearIconVisible(getText().length() > 0);
        } else {
            paintUnderLine(Color.GRAY);
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mRightDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private void paintUnderLine(int color) {
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        this.setLineSpacing(add, mult);
        this.setLines(1);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
                this.getHeight() - 1, mPaint);
    }
}
