package com.jointem.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.jointem.base.R;


/**
 * Created by THC on 2015/12/8.
 */
public class DrawableBoundsSetTextView extends android.support.v7.widget.AppCompatTextView {

    private boolean isCenter;
    private int targetDrawable;

    public DrawableBoundsSetTextView(Context context) {
        super(context);
    }

    public DrawableBoundsSetTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableBoundsSetTextView);
        int drawableWidth = typedArray.getDimensionPixelOffset(R.styleable.DrawableBoundsSetTextView_drawable_width, 0);
        int drawableHeight = typedArray.getDimensionPixelOffset(R.styleable.DrawableBoundsSetTextView_drawable_height, 0);
        this.isCenter = typedArray.getBoolean(R.styleable.DrawableBoundsSetTextView_drawable_center, false);
        int drawableOrientation = typedArray.getInt(R.styleable.DrawableBoundsSetTextView_drawable_orientations, 4);
        targetDrawable = drawableOrientation;
        Drawable drawable;
        switch (drawableOrientation) {
            case 0:
                drawable = getCompoundDrawables()[0];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                    setCompoundDrawables(drawable, null, null, null);
                }
                break;
            case 1:
                drawable = getCompoundDrawables()[1];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                    setCompoundDrawables(null, drawable, null, null);
                }
                break;
            case 2:
                drawable = getCompoundDrawables()[2];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                    setCompoundDrawables(null, null, drawable, null);
                }
                break;
            case 3:
                drawable = getCompoundDrawables()[3];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                    setCompoundDrawables(null, null, null, drawable);
                }
                break;
            default:
                break;
        }
        typedArray.recycle();
    }

    public DrawableBoundsSetTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCenter) {
            Drawable[] drawables = getCompoundDrawables();
            switch (targetDrawable){
                case 0:
                    Drawable drawableLeft = drawables[targetDrawable];
                    if (drawableLeft != null) {
                        float textWidth = getPaint().measureText(getText().toString());
                        int drawablePadding = getCompoundDrawablePadding();
                        int drawableWidth = 0;
                        drawableWidth = drawableLeft.getIntrinsicWidth();
                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
                        canvas.translate((getWidth() - bodyWidth) / 2, 0);
                    }
                    break;
                case 2:
                    Drawable drawableRight = drawables[targetDrawable];
                    if (drawableRight != null) {
                        float textWidth = getPaint().measureText(getText().toString());
                        int drawablePadding = getCompoundDrawablePadding();
                        int drawableWidth = 0;
                        drawableWidth = drawableRight.getIntrinsicWidth();
                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
                        setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                        canvas.translate((getWidth() - bodyWidth) / 2, 0);
                    }
                    break;
            }

        }
        super.onDraw(canvas);
    }
}
