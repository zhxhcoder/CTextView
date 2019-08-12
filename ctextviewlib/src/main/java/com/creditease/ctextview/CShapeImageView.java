package com.creditease.ctextview;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by zhxh on 2019/08/12
 */
public class CShapeImageView extends ImageView {

    private Paint paint;
    private int roundWidth = 5;
    private int roundHeight = 5;
    private Paint paintBitmap;

    boolean leftUp, rightUp, leftDown, rightDown;

    public CShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public CShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CShapeImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paintBitmap = new Paint();
        paintBitmap.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasCorner = new Canvas(bitmap);
        super.draw(canvasCorner);

        if (leftUp) drawLeftUp(canvasCorner);
        if (rightUp) drawRightUp(canvasCorner);
        if (leftDown) drawLeftDown(canvasCorner);
        if (rightDown) drawRightDown(canvasCorner);

        canvas.drawBitmap(bitmap, 0, 0, paintBitmap);
        bitmap.recycle();
    }


    public void setCornerSize(int roundWidth, int roundHeight) {
        this.roundWidth = roundWidth;
        this.roundHeight = roundHeight;
        setCornersPos(true, true, true, true);
    }

    public void setCornersPos(boolean leftUp, boolean rightUp, boolean leftDown, boolean rightDown) {
        this.leftUp = leftUp;
        this.rightUp = rightUp;
        this.leftDown = leftDown;
        this.rightDown = rightDown;
        invalidate();
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundHeight);
        path.lineTo(0, 0);
        path.lineTo(roundWidth, 0);
        path.arcTo(new RectF(
                        0,
                        0,
                        roundWidth * 2,
                        roundHeight * 2),
                -90,
                -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - roundHeight);
        path.lineTo(0, getHeight());
        path.lineTo(roundWidth, getHeight());
        path.arcTo(new RectF(
                        0,
                        getHeight() - roundHeight * 2,
                        0 + roundWidth * 2,
                        getHeight()),
                90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - roundWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - roundHeight);
        path.arcTo(new RectF(
                getWidth() - roundWidth * 2,
                getHeight() - roundHeight * 2,
                getWidth(),
                getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), roundHeight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundWidth, 0);
        path.arcTo(new RectF(
                        getWidth() - roundWidth * 2,
                        0,
                        getWidth(),
                        0 + roundHeight * 2),
                -90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }
}