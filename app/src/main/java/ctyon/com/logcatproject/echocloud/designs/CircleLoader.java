package ctyon.com.logcatproject.echocloud.designs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import ctyon.com.logcatproject.R;

public class CircleLoader extends View implements InvalidateListener {
    private LoaderView loaderView;

    public CircleLoader(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public CircleLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public CircleLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLoader);

        loaderView = new RoundView();
        loaderView.setColor(typedArray.getColor(R.styleable.CircleLoader_mk_color, Color.parseColor("#ffffff")));

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int measuredWidth = resolveSize(loaderView.getDesiredWidth(), widthMeasureSpec);
        final int measuredHeight = resolveSize(loaderView.getDesiredHeight(), heightMeasureSpec);

        setMeasuredDimension(measuredWidth + 10, measuredHeight + 10);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loaderView.setSize(getWidth(), getHeight());
        loaderView.initializeObjects();
        loaderView.setAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loaderView.draw(canvas);
    }

    @Override
    public void reDraw() {
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (loaderView != null && loaderView.isDetached()) {
            loaderView.setInvalidateListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (loaderView != null) {
            loaderView.onDetach();
        }
    }

    public void setAnimationRepeatAble(boolean animationRepeatAble) {
        loaderView.setAnimationRepeatAble(animationRepeatAble);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            setAnimationRepeatAble(true);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            setAnimationRepeatAble(false);
        }
        return true;
    }
}
