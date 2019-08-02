package com.creditease.ctextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhxh on 2019/06/25
 */
public final class CTextView extends TextView {

    GradientDrawable gradientDrawable;

    //关键属性设置
    private int cSolidColor = Color.TRANSPARENT;
    private int strokeColor = Color.TRANSPARENT;
    private int pressedColor = Color.TRANSPARENT;
    private int pressedTextColor = Color.TRANSPARENT;
    private int clickTextColor = Color.TRANSPARENT;
    private int angleCorner = 0;
    private int strokeWidth = 0;

    private int defaultTextColor;

    private int drawableWidth;
    private DrawablePosition position;

    Rect bounds;
    int drawablePadding = 0;


    private int degrees;

    enum DrawablePosition {
        NONE,
        LEFT_AND_RIGHT,
        LEFT,
        RIGHT
    }

    public CTextView(Context context) {
        this(context, null);
    }

    public CTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, AttributeSet attrs) {
        this.setEnabled(false); //默认不可用 当注册点击事件
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CTextView);
        pressedColor = a.getColor(R.styleable.CTextView_CPressedColor, pressedColor);
        pressedTextColor = a.getColor(R.styleable.CTextView_CPressedTextColor, pressedTextColor);
        clickTextColor = a.getColor(R.styleable.CTextView_CClickTextColor, clickTextColor);
        cSolidColor = a.getColor(R.styleable.CTextView_CSolidColor, cSolidColor);
        strokeColor = a.getColor(R.styleable.CTextView_CStrokeColor, strokeColor);
        angleCorner = a.getDimensionPixelSize(R.styleable.CTextView_CAngleCorner, angleCorner);
        strokeWidth = a.getDimensionPixelSize(R.styleable.CTextView_CStrokeWidth, strokeWidth);
        drawablePadding = a.getDimensionPixelSize(R.styleable.CTextView_CDrawablePadding, drawablePadding);
        degrees = a.getDimensionPixelSize(R.styleable.CTextView_CRotateDegree, degrees);

        defaultTextColor = this.getCurrentTextColor();

        if (pressedTextColor == Color.TRANSPARENT) {
            pressedTextColor = defaultTextColor;
        }
        if (clickTextColor == Color.TRANSPARENT) {
            clickTextColor = defaultTextColor;
        }

        if (pressedColor == Color.TRANSPARENT) {
            if (cSolidColor != Color.TRANSPARENT) {
                pressedColor = cSolidColor;
            }
        }

        if (drawablePadding != 0) {
            this.setGravity(Gravity.CENTER);
        }

        if (null == bounds) {
            bounds = new Rect();
        }
        if (null == gradientDrawable) {
            gradientDrawable = new GradientDrawable();
        }

        setDrawablePadding(drawablePadding);
        setBtnDrawable();

        //TODO 设置按钮点击之后的颜色更换 去掉按压效果
        //setOnTouchListener((arg0, event) -> {
        //    setBackgroundDrawable(gradientDrawable);
        //    return setColor(event.getAction());
        //});

        //资源回收
        a.recycle();
    }


    int freezeTime = 500;

    public void setFreezeTime(int freezeTime) {
        this.freezeTime = freezeTime;
    }

    @Override
    public boolean performClick() {
        if (BtnUtils.isQuickClick(freezeTime)) {
            return true;
        }
        return super.performClick();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();
        int factor = (position == DrawablePosition.LEFT_AND_RIGHT) ? 2 : 1;
        int contentWidth = drawableWidth + drawablePadding * factor + textWidth;
        int horizontalPadding = (int) ((getWidth() / 2.0) - (contentWidth / 2.0));

        setCompoundDrawablePadding(-horizontalPadding + drawablePadding);

        switch (position) {
            case LEFT:
                setPadding(horizontalPadding, getPaddingTop(), getPaddingRight(), getPaddingBottom());
                break;

            case RIGHT:
                setPadding(getPaddingLeft(), getPaddingTop(), horizontalPadding, getPaddingBottom());
                break;

            case LEFT_AND_RIGHT:
                setPadding(horizontalPadding, getPaddingTop(), horizontalPadding, getPaddingBottom());
                break;

            default:
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }


    //重新设置位置
    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);

        if (left != null && right != null) {
            drawableWidth = left.getIntrinsicWidth() + right.getIntrinsicWidth();
            position = DrawablePosition.LEFT_AND_RIGHT;
        } else if (left != null) {
            drawableWidth = left.getIntrinsicWidth();
            position = DrawablePosition.LEFT;
        } else if (right != null) {
            drawableWidth = right.getIntrinsicWidth();
            position = DrawablePosition.RIGHT;
        } else {
            position = DrawablePosition.NONE;
        }
        requestLayout();
    }


    public void setGradientColor(GradientDrawable.Orientation orientation, int startColor, int endColor) {
        gradientDrawable.setColors(new int[]{startColor, endColor});
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        gradientDrawable.setOrientation(orientation);
    }

    public GradientDrawable getGradientDrawable() {
        return gradientDrawable;
    }


    //除去Angle还原为默认
    public void resetExAngle() {
        pressedColor = Color.TRANSPARENT;
        cSolidColor = Color.TRANSPARENT;
        strokeColor = Color.TRANSPARENT;

        strokeWidth = 0;
    }

    public void setDrawablePadding(int padding) {
        drawablePadding = padding;
        requestLayout();
    }

    private void setBtnDrawable() {
        //设置按钮颜色
        gradientDrawable.setColor(cSolidColor);
        //设置按钮的边框宽度
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        //设置按钮圆角大小
        gradientDrawable.setCornerRadius(angleCorner);
        setBackgroundDrawable(gradientDrawable);
    }


    //处理按下去的颜色 区分solid和stroke模式
    public boolean setColor(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                gradientDrawable.setColor(pressedColor);
                this.setTextColor(pressedTextColor);
                break;
            case MotionEvent.ACTION_UP:
                gradientDrawable.setColor(cSolidColor);
                this.setTextColor(defaultTextColor);
                break;
            case MotionEvent.ACTION_CANCEL:
                gradientDrawable.setColor(cSolidColor);
                this.setTextColor(defaultTextColor);
                break;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float lastDegrees = degrees % 360;//优化大于360度情况

        if (lastDegrees != 0) {
            canvas.rotate((-lastDegrees), getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }
        super.onDraw(canvas);
    }

    private String richTextSrc = "";
    private String richTextReg = "";
    private int richValueColor;
    private int richValueSize;


    public CTextView withText(String richTextSrc) {
        this.richTextSrc = richTextSrc;
        return this;
    }

    public CTextView withReset() {
        richValueColor = 0;
        richValueSize = 0;
        return this;
    }

    public CTextView withTextColor(int textColor) {
        this.setTextColor(textColor);
        return this;
    }

    public CTextView withRegex(String richTextReg) {
        this.richTextReg = richTextReg;
        return this;
    }

    public CTextView withColor(int richValueColor) {
        this.richValueColor = richValueColor;
        return this;
    }

    public CTextView withSize(int richValueSize) {
        this.richValueSize = richValueSize;
        return this;
    }

    public void withBack(ClickAction cb) {
        this.setEnabled(true);
        setSpecialText(richTextSrc, richTextReg, richValueColor, richValueSize, cb);
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        this.setEnabled(true);
        super.setOnClickListener(l);
    }

    //根据正则用来 处理特殊字符串的特殊颜色或大小及点击事件
    public void setSpecialText(String richTextSrc, String richTextReg, int richValueColor, int richValueSize, ClickAction cb) {
        if (isEmpty(richTextSrc)) {
            return;
        }
        if (isEmpty(richTextReg)) {
            this.setText(richTextSrc);
            return;
        }
        if (richValueColor == 0) {
            richValueColor = this.getCurrentTextColor();
        }
        SpannableString resultSpan = new SpannableString(richTextSrc);

        Pattern p = Pattern.compile(richTextReg);
        Matcher m = p.matcher(richTextSrc);

        if (m == null) {
            this.setText(richTextSrc);
            return;
        }

        while (m.find()) {

            if (m.start() < 0) {
                continue;
            }
            resultSpan.setSpan(new ForegroundColorSpan(richValueColor),
                    m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (richValueSize != 0) {
                resultSpan.setSpan(new AbsoluteSizeSpan(richValueSize, true), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (cb != null) {
                int finalRichValueColor = richValueColor;
                resultSpan.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(finalRichValueColor);
                        ds.setUnderlineText(false);
                        ds.clearShadowLayer();
                    }

                    @Override
                    public void onClick(View widget) {

                        CTextView.this.setHighlightColor(Color.TRANSPARENT);
                        cb.onClick(m.group());

                    }
                }, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (cb != null) {
            this.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.setText(resultSpan);
        return;
    }


    public void setMaxLineText(String srcStr, int maxLines, ClickAction greater, ClickAction lesser) {
        this.setText(srcStr);
        this.setMaxLines(maxLines);
        this.post(new Runnable() {
            @Override
            public void run() {
                if (getLineCount() > maxLines) {
                    greater.onClick(String.valueOf(getLineCount()));
                } else {
                    lesser.onClick(String.valueOf(getLineCount()));
                }
            }
        });
    }


    public void initBtnAttr(int solidColor, int strokeColor, int pressedColor, int pressedTextColor, int angleCorner, int strokeWidth) {
        this.cSolidColor = solidColor;
        this.strokeColor = strokeColor;
        this.pressedColor = pressedColor;
        this.pressedTextColor = pressedTextColor;
        this.angleCorner = angleCorner;
        this.strokeWidth = strokeWidth;
        setBtnDrawable();
    }


    //初始化 默认
    public void initSolidArr(int textColor, int solidColor, int angleCorner) {
        resetExAngle();
        initBtnAttr(solidColor, strokeColor, solidColor, textColor, angleCorner, strokeWidth);
        setTextColor(textColor);
    }


    //初始化空心的
    public void initStrokeAttr(int textColor, int strokeColor, int pressedColor, int strokeWidth, int angleCorner) {
        resetExAngle();
        initBtnAttr(cSolidColor, strokeColor, pressedColor, pressedTextColor, angleCorner, strokeWidth);
        setTextColor(textColor);
    }

    //stockColor等于textColor等于pressColor等于pressTextColor
    public void initStrokeAttr(int strokeColor, int strokeWidth, int angleCorner) {
        resetExAngle();
        initBtnAttr(cSolidColor, strokeColor, pressedColor, strokeColor, angleCorner, strokeWidth);
        setTextColor(strokeColor);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        defaultTextColor = color;

        if (pressedTextColor == Color.TRANSPARENT) {
            pressedTextColor = defaultTextColor;
        }
        if (clickTextColor == Color.TRANSPARENT) {
            clickTextColor = defaultTextColor;
        }
    }

    public interface ClickAction {
        void onClick(String s);
    }

    public static boolean isEmpty(String src) {
        return src == null || src.length() == 0 || src.trim().length() == 0 || TextUtils.equals(src.trim(), "null");
    }

}