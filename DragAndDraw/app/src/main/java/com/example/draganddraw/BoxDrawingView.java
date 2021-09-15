package com.example.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static boolean isDraw = true;
    private Box mCurrentBox;
    private List<Box> mBoxenErase = new ArrayList<>();
    private List<Box> mBoxenDraw = new ArrayList<>();
    private static Paint mBoxPaint;
    private Paint mBackgroundPain;
    public BoxDrawingView(Context context) {
        super(context);
    }

    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(Color.RED);

        mBackgroundPain = new Paint();
        mBackgroundPain.setColor(0xfff8efe0);
    }

    public static void setBoxPain(boolean isdraw) {
        isDraw = isdraw;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPain);
        for (Box box : mBoxenDraw) {
//            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
//            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
//            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
//            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
//            canvas.drawRect(left,top,right,bottom, mBoxPaint);
            float cx = box.getCurrent().x;
            float cy = box.getCurrent().y;
            boolean isdraw = box.getDraw();
            if (isdraw) {
                canvas.drawCircle(cx, cy, 20, mBoxPaint);
            } else {
                canvas.drawCircle(cx, cy, 30, mBackgroundPain);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                mCurrentBox = new Box(current);
                if (isDraw) {
                    mBoxenDraw.add(mCurrentBox);
                }
                else {
                    mBoxenErase.add(mCurrentBox);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    mCurrentBox = new Box(current);
                    mCurrentBox.setDraw(isDraw);
                    mBoxenDraw.add(mCurrentBox);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
        }

        Log.i(TAG, action + " at x = " + current.x + ", y = "
        + current.y);
        return true;
    }
}
