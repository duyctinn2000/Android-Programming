package com.example.draganddraw;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Box {
    private PointF mOrigin;
    private PointF mCurrent;
    private boolean isDraw;

    public Box(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public boolean getDraw() {
        return isDraw;
    }

    public void setDraw(boolean isdraw) {
        isDraw = isdraw;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }
}


