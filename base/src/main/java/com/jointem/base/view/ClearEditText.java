package com.jointem.base.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin.Li
 *         Description: 带清空按钮的编辑框
 *         date 2015-10-20 下午3:25:43
 */
public class ClearEditText extends AppCompatEditText implements TextWatcher,
        OnFocusChangeListener {
    /**
     * 左右两侧图片资源
     */
    private Drawable left, right;
    /**
     * 是否获取焦点，默认没有焦点
     */
    private boolean hasFocus = false;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDatas();
    }

    private OnEtFocusChangeListener onEtFocusChangeListener;
    private OnEditTextChangeListener onEditTextChangeListener;

    public void setOnEtFocusChangeListener(
            OnEtFocusChangeListener onEtFocusChangeListener) {
        this.onEtFocusChangeListener = onEtFocusChangeListener;
    }

    public void setOnEditTextChangeListener(
            OnEditTextChangeListener onEditTextChangeListener) {
        this.onEditTextChangeListener = onEditTextChangeListener;
    }


    /**
     * 初始化数据
     */
    private void initDatas() {
        left = getCompoundDrawables()[0];
        right = getCompoundDrawables()[2];

        // 第一次显示，隐藏删除图标
        if (left != null) {
            setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        addListeners();

    }


    /**
     * 添加事件监听
     */
    private void addListeners() {
        try {
            setOnFocusChangeListener(this);
            addTextChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输入表情前的光标位置
    private int cursorPos; // 输入表情前EditText中的文本
    private String inputAfterText; // 是否重置了EditText的内容
    private boolean resetText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        if (!resetText) {
            cursorPos = start;// 这里要使用start，否则在onTextChanged中回调会报下标越界的异常
            /*
             * 这里用s.toString()而不直接用s是因为如果用s，
			 * 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
			 * inputAfterText也就改变了，那么表情过滤就失败了
			 */
            inputAfterText = s.toString();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (!resetText) {
            if (after >= 2) {// 表情符号的字符长度最小为2
                CharSequence input;
                try {
                    input = s.subSequence(cursorPos, cursorPos + after);
                } catch (IndexOutOfBoundsException e) {
                    input = "";// 如果抛出异常，说明已弃掉特殊表情
                    resetText = false;
                }
                if (handlerEmojiCharacter(input.toString())
                        || s.toString().equalsIgnoreCase("☺")) {
                    resetText = true;
                    // 是表情符号就将文本还原为输入表情符号之前的内容
                    setText(inputAfterText);
                    CharSequence text = getText();
                    if (text != null) {
                        Spannable spanText = (Spannable) text;
                        Selection.setSelection(spanText, text.length());
                    }
                }
            } else {
                if (s.toString().equalsIgnoreCase("☺")) {
                    resetText = true;
                    setText(inputAfterText);
                    CharSequence text = getText();
                    if (text != null) {
                        Spannable spanText = (Spannable) text;
                        Selection.setSelection(spanText, text.length());
                    }
                }
            }
        } else {
            resetText = false;
        }

        if (hasFocus) {
            if (TextUtils.isEmpty(s)) {
                // 如果为空，则不显示删除图标
                if (left != null) {
                    setCompoundDrawablesWithIntrinsicBounds(left, null, null,
                            null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                            null);
                }
            } else {
                // 如果非空，则要显示删除图标
                if (null == right) {
                    right = getCompoundDrawables()[2];
                }
                if (null == left) {
                    left = getCompoundDrawables()[0];
                }
                if (left != null) {
                    setCompoundDrawablesWithIntrinsicBounds(left, null, right,
                            null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, right,
                            null);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (onEditTextChangeListener != null) {
            onEditTextChangeListener.onEditTextChangeComplete(s.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 获取点击时手指抬起的X坐标
                    int xUp = (int) event.getX();
                    // 当点击的坐标到当前输入框右侧的距离小于等于getCompoundPaddingRight()的距离时，则认为是点击了删除图标
                    // getCompoundPaddingRight()的说明：Returns the right padding of the
                    // view, plus space for the right Drawable if any.
                    if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
                        if (!TextUtils.isEmpty(getText().toString())) {
                            setText("");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            this.hasFocus = hasFocus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (onEtFocusChangeListener != null) {
            onEtFocusChangeListener.onEtFocusChange(v, hasFocus);
        }
        if (!hasFocus) {
            // 如果为空，则不显示删除图标
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            if (this.getText().length() > 0) {
                // 如果非空，则要显示删除图标
                if (null == right) {
                    right = getCompoundDrawables()[2];
                }
                this.setCompoundDrawablesWithIntrinsicBounds(null, null, right,
                        null);
            }
        }
    }

    private static boolean handlerEmojiCharacter(CharSequence source) {
        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        return emojiMatcher.find();
    }

    public interface OnEtFocusChangeListener {
        void onEtFocusChange(View v, boolean hasFocus);
    }

    public interface OnEditTextChangeListener {
        /**
         * @param finalString 改变后的字符串
         */
        void onEditTextChangeComplete(String finalString);
    }
}
