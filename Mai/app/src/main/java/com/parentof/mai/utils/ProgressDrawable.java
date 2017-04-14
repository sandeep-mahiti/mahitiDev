package com.parentof.mai.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeep HR on 09/02/17.
 */


public class ProgressDrawable extends Drawable {
/*    private static final int NUM_SEGMENTS = 4;
    private final int mForeground;
    private final int mBackground;
    private final Paint mPaint = new Paint();
    private final RectF mSegment = new RectF();

    public ProgressDrawable(int fgColor, int bgColor) {
        mForeground = fgColor;
        mBackground = bgColor;
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        float level = getLevel() / 10000f;
        Rect b = getBounds();
        float gapWidth = b.height() / 2f;
        float segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS;
        mSegment.set(0, 0, segmentWidth, b.height());
        mPaint.setColor(mForeground);

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            float loLevel = i / (float) NUM_SEGMENTS;
            float hiLevel = (i + 1) / (float) NUM_SEGMENTS;
            if (loLevel <= level && level <= hiLevel) {
                float middle = mSegment.left + NUM_SEGMENTS * segmentWidth * (level - loLevel);
                canvas.drawRect(mSegment.left, mSegment.top, middle, mSegment.bottom, mPaint);
                mPaint.setColor(mBackground);
                canvas.drawRect(middle, mSegment.top, mSegment.right, mSegment.bottom, mPaint);
            } else {
                canvas.drawRect(mSegment, mPaint);
            }
            mSegment.offset(mSegment.width() + gapWidth, 0);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }*/

    private int parts = 10;

    private Paint paint = null;
    private int fillColor = Color.parseColor("#5e9b4c");
    private int emptyColor = Color.parseColor("#FFFFFF");
    private int separatorColor = Color.parseColor("#dbdbdb");
    private RectF rectFill = null;
    private RectF rectEmpty = null;
    private List<RectF> separators = null;

    public ProgressDrawable(int parts)
    {
        this.parts = parts;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.separators = new ArrayList<RectF>();
    }

    public ProgressDrawable(int parts, int fcolor, int emptyColor)
    {
        this.parts = parts;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.separators = new ArrayList<RectF>();
        this.fillColor=fcolor;
        this.emptyColor=emptyColor;
    }

    @Override
    protected boolean onLevelChange(int level)
    {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas)
    {
        // Calculate values
        Rect b = getBounds();
        float width = b.width();
        float height = b.height();

        int spaceFilled = (int)(getLevel() * width / 10000);
        this.rectFill = new RectF(0, 0, spaceFilled, height);
        this.rectEmpty = new RectF(spaceFilled, 0, width, height);

        int spaceBetween = (int)(width / 100);
        int widthPart = (int)(width / this.parts - (int)(0.9 * spaceBetween));
        int startX = widthPart;
        for (int i=0; i<this.parts - 1; i++)
        {
            this.separators.add( new RectF(startX, 0, startX + spaceBetween, height) );
            startX += spaceBetween + widthPart;
        }


        // Foreground
        this.paint.setColor(this.fillColor);
        canvas.drawRect(this.rectFill, this.paint);

        // Background
        this.paint.setColor(this.emptyColor);
        canvas.drawRect(this.rectEmpty, this.paint);

        // Separator
        this.paint.setColor(this.separatorColor);
        for (RectF separator : this.separators)
        {
            canvas.drawRect(separator, this.paint);
        }
    }

    @Override
    public void setAlpha(int alpha)
    {
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }
}
