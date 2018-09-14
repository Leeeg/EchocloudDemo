package ctyon.com.logcatproject.contacts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class CircleTextView extends View {

    private Paint bgPaint;
    private Paint wordPaint;
    private int rid = 3;
    private String text = "T";
    private String textColor;
    private String bgColor;
    private boolean drawAble;
    private int textSize = 13;
    int wordWidth;

    public CircleTextView setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public CircleTextView setText(String text) {
        this.text = text;
        return this;
    }

    public CircleTextView setTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public CircleTextView setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public void refresh(){
        drawAble = true;
        init();
        invalidate();
    }

    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor(bgColor));

        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setColor(Color.parseColor(textColor));
        wordPaint.setTextSize(textSize);
        wordPaint.setTypeface(Typeface.DEFAULT_BOLD);

        Rect rect = new Rect();
        wordPaint.getTextBounds(text, 0, 1, rect);
        wordWidth = rect.width();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rid = getMeasuredWidth() > getMeasuredHeight()? getMeasuredHeight()/2 : getMeasuredWidth()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawAble){
            canvas.drawCircle(getMeasuredHeight()/2, getMeasuredWidth()/2, rid, bgPaint);

            canvas.drawText(text,getMeasuredHeight()/2 - wordWidth/2, getMeasuredWidth()/2 + wordWidth/2, wordPaint);
        }


    }
}
