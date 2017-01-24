package com.letterlocation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class LetterView extends View {
    public static String[] LETTERS = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private final int mPaintTextSize = 30;
    private final int mPaintDefaultColor = Color.parseColor("#FF333333");
    private final int mPaintSelectedColor = Color.parseColor("#FF00FF00");

    private int mChoose = -1;
    private Paint mPaint = new Paint();
    private TextView mLetterTV;
    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;

    public void setTextView(TextView letterTV) {
        this.mLetterTV = letterTV;
    }

    public LetterView(Context context) {
        super(context);
    }

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / LETTERS.length;
        for (int i = 0; i < LETTERS.length; i++) {
            mPaint.setColor(mPaintDefaultColor);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mPaintTextSize);  //unit--scaled pixels
            if (i == mChoose) {
                mPaint.setColor(mPaintSelectedColor);
                mPaint.setFakeBoldText(true);
            }
            float xPos = width / 2 - mPaint.measureText(LETTERS[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(LETTERS[i], xPos, yPos, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = mChoose;
        OnTouchingLetterChangedListener listener = mOnTouchingLetterChangedListener;
        int c = (int) (y / getHeight() * LETTERS.length);
        switch (action) {
            case MotionEvent.ACTION_UP:
                mChoose = -1;
                invalidate();
                if (mLetterTV != null) {
                    mLetterTV.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < LETTERS.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(LETTERS[c]);
                        }
                        if (mLetterTV != null) {
                            mLetterTV.setText(LETTERS[c]);
                            mLetterTV.setVisibility(View.VISIBLE);
                        }
                        mChoose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.mOnTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
