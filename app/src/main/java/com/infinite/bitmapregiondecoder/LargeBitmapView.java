package com.infinite.bitmapregiondecoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class LargeBitmapView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final String TAG = "LargeBitmapView";
    private int mImageWidht, mImageHeight;

    private BitmapRegionDecoder mDecoder;

    private GestureDetector mDetector;

    private static BitmapFactory.Options options = new BitmapFactory.Options();

    private Rect mDisplayRect = new Rect();

    public LargeBitmapView(Context context) {
        this(context, null);
    }

    public LargeBitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = getMeasuredWidth();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = getMeasuredHeight();
        }
        // 默认从图片左上角开始显示
        mDisplayRect.left = 0;
        mDisplayRect.top = 0;
        mDisplayRect.right = widthSize;
        mDisplayRect.bottom = heightSize;
        setMeasuredDimension(widthSize, heightSize);
    }


    public void setInputStream(InputStream inputStream) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options tempOptions = new BitmapFactory.Options();
        tempOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, tempOptions);
        mImageWidht = tempOptions.outWidth;
        mImageHeight = tempOptions.outHeight;
        requestLayout();
        invalidate();
        mDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    private Bitmap bitmap;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap!=null){
            bitmap.recycle();
        }
        bitmap = mDecoder.decodeRegion(mDisplayRect, options);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float dx, float dy) {
        mDisplayRect.offset(getDx(dx),getDy(dy));
        invalidate();
        return true;
    }

    private int getDx(float dx){
        if (mDisplayRect.left+dx<0){
            return 0;
        }
        if (mDisplayRect.right+dx>mImageWidht){
            return 0;
        }
        return (int) dx;
    }

    private int getDy(float dy){
        if (mDisplayRect.top+dy<0){
            return 0;
        }
        if (mDisplayRect.bottom+dy>mImageHeight){
            return 0;
        }
        return (int) dy;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mDetector.onTouchEvent(motionEvent);
    }
}
