package com.haoshi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author HaoShi
 */
public class MarqueeTextView extends TextView implements View.OnClickListener {

    private float textLength = 0f;//文本长度
    private float viewWidth = 0f;
    private float step = 0f;//文字的横坐标
    private float speed = 1;
    private float y = 0f;//文字的纵坐标
    private float temp_view_plus_text_length = 0.0f;//用于计算的临时变量
    private float temp_view_plus_two_text_length = 0.0f;//用于计算的临时变量
    public boolean isStarting = false;//是否开始滚动
    private Paint paint = null;//绘图样式
    private String text = "";//文本内容
    private String fontColor = "#000000";
    private OnMarqueeCompleteListener marqueeCompleteListener;  //监听

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(WindowManager windowManager) {
        paint = getPaint();
        paint.setColor(Color.parseColor(fontColor));
        text = getText().toString();
        textLength = paint.measureText(text);
        viewWidth = getWidth();
        if (viewWidth == 0) {
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                viewWidth = display.getWidth();
            }
        }
        step = textLength;
        temp_view_plus_text_length = viewWidth + textLength;
        temp_view_plus_two_text_length = viewWidth + textLength * 2;
        y = getTextSize() + getPaddingTop();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.step = step;
        ss.isStarting = isStarting;
        return ss;

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        step = ss.step;
        isStarting = ss.isStarting;

    }

    public static class SavedState extends BaseSavedState {
        public boolean isStarting = false;
        public float step = 0.0f;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBooleanArray(new boolean[]{isStarting});
            out.writeFloat(step);
        }


        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
        };

        private SavedState(Parcel in) {
            super(in);
            boolean[] b = null;
            try {
                if (in == null) {
                    return;
                }
                in.readBooleanArray(b);
                if (b != null && b.length > 0) {
                    isStarting = b[0];
                }
                step = in.readFloat();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        isStarting = false;
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (text == "") {
            return;
        }
        canvas.drawText(text, temp_view_plus_text_length - step, y, paint);
        if (!isStarting) {
            return;
        }
        step += speed; //文字滚动速度。
        if (step > temp_view_plus_two_text_length) {
            if(marqueeCompleteListener!= null){
                marqueeCompleteListener.onMarqueeComplete();
            }
            step = textLength;
        }
        invalidate();
    }

    @Override
    public void onClick(View v) {
        if (isStarting)
            stopScroll();
        else
            startScroll();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setFontColor(String color) {
        this.fontColor = color;
    }

    public void setOnMarqueeCompleteListener(OnMarqueeCompleteListener marqueeCompleteListener) {
        this.marqueeCompleteListener = marqueeCompleteListener;
    }

    public interface OnMarqueeCompleteListener {
        void onMarqueeComplete();
    }
}
