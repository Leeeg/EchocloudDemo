package ctyon.com.logcatproject.designs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ctyon.com.logcatproject.R;

public class CustomTabBackgroundView extends View {

    private int tabNum; //tab个数
    private int backgroundColor;    //背景色

    private int width;  //背景宽度
    private int heigth; //背景高度
    private int marLeft;    //下标中心左宽
    private int indWidth;   //下标宽度
    private int indHeigth;  //下标高度

    private int index = 1;

    private Paint paint;    //画笔

    public CustomTabBackgroundView(Context context) {
        this(context, null);
    }

    public CustomTabBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTabBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("ResourceAsColor")
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTabBackgroundView);
        tabNum = typedArray.getInteger(R.styleable.CustomTabBackgroundView_tabNum, 2);
        backgroundColor = typedArray.getColor(R.styleable.CustomTabBackgroundView_backgroundColor, getResources().getColor(R.color.backgrounddefault));
        typedArray.recycle();
    }

    public void setTabNum(int tabNum) {
        this.tabNum = tabNum;
    }

    public void setIndex(int index) {
        this.index = index;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init(){
        index = 2;

        width = getWidth();
        heigth = getHeight();

        marLeft = width / tabNum / 2;
        indWidth = 20;
        indHeigth = 16;

        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int factor = index * 2 - 1;//奇数递增数列

        paint.setStrokeWidth(1f);
        paint.setColor(backgroundColor);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, heigth);
        path.lineTo((marLeft * factor - indWidth), heigth);
        path.lineTo(marLeft * factor, (heigth - indHeigth));
        path.lineTo((marLeft * factor + indWidth), heigth);
        path.lineTo(width, heigth);
        path.lineTo(width, 0);
        path.close();
        canvas.drawPath(path, paint);

        paint.setStrokeWidth(4f);
        paint.setColor(getResources().getColor(R.color.backgroundLineGray));
        canvas.drawLine(0,  heigth,(marLeft * factor - indWidth), heigth, paint);
        canvas.drawLine((marLeft * factor - indWidth),  heigth,marLeft * factor, (heigth - indHeigth), paint);
        canvas.drawLine(marLeft * factor,  (heigth - indHeigth), (marLeft * factor + indWidth), heigth, paint);
        canvas.drawLine((marLeft * factor + indWidth), heigth, width, heigth, paint);

        Log.e("Lee",
                "(" + 0 + "," + 0 + ") \n" +
                        "(" + 0 + "," + heigth + ") \n" +
                        "(" + (marLeft - indWidth) * factor + "," + heigth + ") \n" +
                        "(" + marLeft * factor + "," + (heigth - indHeigth) + ") \n" +
                        "(" + (marLeft + indWidth) * factor + "," + heigth + ") \n" +
                        "(" + width + "," + heigth + ") \n" +
                        "(" + width + "," + 0 + ") \n"
        );


    }
}
